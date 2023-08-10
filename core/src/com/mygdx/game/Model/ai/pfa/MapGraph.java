package com.mygdx.game.Model.ai.pfa;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * tutorial: <a href="https://happycoding.io/tutorials/libgdx/pathfinding#gdxai">...</a>
 */
public class MapGraph implements IndexedGraph<MapNode> {

    private final IndexedAStarPathFinder<MapNode> pathFinder;
    private final Heuristic<MapNode> heuristic;
    private final Array<MapNode> nodes;
    private final Array<MapConnection> connections;
    private int nodeCount;
    private final GraphPath<MapNode> path;

    public MapGraph(Heuristic<MapNode> heuristic) {
        this.pathFinder = new IndexedAStarPathFinder<MapNode>(this);
        this.heuristic = heuristic;
        this.nodes = new Array<MapNode>();
        this.connections = new Array<MapConnection>();
        this.nodeCount = 0;
        this.path = new DefaultGraphPath<MapNode>();
    }

    /**
     * adds the given node to this graph and sets its index
     *
     * @param node the node to be added
     */
    public void addNode(MapNode node) {
        node.setIndex(this.nodeCount++);
        this.nodes.add(node);
    }

    public void connectNodes(MapNode fromNode, MapNode toNode) {
        MapConnection connection = new MapConnection(fromNode, toNode, this);
        fromNode.addConnection(connection);
        toNode.addConnection(connection);
        this.connections.add(connection);
    }

    public GraphPath<MapNode> findPath(MapNode startNode, MapNode endNode) {
        this.path.clear();
        this.pathFinder.searchNodePath(startNode, endNode, this.heuristic, this.path);
        return this.path;
    }

    /**
     * Returns the unique index of the given node.
     *
     * @param node the node whose index will be returned
     * @return the unique index of the given node.
     */
    @Override
    public int getIndex(MapNode node) {
        return node.getIndex();
    }

    /**
     * Returns the number of nodes in this graph.
     */
    @Override
    public int getNodeCount() {
        return this.nodeCount;
    }

    /**
     * Returns the connections outgoing from the given node.
     *
     * @param fromNode the node whose outgoing connections will be returned
     * @return the array of connections outgoing from the given node.
     */
    @Override
    public Array<Connection<MapNode>> getConnections(MapNode fromNode) {
        return fromNode.getConnections();
    }
}
