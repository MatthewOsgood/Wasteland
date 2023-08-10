package com.mygdx.game.Model.ai.pfa;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

public class MapNode {

    /** The x coordinate of this tile */
    private final int x;
    /** The y coordinate of this tile */
    private final int y;
    /**
     * the index of this tile
     */
    private int index;

    private final Array<Connection<MapNode>> connections;
    public MapNode(int x, int y, Array<Connection<MapNode>> connections) {
        this.x = x;
        this.y = y;
        this.connections = connections;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Array<Connection<MapNode>> getConnections() {
        return this.connections;
    }

    public void addConnection(MapConnection connection) {
        this.connections.add(connection);
    }

    public void addConnection(MapConnection... connections) {
        for (MapConnection connection : connections) {
            this.addConnection(connection);
        }
    }
}
