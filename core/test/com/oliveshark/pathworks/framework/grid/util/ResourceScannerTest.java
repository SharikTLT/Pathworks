package com.oliveshark.pathworks.framework.grid.util;

import org.junit.jupiter.api.Test;

import static java.util.function.Function.identity;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test {@link ResourceScanner}
 */
class ResourceScannerTest {

    /**
     * Simply scan testDir and assert number of returned elements
     */
    @Test
    void scanAndMapToTexture() {
        assertEquals(2, ResourceScanner.scanAndMap("/testDir", identity()).size());
        assertEquals(2, ResourceScanner.scanAndMap("testDir", identity()).size());
    }
}