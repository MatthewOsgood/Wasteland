package com.mygdx.game.Model.ai.pfa;

import com.badlogic.gdx.ai.pfa.DefaultConnection;

public class MapConnection extends DefaultConnection<MapNode> {

    private final MapGraph graph;
    public MapConnection(MapNode fromNode, MapNode toNode, MapGraph graph) {
        super(fromNode, toNode);
        this.graph = graph;

    }
}
