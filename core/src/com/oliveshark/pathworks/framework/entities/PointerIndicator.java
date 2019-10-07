package com.oliveshark.pathworks.framework.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.oliveshark.pathworks.framework.grid.Grid;

public class PointerIndicator extends Actor {

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private boolean shapeRendererMatrixSet = false;

    public PointerIndicator() {
        setTouchable(Touchable.disabled);
        setName("pointerIndicator");
        setWidth(32);
        setHeight(32);
        setX(0);
        setY(0);
    }

    public void updatePosition(float x, float y) {
        Vector2 gridPos = Grid.getGridPositionFromStagePosition(x, y);
        Vector2 gdxCellPos = Grid.getStagePositionFromGridPosition(gridPos);
        setX(gdxCellPos.x);
        setY(gdxCellPos.y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        if (!shapeRendererMatrixSet) {
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRendererMatrixSet = true;
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(1f, 1f, 1f, 0.4f));
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
        shapeRenderer.end();
        batch.begin();
    }
}
