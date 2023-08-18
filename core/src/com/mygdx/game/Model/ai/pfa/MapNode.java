package com.mygdx.game.Model.ai.pfa;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class MapNode {

    private final Vector2 position;
    /**
     * the index of this tile
     */
    private int index;
    private final Vector2 worldPosition;
    private final Array<Connection<MapNode>> connections;
    public MapNode(float x, float y, Array<Connection<MapNode>> connections) {
        this.position = new Vector2(x, y);
        this.connections = connections;
        this.worldPosition = new Vector2(x + .5f, y + .5f);
    }

    public MapNode(float x, float y) {
        this(x, y, new Array<Connection<MapNode>>());
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * may be null if the index has not been set
     *
     * @return this nodes index
     */
    public int getIndex() {
        return this.index;
    }

    public Vector2 getPosition() {
        return this.position;
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

    /**
     * is this node connected to the given node
     *
     * @param node the node to check
     * @return if this node is connected to the given node
     */
    public boolean isConnectedTo(MapNode node) {
        for (Connection<MapNode> connection : this.connections) {
            if (connection.getToNode() == node) {
                return true;
            }
        }
        return false;
    }

    public boolean isAdjacentTo(MapNode n) {
        return Math.abs(this.getPosition().x - n.getPosition().x) == 1 && Math.abs(this.getPosition().y - n.getPosition().y) == 0 || Math.abs(this.getPosition().x - n.getPosition().x) == 0 && Math.abs(this.getPosition().y - n.getPosition().y) == 1;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.set(ShapeRenderer.ShapeType.Point);
        shapeRenderer.point(this.position.x + 0.5f, this.position.y + .5f, 0);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        for (Connection<MapNode> connection : this.connections) {
            if (connection instanceof MapConnection) {
                ((MapConnection) connection).draw(shapeRenderer);
            }
        }
    }

    public Vector2 getWorldPosition() {
        return this.worldPosition;
    }
}
