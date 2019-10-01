package com.oliveshark.pathworks.framework.grid.util;

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
}
