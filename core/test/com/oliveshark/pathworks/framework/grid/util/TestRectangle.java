package com.oliveshark.pathworks.framework.grid.util;

import com.oliveshark.pathworks.core.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestRectangle {

    @Test
    public void testOverlap() {
        Rectangle r1 = new Rectangle(0, 0, 32, 32);
        Rectangle r2 = new Rectangle(33, 0, 32, 32);
        Rectangle r3 = new Rectangle(15, 0, 32, 32);

        Assertions.assertTrue(r1.overlaps(r3));
        Assertions.assertTrue(r2.overlaps(r3));
        Assertions.assertFalse(r1.overlaps(r2));
    }

    @Test
    public void testContains() {
        Position<Float> pos1 = new Position<Float>(15f, 15f);
        Position<Float> pos2 = new Position<Float>(35f, 35f);
        Rectangle rect = Rectangle.createSquare(0, 0, 32);

        Assertions.assertTrue(rect.contains(pos1));
        Assertions.assertFalse(rect.contains(pos2));
    }
}
