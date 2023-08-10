package com.mygdx.game.Model.ai.pfa;

import com.badlogic.gdx.ai.pfa.Heuristic;

/**
 * uses the Manhattan distance between nodes
 */
public class ManhattanHeuristic implements Heuristic<MapNode> {
    /**
     * Calculates an estimated cost to reach the goal node from the given node.
     *
     * @param startNode    the start node
     * @param endNode the end node
     * @return the estimated cost
     */
    @Override
    public float estimate(MapNode startNode, MapNode endNode) {
        return (endNode.getX() - startNode.getX()) + (endNode.getY() - startNode.getY());
    }
}
