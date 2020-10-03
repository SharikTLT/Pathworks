package com.oliveshark.pathworks.framework.grid;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.oliveshark.pathworks.framework.grid.util.TilePackManager;
import com.oliveshark.pathworks.framework.algorithm.def.CellUserData;

public class Cell {

    private final TilePackManager tilePackManager;
    private boolean occupied = false;
    private CellUserData userData;

    public Cell(TilePackManager tilePackManager) {
        this.tilePackManager = tilePackManager;
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
            batch.draw(tilePackManager.get(), x, y, 32, 0, 32, 32);
        } else {
            batch.draw(tilePackManager.get(), x, y, 0, 0, 32, 32);
        }
    }

    public CellUserData getUserData() {
        return userData;
    }

    public void setUserData(CellUserData userData) {
        this.userData = userData;
    }
}
