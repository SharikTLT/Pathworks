package com.oliveshark.pathworks.framework;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.oliveshark.pathworks.core.Position;
import com.oliveshark.pathworks.framework.entities.Agent;
import com.oliveshark.pathworks.framework.grid.Grid;
import com.oliveshark.pathworks.framework.grid.util.Rectangle;

import static com.oliveshark.pathworks.config.Config.TILE_DIMENSION;
import static com.oliveshark.pathworks.framework.grid.util.PositionUtil.getGridPositionFromScreenPosition;
import static com.oliveshark.pathworks.framework.grid.util.PositionUtil.getPositionFromGridPosition;
import static com.oliveshark.pathworks.framework.grid.util.PositionUtil.getPositionFromScreenPosition;
import static com.oliveshark.pathworks.framework.grid.util.Rectangle.createSquare;

public class ViewStage extends Stage {

    private Agent currentAgent = null;
    private boolean secondRightClick = false;

    private Texture agentTexture;
    private Texture destTexture;

    public ViewStage() {
        agentTexture = new Texture(Gdx.files.internal("agent.png"));
        destTexture = new Texture(Gdx.files.internal("destination.png"));
        getBatch().enableBlending();
        addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if (character == 'r') {
                    Objects.requireNonNull(getGrid()).reverseCells();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void draw() {
        super.draw();
        if (currentAgent != null) {
            Batch batch = getBatch();
            batch.begin();
            currentAgent.draw(batch, 1);
            batch.end();
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.RIGHT) {
            Position<Integer> cellPos = getGridPositionFromScreenPosition(screenX, screenY);
            Position<Integer> gdxCellPos = getPositionFromGridPosition(cellPos);
            Position<Integer> gdxPos = getPositionFromScreenPosition(screenX, screenY);

            // Check if this is a remove agent click
            if (!secondRightClick && hasAgentOrDestinationAt(gdxPos.x, gdxPos.y)) {
                getActors().removeValue(getAgentFor(gdxPos.x, gdxPos.y), true);
                return true;
            }

            // Otherwise check if we should add an agent or a destination
            Grid grid = getGrid();
            assert grid != null;
            if (grid.isCellOccupied(cellPos) || hasAgentOnTile(gdxCellPos)) {
                return false;
            }
            if (secondRightClick) {
                currentAgent.setDestination(gdxCellPos);
                addActor(currentAgent);
                currentAgent = null;
                secondRightClick = false;
            } else {
                currentAgent = new Agent(agentTexture, destTexture, gdxCellPos.x, gdxCellPos.y);
                secondRightClick = true;
            }
        }
        if (button == Input.Buttons.LEFT) {
            secondRightClick = false;
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }

    private List<Agent> getAgents() {
        return Stream.of(getActors().items)
                .filter(Objects::nonNull)
                .filter(actor -> "agent".equals(actor.getName()))
                .map(actor -> (Agent) actor)
                .collect(Collectors.toList());
    }

    private boolean hasAgentOrDestinationAt(float x, float y) {
        return getAgentFor(x, y) != null;
    }

    private Agent getAgentFor(float x, float y) {
        Position<Float> pos = new Position<>(x, y);
        for (Agent agent : getAgents()) {
            Rectangle agentRect = Rectangle.fromActor(agent);
            if (agentRect.contains(pos)) {
                return agent;
            }
            if (agent.hasDestination()) {
                Rectangle destRect = createSquare(agent.getDestination(), TILE_DIMENSION);
                if (destRect.contains(pos)) {
                    return agent;
                }
            }
        }
        return null;
    }

    public boolean hasAgentOnTile(Position<Integer> tilePos) {
        Rectangle rect = createSquare(tilePos, TILE_DIMENSION);
        for (Agent agent : getAgents()) {
            Rectangle agentRect = Rectangle.fromActor(agent);
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

    private Grid getGrid() {
        for (Actor actor : getActors()) {
            if ("grid".equals(actor.getName())) {
                return (Grid) actor;
            }
        }
        return null;
    }
}
