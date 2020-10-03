package com.oliveshark.pathworks.framework.grid.util;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CycleListTest {

    @Test
    void get() {
        CycleList<String> cycleList = new CycleList<>(asList("one", "two", "three"));
        assertEquals("one", cycleList.get());
        assertEquals("one", cycleList.get());
    }

    @Test
    void next() {
        CycleList<String> cycleList = new CycleList<>(asList("one", "two", "three"));
        assertEquals("one", cycleList.get());
        assertEquals("two", cycleList.next());
        assertEquals("two", cycleList.get());
        assertEquals("three", cycleList.next());
        assertEquals("one", cycleList.next());
    }
}