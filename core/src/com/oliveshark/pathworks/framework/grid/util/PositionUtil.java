package com.oliveshark.pathworks.framework.grid.util;

import com.oliveshark.pathworks.core.Position;

import static com.oliveshark.pathworks.config.Config.*;

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
    public static Position<Integer> getGridPositionFromScreenPosition(int x, int y) {
        int cellX = (x - (x % TILE_DIMENSION)) / TILE_DIMENSION;
        int cellY = (y - (y % TILE_DIMENSION)) / TILE_DIMENSION;

        // Mouse pos originates from the top left corner (like SDL)
        // libgdx originates coordinates from bottom left so we have to reverse it here
        cellY = GRID_HEIGHT - cellY - 1;

        if (cellX < 0) cellX = 0;
        else if (cellX >= GRID_WIDTH) cellX = GRID_WIDTH - 1;
        if (cellY < 0) cellY = 0;
        else if (cellY >= GRID_HEIGHT) cellY = GRID_HEIGHT - 1;
        return new Position<>(cellX, cellY);
    }

    /**
     * Takes a position and converts it to the coresponding grid cell position.
     *
     * @param x The x pos
     * @param y The y pos
     * @return The position in Grid cell coordinates
     */
    public static Position<Integer> getGridPositionFromPosition(float x, float y) {
        float cellX = (x - (x % TILE_DIMENSION)) / TILE_DIMENSION;
        float cellY = (y - (y % TILE_DIMENSION)) / TILE_DIMENSION;

        if (cellX < 0) cellX = 0;
        else if (cellX >= GRID_WIDTH) cellX = GRID_WIDTH - 1;
        if (cellY < 0) cellY = 0;
        else if (cellY >= GRID_HEIGHT) cellY = GRID_HEIGHT - 1;
        return new Position<>((int) cellX, (int) cellY);
    }

    public static Position<Integer> getPositionFromGridPosition(Position<Integer> pos) {
        return getPositionFromGridPosition(pos.x, pos.y);
    }

    public static Position<Integer> getPositionFromGridPosition(int x, int y) {
        return new Position<>(x * TILE_DIMENSION, y * TILE_DIMENSION);
    }

    /**
     * This converts 'click' positions (that have origin in top left corner)
     * to a GDX position (that has origin in bottom left)
     * @param screenX The click x position
     * @param screenY The click y position
     * @return A gdx position
     */
    public static Position<Integer> getPositionFromScreenPosition(int screenX, int screenY) {
        return new Position<>(screenX, GRID_HEIGHT * TILE_DIMENSION - screenY);
    }
}
