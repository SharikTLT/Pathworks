package com.oliveshark.pathworks.framework.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.oliveshark.pathworks.core.Position;

import java.util.Random;

import static com.oliveshark.pathworks.config.Config.TILE_DIMENSION;
import static com.oliveshark.pathworks.config.Config.GRID_WIDTH;

public class Agent {

    private Position<Integer> position;
    private Position<Integer> destination;
//    private float velocity = 0.0f;
    private final Color color;

    public Agent(Position<Integer> position){
        color = generateRandomColor();
        this.position = position;
    }

    private Color generateRandomColor() {
        Random random = new Random(System.nanoTime());
        final int colorUpperBound = 256;
        float red = random.nextInt(colorUpperBound) / 255.0f;
        float green = random.nextInt(colorUpperBound) / 255.0f;
        float blue = random.nextInt(colorUpperBound) / 255.0f;
        return new Color(red, green, blue, 1.0f);
    }

    public void draw(ShapeRenderer renderer) {
        renderer.setColor(color);

        int halfWidth = (TILE_DIMENSION >> 1);

        float posXCenter = position.x + halfWidth;
        float posYCenter = position.y + halfWidth;

        // Agent
        renderer.circle(posXCenter, posYCenter, halfWidth);

        if(destination == null){
            return;
        }

        float destXCenter = destination.x + halfWidth;
        float destYCenter = destination.y + halfWidth;

        Position<Float> triangleTop = new Position<>(destXCenter, destYCenter + halfWidth);
        Position<Float> triangleLeft = new Position<>(destXCenter - halfWidth, destYCenter - halfWidth);
        Position<Float> triangleRight = new Position<>(destXCenter + halfWidth, destYCenter - halfWidth);

        // Destination
        renderer.triangle(triangleTop.x, triangleTop.y, triangleLeft.x,
                triangleLeft.y, triangleRight.x, triangleRight.y);
    }

    public Position<Integer> getPosition() {
        return position;
    }

    public void setPosition(Position<Integer> position) {
        this.position = position;
    }

    public boolean hasDestination() {
        return destination != null;
    }

    public Position<Integer> getDestination() {
        return destination;
    }

    public void setDestination(Position<Integer> destination) {
        this.destination = destination;
    }

    public Color getColor() {
        return color;
    }
}
