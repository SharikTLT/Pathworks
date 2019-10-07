package com.oliveshark.pathworks.framework.grid;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.oliveshark.pathworks.config.Config.GRID_HEIGHT;
import static com.oliveshark.pathworks.config.Config.GRID_WIDTH;
import static com.oliveshark.pathworks.config.Config.TILE_DIMENSION;

class TestPositionUtil {

    @Test
    void testGetGridPositionFromScreenPosition() {
        Vector2 beyondTopRight = Grid.getGridPositionFromScreenPosition(GRID_WIDTH*TILE_DIMENSION + 10, -100);
        Vector2 beyondBottomLeft = Grid.getGridPositionFromScreenPosition(-100, GRID_HEIGHT*TILE_DIMENSION + 10);
        Vector2 withinGrid = Grid.getGridPositionFromScreenPosition(320, 320);

        Assertions.assertEquals(new Vector2(GRID_WIDTH - 1, GRID_HEIGHT - 1), beyondTopRight);
        Assertions.assertEquals(new Vector2(0, 0), beyondBottomLeft);
        Assertions.assertEquals(new Vector2(10, 13), withinGrid);
    }

    @Test
    void testGetPositionFromGridPosition() {
        Vector2 pos = Grid.getStagePositionFromGridPosition(10, 10);
        Assertions.assertEquals(new Vector2(320, 320), pos);
    }
}
