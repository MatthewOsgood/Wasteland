package com.mygdx.game.Model.ai.pfa;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * tutorial: <a href="https://happycoding.io/tutorials/libgdx/pathfinding#gdxai">...</a>
 */
public class MapGraph implements IndexedGraph<MapNode> {

    private final IndexedAStarPathFinder<MapNode> pathFinder;
    private final Heuristic<MapNode> heuristic;
    private final Array<MapNode> nodes;
    private int nodeCount;
    private final SmoothablePath path;

    public MapGraph(Heuristic<MapNode> heuristic, TiledMap tiledMap) {
        this.heuristic = heuristic;
        this.nodes = new Array<MapNode>();
        this.nodeCount = 0;
        this.path = new SmoothablePath();
        this.init(tiledMap);
        this.pathFinder = new IndexedAStarPathFinder<MapNode>(this);
    }

    private void init(TiledMap tiledMap) {
        TiledMapTileLayer tileLayer;
        TiledMapTileLayer.Cell cell;
        for (MapLayer layer : tiledMap.getLayers()) {
            if (layer instanceof TiledMapTileLayer) {
                tileLayer = (TiledMapTileLayer) layer;
                for (int y = 0; y < tileLayer.getWidth(); y++) {
                    for (int x = 0; x < tileLayer.getHeight(); x++) {
                        cell = tileLayer.getCell(x, y);
                        if (cell.getTile().getProperties().get("collision") == Boolean.FALSE) {
                            MapNode node = new MapNode(x, y);
                            this.addNode(node);
                            this.connectToAdjacent(node);
                        }
                    }
                }
            } else {
                break;
            }
        }
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

    /**
     * connects the two nodes together in both directions
     *
     * @param node1 the first node to be connected
     * @param node2 the second node to be connected
     */
    public void connectNodes(MapNode node1, MapNode node2) {
        node1.addConnection(new MapConnection(node1, node2));
        node2.addConnection(new MapConnection(node2, node1));
    }

    public SmoothablePath findPath(MapNode startNode, MapNode endNode) {
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

    /**
     * gets all nodes adjacent (by x,y coords) to the given node
     * returns null if the node is not in this graph
     *
     * @param node the node whose adjacent nodes will be gotten
     * @return the nodes that are adjacent to the given node
     */
    public Array<MapNode> getAdjacent(MapNode node) {
        Array<MapNode> ans = new Array<MapNode>(4);
        if (!this.nodes.contains(node, true)) {
            return ans;
        }
        for (MapNode toCheck : this.nodes) {
            if (node.isAdjacentTo(toCheck)) {
                ans.add(toCheck);
            }
        }
        return ans;
    }

    /**
     * connects the given node to all adjacent nodes
     *
     * @param node the node that will be connected
     */
    public void connectToAdjacent(MapNode node) {
        for (MapNode adjacent : this.getAdjacent(node)) {
            this.connectNodes(node, adjacent);
        }
    }

    /**
     * gets the node of the tile that contains the given position
     *
     * @param position the position on the map
     * @return the node that contains the given position
     */
    public MapNode getNodeAt(Vector2 position) {
        for (MapNode node : this.nodes) {
            if (Math.abs(node.getWorldPosition().x - position.x) <= .5f && Math.abs(node.getWorldPosition().y - position.y) <= .5f) {
                return node;
            }
        }
        throw new IllegalArgumentException("Position " + position.toString() + " is not on the map");
    }

    public void draw(ShapeRenderer shapeRenderer) {
        for (MapNode node : this.nodes) {
            node.draw(shapeRenderer);
        }
    }
}
