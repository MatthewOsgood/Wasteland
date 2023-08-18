package com.mygdx.game.Model.ai.pfa;

import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MapConnection extends DefaultConnection<MapNode> {

    public MapConnection(MapNode fromNode, MapNode toNode) {
        super(fromNode, toNode);
    }
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.line(this.fromNode.getWorldPosition(), this.toNode.getWorldPosition());
    }
}
