package com.oliveshark.pathworks.framework.grid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.oliveshark.pathworks.framework.algorithm.def.CellUserData;

public class Cell {

    private final Texture texture;
    private boolean occupied = false;
    private CellUserData userData;

    public Cell(Texture texture) {
        this.texture = texture;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void toggleOccupied() {
        occupied = !occupied;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void draw(Batch batch, int x, int y) {
        if (occupied) {
            batch.draw(texture, x, y, 32, 0, 32, 32);
        } else {
            batch.draw(texture, x, y, 0, 0, 32, 32);
        }
    }

    public CellUserData getUserData() {
        return userData;
    }

    public void setUserData(CellUserData userData) {
        this.userData = userData;
    }
}
