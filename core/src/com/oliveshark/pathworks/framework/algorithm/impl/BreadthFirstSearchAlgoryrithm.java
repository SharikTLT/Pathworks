package com.oliveshark.pathworks.framework.algorithm.impl;

import com.badlogic.gdx.math.Vector2;
import com.oliveshark.pathworks.framework.algorithm.def.AbstractAlgorithm;
import com.oliveshark.pathworks.framework.algorithm.def.AgentUserData;
import com.oliveshark.pathworks.framework.entities.Agent;
import com.oliveshark.pathworks.framework.grid.Grid;

import java.util.*;

import static com.oliveshark.pathworks.framework.grid.Grid.getGridPositionFromStagePosition;
import static com.oliveshark.pathworks.framework.grid.Grid.getStagePositionFromGridPosition;
import static java.util.Arrays.asList;

/**
 * Basic path finding algorithm
 * <a href="https://en.wikipedia.org/wiki/Breadth-first_search">Wiki</a>
 */
public class BreadthFirstSearchAlgoryrithm extends AbstractAlgorithm {

    public static final Vector2 UP_VECTOR = new Vector2(0, 1);
    public static final Vector2 RIGHT_VECTOR = new Vector2(1, 0);
    public static final Vector2 DOWN_VECTOR = new Vector2(0, -1);
    public static final Vector2 LEFT_VECTOR = new Vector2(-1, 0);

    @Override
    public void operate(Grid grid, Agent agent) {
        if (agent.getUserData() == null) {
            agent.setUserData(new BFSAgentData());
            findPath(grid, agent);
        }
        BFSAgentData userData = (BFSAgentData) agent.getUserData();
        if (userData.getPath().size() == 0) {
            return;
        }
        Vector2 destGrid = userData.getPath().peek();
        Vector2 dest = new Vector2(getStagePositionFromGridPosition(destGrid));
        Vector2 pos = new Vector2(agent.getX(), agent.getY());

        if (dest.dst(pos) < 1) {
            userData.getPath().pop();
            agent.getVelocity().set(0, 0);
            agent.setX(dest.x);
            agent.setY(dest.y);
            return;
        }

        Vector2 direction = new Vector2(dest).sub(pos).setLength(100);
        agent.getVelocity().set(direction);//.setLength(0);
    }

    private void findPath(Grid grid, Agent agent) {
        Vector2 start = getGridPositionFromStagePosition(agent.getX(), agent.getY());
        Vector2 end = getGridPositionFromStagePosition(agent.getDestination().x, agent.getDestination().y);

        List<Vector2> searchVectors = asList(UP_VECTOR, RIGHT_VECTOR, DOWN_VECTOR, LEFT_VECTOR);
        Queue<Vector2> locationQueue = new LinkedList<>();
        locationQueue.add(start);
        Map<Vector2, Vector2> visited = new HashMap<>();
        while (locationQueue.size() > 0) {
            Vector2 current = locationQueue.poll();
            if (grid.isOutsideGrid(current)) {
                //skip outside cells
                continue;
            }
            if (current.equals(end)) {
                locationQueue.clear();
                break;
            }
            if (grid.isCellOccupied(current)) {
                visited.put(current, null);
                continue;
            }
            searchVectors.forEach(search -> {
                Vector2 nextLocation = new Vector2(current).add(search);
                if (!visited.containsKey(nextLocation) && !nextLocation.equals(current)) {
                    visited.put(nextLocation, current);
                    locationQueue.add(nextLocation);
                }
            });
        }

        if (visited.containsKey(end)) {
            Stack<Vector2> path = ((BFSAgentData) agent.getUserData()).getPath();
            path.add(end);
            Vector2 next = visited.get(end);
            while (next != null && !next.equals(start)) {
                path.push(next);
                next = visited.get(next);
            }
        }

    }


    public static class BFSAgentData implements AgentUserData {
        private Stack<Vector2> path = new Stack<>();

        public Stack<Vector2> getPath() {
            return path;
        }

        @Override
        public String toString() {
            return "BFSAgentData{" +
                    "path=" + path +
                    '}';
        }
    }
}
