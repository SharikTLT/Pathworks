package com.oliveshark.pathworks.framework.algorithm.impl;

import com.badlogic.gdx.math.Vector2;
import com.oliveshark.pathworks.framework.algorithm.def.AbstractAlgorithm;
import com.oliveshark.pathworks.framework.entities.Agent;
import com.oliveshark.pathworks.framework.grid.Grid;

public class NavigateTowardsDestinationAlgorithm extends AbstractAlgorithm {

    @Override
    public void operate(Grid grid, Agent agent) {
        Vector2 dest = agent.getDestination();
        Vector2 pos = new Vector2(agent.getX(), agent.getY());

        if (pos.equals(dest)) {
            return;
        }

        Vector2 direction = dest.sub(pos).nor().setLength(Math.min(100, pos.dst(dest)));
        agent.getVelocity().set(direction);
    }
}
