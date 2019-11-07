package com.oliveshark.pathworks.framework.algorithm.def;

import com.oliveshark.pathworks.framework.entities.Agent;
import com.oliveshark.pathworks.framework.grid.Grid;

public abstract class AbstractAlgorithm {

    public abstract void operate(Grid grid, Agent agent);
}
