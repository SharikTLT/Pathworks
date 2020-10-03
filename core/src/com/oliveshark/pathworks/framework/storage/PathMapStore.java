package com.oliveshark.pathworks.framework.storage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.oliveshark.pathworks.config.Config;
import com.oliveshark.pathworks.framework.grid.Cell;
import com.oliveshark.pathworks.framework.grid.Grid;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PathMap file store controller <br/>
 * provide methods to store current map to file and restore from file<br/>
 * File format:
 * <pre>
 * size:WIDTH_SIZE,HEIGHT_SIZE
 * EMPTY_LINE
 * row0
 * row1
 * ....
 * rowN
 * </pre>
 * Every row consist of characters _ (free) or X (occupied)</br>
 * Example:
 * <pre>
 * size:3,3
 *
 * ___
 * _X_
 * ___
 * </pre>
 */
public class PathMapStore {

    public static final String TAG = "store";
    public static final String SIZE_HEADER = "size:";
    public static final char OCCUPIED_SYMBOL = 'X';
    public static final char EMPTY_SYMBOL = '_';
    public static final String PATH_MAP_SUFFIX = ".pathMap";
    private Grid grid;

    public PathMapStore(Grid grid) {
        this.grid = grid;
    }

    /**
     * Save current map to file (without agents)
     *
     * @param pathFile path from fileChooser
     */
    public void save(FileHandle pathFile) {
        File file = pathFile.file();
        if (!file.getName().endsWith(PATH_MAP_SUFFIX)) {
            file = new File(file.getAbsolutePath() + PATH_MAP_SUFFIX);
        }
        Gdx.app.log("store", "Save file: " + pathFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Gdx.app.error(TAG, e.getMessage());
                return;
            }
        }
        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter bw = new BufferedWriter(osw)) {
            bw.write(String.format("size:%d,%d", Config.GRID_WIDTH, Config.GRID_HEIGHT));
            bw.newLine();
            bw.newLine();
            for (int row = Config.GRID_HEIGHT - 1; row >= 0; row--) {
                for (int col = 0; col < Config.GRID_WIDTH; col++) {
                    Cell cell = grid.getCell(new Vector2(col, row));
                    bw.write(cell.isOccupied() ? OCCUPIED_SYMBOL : EMPTY_SYMBOL);
                }
                bw.newLine();
            }
        } catch (Exception e) {
            Gdx.app.error(TAG, e.getMessage());
        }
    }

    /**
     * Restore map from file
     *
     * @param pathFile path from fileChooser
     */
    public void load(FileHandle pathFile) {
        if (!pathFile.file().getName().endsWith(PATH_MAP_SUFFIX)) {
            return;
        }
        Gdx.app.log("store", "Load file: " + pathFile);
        try (FileInputStream fis = new FileInputStream(pathFile.file());
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr);
        ) {
            int row = -1;
            String line;
            int cols = 0;
            int rows = 0;
            while ((line = br.readLine()) != null) {
                Gdx.app.log(TAG, "Read line: " + line);
                if (line.isEmpty()) {
                    if (cols == 0 || rows == 0 || row > -1) {
                        throw new RuntimeException("Missing map size, or empty wrong place for empty line");
                    }
                    row = 0;
                    continue;
                }
                if (line.startsWith(SIZE_HEADER)) {
                    Matcher matcher = Pattern.compile(SIZE_HEADER + "(\\d+),(\\d+)$").matcher(line);
                    if (matcher.find()) {
                        cols = Integer.parseInt(matcher.group(1));
                        rows = Integer.parseInt(matcher.group(2));
                    }
                }

                if (row > -1) {
                    for (int col = 0; col < cols; col++) {
                        grid.getCell(new Vector2(col, row)).setOccupied(line.charAt(col) == OCCUPIED_SYMBOL);
                    }
                    row++;
                }
            }
        } catch (Exception e) {
            Gdx.app.error(TAG, e.getMessage());
        }
    }
}
