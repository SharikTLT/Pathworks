package com.oliveshark.pathworks.framework.collision;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class QuadTree<T> {
    private final int capacity;
    private final List<QuadTreeItem<T>> items = new ArrayList<>();
    private final Rectangle boundary;
    private QuadTree<T>[] subTrees;

    public QuadTree(Rectangle boundary, int capacity) {
        this.capacity = capacity;
        this.boundary = boundary;
    }

    public boolean insert(Vector2 pos, T item) {
        return insert(new QuadTreeItem<>(pos, item));
    }

    public boolean insert(QuadTreeItem<T> item) {
        if (!boundary.contains(item.pos)) {
            return false;
        }
        if (subTrees == null) {
            addItem(item);
            return true;
        }
        for (QuadTree<T> subTree : subTrees) {
            if (subTree.insert(item)) {
                return true;
            }
        }
        return false;
    }

    void addItem(QuadTreeItem<T> item) {
        items.add(item);
        if (items.size() >= capacity) {
            split();
        }
    }

    /**
     * Split current QuadTree to four subTree and move all items to subTrees
     */
    private void split() {
        float halfWidth = boundary.width / 2f;
        float halfHeight = boundary.height / 2f;
        Vector2 center = new Vector2(
                boundary.x + halfWidth,
                boundary.y + halfHeight
        );

        this.subTrees = new QuadTree[]{
                //NorthWest
                new QuadTree<>(new Rectangle(boundary.x, center.y, halfWidth, halfHeight), capacity),
                //NorthEast
                new QuadTree<>(new Rectangle(center.x, center.y, halfWidth, halfHeight), capacity),
                //SouthEast
                new QuadTree<>(new Rectangle(center.x, boundary.y, halfWidth, halfHeight), capacity),
                //SouthWest
                new QuadTree<>(new Rectangle(boundary.x, boundary.y, halfWidth, halfHeight), capacity)
        };

        for (QuadTreeItem<T> item : items) {
            for (QuadTree<T> subTree : subTrees) {
                subTree.insert(item);
            }
        }
        items.clear();
    }

    @Override
    public String toString() {
        return "QuadTree{" +
                "boundary=" + boundary +
                ", size=" + size() +
                '}';
    }

    public List<QuadTreeItem<T>> getItems() {
        return items;
    }

    public QuadTree<T>[] getSubTrees() {
        return subTrees;
    }

    public long size() {
        if (subTrees == null) {
            return items.size();
        }
        LongSummaryStatistics summaryStatistics = Arrays.stream(subTrees).map(QuadTree::size).collect(Collectors.summarizingLong(Long::longValue));
        return items.size() + (subTrees != null ? summaryStatistics.getSum() : 0);
    }

    public List<T> query(Rectangle rect) {
        if (!rect.overlaps(boundary)) {
            return Collections.emptyList();
        }
        if (subTrees == null) {
            return items.stream().filter(item -> rect.contains(item.pos)).map(QuadTreeItem::getItem).collect(toList());
        }
        return Arrays.stream(subTrees).flatMap((QuadTree<T> subTree) -> subTree.query(rect).stream()).collect(toList());
    }
}

class QuadTreeItem<T> {
    final T item;
    final Vector2 pos;

    public QuadTreeItem(Vector2 pos, T item) {
        this.pos = pos.cpy();
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public Vector2 getPos() {
        return pos;
    }
}


