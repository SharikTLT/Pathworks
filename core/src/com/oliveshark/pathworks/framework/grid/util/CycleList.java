package com.oliveshark.pathworks.framework.grid.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Cycle list implementation with generic type items stored.
 * Can be iterated through items and return to beginning after end reached.
 *
 * @param <T>
 */
public class CycleList<T> {

    private final List<T> list = new ArrayList<>();

    private AtomicInteger index = new AtomicInteger(0);

    public CycleList(List<T> elements) {
        list.addAll(elements);
    }

    /**
     * Return current element
     *
     * @return current element
     */
    public T get() {
        return list.get(index.get());
    }

    /**
     * Return next element with changing stored index
     *
     * @return next element
     */
    public T next() {
        if (index.incrementAndGet() == list.size()) {
            index.set(0);
        }
        return get();
    }

    /**
     * Return entire list
     *
     * @return entire list
     */
    public List<T> getList() {
        return list;
    }
}
