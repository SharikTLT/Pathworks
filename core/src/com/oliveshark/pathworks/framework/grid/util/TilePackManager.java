package com.oliveshark.pathworks.framework.grid.util;

import com.badlogic.gdx.graphics.Texture;

/**
 * Tile pack manager store and rotate tiles
 */
public class TilePackManager {

    private CycleList<Texture> textureCycleList;

    public TilePackManager(String tilesRoot) {
        textureCycleList = new CycleList<>(ResourceScanner.scanAndMap(tilesRoot, Texture::new));
    }

    /**
     * Get current tile
     *
     * @return current tile
     */
    public Texture get() {
        return textureCycleList.get();
    }

    /**
     * Switch to next tile and return
     *
     * @return next tile
     */
    public Texture next() {
        return textureCycleList.next();
    }

    /**
     * Dispose stored textures
     */
    public void dispose() {
        this.textureCycleList.getList().forEach(Texture::dispose);
    }
}