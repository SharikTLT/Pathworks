package com.oliveshark.pathworks.framework.grid.util;

import com.oliveshark.pathworks.core.Position;

import static com.oliveshark.pathworks.config.Config.*;

public class PositionUtil {

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
