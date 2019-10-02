package com.oliveshark.pathworks.framework.grid.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.oliveshark.pathworks.core.Position;

import java.util.Objects;

public class Rectangle {

    private float x;
    private float y;
    private float w;
    private float h;

    public Rectangle(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Rectangle(Position<Integer> pos, float w, float h) {
        this(pos.x, pos.y, w, h);
    }

    public static Rectangle createSquare(float x, float y, float dim) {
        return new Rectangle(x, y, dim, dim);
    }

    public static Rectangle createSquare(Position<Integer> pos, float dim) {
        return new Rectangle(pos, dim, dim);
    }

    public static Rectangle fromActor(Actor actor) {
        return new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
    }

    public boolean contains(Position<Float> pos) {
        return pos.x > x && pos.x < x + w && pos.y > y && pos.y < y + h;
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
