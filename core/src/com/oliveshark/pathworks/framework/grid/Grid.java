package com.oliveshark.pathworks.framework.grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.oliveshark.pathworks.core.Position;
import com.oliveshark.pathworks.framework.ViewStage;

import static com.oliveshark.pathworks.config.Config.*;
import static com.oliveshark.pathworks.framework.grid.util.PositionUtil.getGridPositionFromPosition;
import static com.oliveshark.pathworks.framework.grid.util.PositionUtil.getPositionFromGridPosition;

public class Grid extends Actor {

    private Cell[][] cells;
    private Texture tileTexture;

    private boolean touchDragToggle = false;

    public Grid() {
        tileTexture = new Texture(Gdx.files.internal("tiles_grass_boulder.png"));
        cells = new Cell[GRID_WIDTH][GRID_HEIGHT];
        for (int i = 0; i < GRID_WIDTH; ++i) {
            for (int j = 0; j < GRID_HEIGHT; ++j) cells[i][j] = new Cell(tileTexture);
        }

        setWidth(GRID_WIDTH * TILE_DIMENSION);
        setHeight(GRID_HEIGHT * TILE_DIMENSION);

        addListener(new GridClickListener());
        setName("grid");
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int i = 0; i < GRID_WIDTH; ++i) {
            for (int j = 0; j < GRID_HEIGHT; ++j) {
                cells[i][j].draw(batch, i * TILE_DIMENSION, j * TILE_DIMENSION);
            }
        }
    }

    @Override
    public boolean remove() {
        boolean success = super.remove();
        tileTexture.dispose();
        return success;
    }

    public void reverseCells() {
        for (int i = 0; i < cells.length; ++i) {
            for (int j = 0; j < cells[i].length; ++j) {
                if (!hasAgentOnTile(getPositionFromGridPosition(i, j))) {
                    cells[i][j].toggleOccupied();
                }
            }
        }
    }

    private boolean hasAgentOnTile(Position<Integer> position) {
        return ((ViewStage) getStage()).hasAgentOnTile(position);
    }

    public Cell getCell(Position<Integer> pos) {
        return cells[pos.x][pos.y];
    }

    public boolean isCellOccupied(Position<Integer> pos) {
        return cells[pos.x][pos.y].isOccupied();
    }


    private class GridClickListener extends ClickListener {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Position<Integer> cellPos = getGridPositionFromPosition(x, y);
            Cell cell = cells[cellPos.x][cellPos.y];
            if (button == Input.Buttons.LEFT) {
                if (hasAgentOnTile(getPositionFromGridPosition(cellPos))) {
                    return false;
                }
                cell.toggleOccupied();
                touchDragToggle = cell.isOccupied();
                return true;
            }

            return false;
        }

        @Override
        public void touchDragged(InputEvent input, float x, float y, int pointer) {
            Position<Integer> cellPos = getGridPositionFromPosition(x, y);
            if (!hasAgentOnTile(getPositionFromGridPosition(cellPos))) {
                cells[cellPos.x][cellPos.y].setOccupied(touchDragToggle);
            }
        }
    }
}
