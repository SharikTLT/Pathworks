package com.oliveshark.pathworks.framework.grid.util;

import com.oliveshark.pathworks.core.Position;

import java.util.Objects;

public class Rectangle {

    private int x;
    private int y;
    private int w;
    private int h;

    public Rectangle(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Rectangle(Position<Integer> pos, int w, int h) {
        this(pos.x, pos.y, w, h);
    }

    public static Rectangle createSquare(int x, int y, int dim) {
        return new Rectangle(x, y, dim, dim);
    }

    public static Rectangle createSquare(Position<Integer> pos, int dim) {
        return new Rectangle(pos, dim, dim);
    }

    public boolean overlaps(Rectangle r) {
        return !(x + w <= r.x || x >= r.x + r.w
                || y <= r.y - r.h || y - h >= r.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return x == rectangle.x &&
                y == rectangle.y &&
                w == rectangle.w &&
                h == rectangle.h;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, w, h);
    }
}
