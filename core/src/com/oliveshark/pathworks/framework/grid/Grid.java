package com.oliveshark.pathworks.framework.grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.oliveshark.pathworks.core.Position;
import com.oliveshark.pathworks.framework.entities.Agent;
import com.oliveshark.pathworks.framework.grid.util.Rectangle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static com.oliveshark.pathworks.config.Config.*;
import static com.oliveshark.pathworks.framework.grid.util.PositionUtil.*;
import static com.oliveshark.pathworks.framework.grid.util.Rectangle.createSquare;

public class Grid extends Actor implements InputProcessor {

    private Position<Integer> mousePos = new Position<>(0, 0);
    private Cell[][] cells;
    private Texture tileTexture;
    private Collection<Agent> agents;
    private ShapeRenderer agentRenderer;
    private Agent currentAgent = null;

    private boolean mouseLeftButtonDown = false;
    private boolean mouseLeftButtonDownToggle = false;
    private boolean secondRightClick = false;

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private boolean shapeRendererMatrixSet = false;

    public Grid() {
        tileTexture = new Texture(Gdx.files.internal("tiles.png"));
        cells = new Cell[GRID_WIDTH][GRID_HEIGHT];
        for (int i = 0; i < GRID_WIDTH; ++i) {
            for (int j = 0; j < GRID_HEIGHT; ++j) cells[i][j] = new Cell(tileTexture);
        }
        Gdx.input.setInputProcessor(this);

        // Get random positions based on grid dimensions
        agents = new ArrayList<>();
        agentRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int i = 0; i < GRID_WIDTH; ++i) {
            for (int j = 0; j < GRID_HEIGHT; ++j) {
                cells[i][j].draw(batch, i * TILE_DIMENSION, j * TILE_DIMENSION);
            }
        }

        // Render agents
        batch.end();
        agentRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Agent agent : agents) {
            agent.draw(agentRenderer);
        }
        agentRenderer.end();
        batch.begin();

        // Draw the mouse grid indicator
        if (mousePos.x != 0 || mousePos.y != 0) {
            batch.end();

            Gdx.gl.glEnable(GL30.GL_BLEND);
            Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
            if (!shapeRendererMatrixSet) {
                shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
                shapeRendererMatrixSet = true;
            }
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(new Color(1.0f, 1.0f, 1.0f, 0.3f));
            shapeRenderer.rect(mousePos.x, mousePos.y, TILE_DIMENSION, TILE_DIMENSION);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL30.GL_BLEND);

            batch.begin();
        }

    }

    @Override
    public boolean remove() {
        boolean success = super.remove();
        tileTexture.dispose();
        return success;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    private void reverseCells() {
        for (int i = 0; i < cells.length; ++i) {
            for (int j = 0; j < cells[i].length; ++j) {
                if (!hasAgentOnTile(getPositionFromGridPosition(i, j))) {
                    cells[i][j].toggleOccupied();
                }
            }
        }
    }

    @Override
    public boolean keyTyped(char character) {
        if (character == 'r') reverseCells();
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Position<Integer> cellPos = getGridPositionFromScreenPosition(screenX, screenY);
        Position<Integer> gdxCellPos = getPositionFromGridPosition(cellPos);

        Cell cell = cells[cellPos.x][cellPos.y];

        if (button == Input.Buttons.RIGHT) {
            if (cellOccupied(cellPos)) {
                return true;
            }
            if (hasAgentOnTile(gdxCellPos)) {
                if (secondRightClick) {
                    return true;
                } else {
                    removeAgentAtPosition(getPositionFromScreenPosition(screenX, screenY));
                    return true;
                }
            }
            if (secondRightClick) {
                currentAgent.setDestination(gdxCellPos);
                currentAgent = null;
                secondRightClick = false;
                return true;
            } else {
                currentAgent = new Agent(gdxCellPos);
                agents.add(currentAgent);
                secondRightClick = true;
                return true;
            }
        } else if (button == Input.Buttons.LEFT) {
            if (hasAgentOnTile(cellPos)) {
                return true;
            }
            cell.toggleOccupied();
            mouseLeftButtonDown = true;
            mouseLeftButtonDownToggle = cell.isOccupied();
        }
        secondRightClick = false;
        agents.remove(currentAgent);
        currentAgent = null;

        return true;
    }

    private void removeAgentAtPosition(Position<Integer> screenPos) {
        Iterator<Agent> it = agents.iterator();
        while (it.hasNext()) {
            Agent agent = it.next();
            Rectangle agentRect = Rectangle.createSquare(agent.getPosition(), TILE_DIMENSION);
            if (agentRect.contains(screenPos)) {
                it.remove();
            }
            if (agent.hasDestination()) {
                Rectangle destRect = Rectangle.createSquare(agent.getDestination(), TILE_DIMENSION);
                if (destRect.contains(screenPos)) {
                    it.remove();
                }
            }
        }
    }

    private boolean hasAgentOnTile(Position<Integer> tilePos) {
        Rectangle rect = createSquare(tilePos, TILE_DIMENSION);
        for (Agent agent : agents) {
            Position<Integer> agentPos = agent.getPosition();
            Rectangle agentRect = createSquare(agentPos, TILE_DIMENSION);
            if (rect.overlaps(agentRect)) {
                return true;
            }
            if (agent.hasDestination()) {
                Rectangle destRect = createSquare(agent.getDestination(), TILE_DIMENSION);
                if (rect.overlaps(destRect)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean cellOccupied(Position<Integer> cellPosition) {
        return cells[cellPosition.x][cellPosition.y].isOccupied();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT)
            return true;
        mouseLeftButtonDown = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!mouseLeftButtonDown)
            return true;
        Position<Integer> cellPos = getGridPositionFromScreenPosition(screenX, screenY);
        if (hasAgentOnTile(getPositionFromGridPosition(cellPos))) {
            return true;
        }
        cells[cellPos.x][cellPos.y].setOccupied(mouseLeftButtonDownToggle);
        updateMousePos(screenX, screenY);
        return false;
    }

    private void updateMousePos(int screenX, int screenY) {
        Position<Integer> gridPos = getGridPositionFromScreenPosition(screenX, screenY);
        mousePos = getPositionFromGridPosition(gridPos.x, gridPos.y);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        updateMousePos(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
