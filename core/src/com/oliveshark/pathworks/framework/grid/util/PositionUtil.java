package com.oliveshark.pathworks.framework.grid.util;

import com.badlogic.gdx.math.Vector2;

import static com.oliveshark.pathworks.config.Config.GRID_HEIGHT;
import static com.oliveshark.pathworks.config.Config.GRID_WIDTH;
import static com.oliveshark.pathworks.config.Config.TILE_DIMENSION;

public class PositionUtil {

    /**
     * This converts 'click' positions (that have origin in top left corner)
     * to a grid position (that has origin in bottom left) and coresponds to a grid cells
     * in the Grids cell matrix
     *
     * @param x The x pos
     * @param y The y pos
     * @return The position in Grid cell coordinates
     */
    public static Vector2 getGridPositionFromScreenPosition(int x, int y) {
        int cellX = (x - (x % TILE_DIMENSION)) / TILE_DIMENSION;
        int cellY = (y - (y % TILE_DIMENSION)) / TILE_DIMENSION;

        // Mouse pos originates from the top left corner (like SDL)
        // libgdx originates coordinates from bottom left so we have to reverse it here
        cellY = GRID_HEIGHT - cellY - 1;

        if (cellX < 0) cellX = 0;
        else if (cellX >= GRID_WIDTH) cellX = GRID_WIDTH - 1;
        if (cellY < 0) cellY = 0;
        else if (cellY >= GRID_HEIGHT) cellY = GRID_HEIGHT - 1;
        return new Vector2(cellX, cellY);
    }

    /**
     * Takes a position and converts it to the coresponding grid cell position.
     *
     * @param x The x pos
     * @param y The y pos
     * @return The position in Grid cell coordinates
     */
    public static Vector2 getGridPositionFromPosition(float x, float y) {
        float cellX = (x - (x % TILE_DIMENSION)) / TILE_DIMENSION;
        float cellY = (y - (y % TILE_DIMENSION)) / TILE_DIMENSION;

        if (cellX < 0) cellX = 0;
        else if (cellX >= GRID_WIDTH) cellX = GRID_WIDTH - 1;
        if (cellY < 0) cellY = 0;
        else if (cellY >= GRID_HEIGHT) cellY = GRID_HEIGHT - 1;
        return new Vector2(cellX, cellY);
    }

    public static Vector2 getPositionFromGridPosition(Vector2 pos) {
        return getPositionFromGridPosition(pos.x, pos.y);
    }

    public static Vector2 getPositionFromGridPosition(int x, int y) {
        return new Vector2(x * TILE_DIMENSION, y * TILE_DIMENSION);
    }

    public static Vector2 getPositionFromGridPosition(float x, float y) {
        return new Vector2(x * TILE_DIMENSION, y * TILE_DIMENSION);
    }
}
