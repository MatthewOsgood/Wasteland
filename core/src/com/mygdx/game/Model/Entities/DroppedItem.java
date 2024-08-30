package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.enums.Items;

public class DroppedItem {

    private final Items item;

    private final int count;

    private final Body reachBox;

    public DroppedItem(Items item, int count, Vector2 pos, float width, float height, World world) {
        this.item = item;
        this.count = count;
        this.reachBox = this.createReachBox(pos, width, height, world);
    }

    private Body createReachBox(Vector2 pos, float width, float height, World world) {
        return this.createReachBox(pos.x, pos.y, width, height, world);
    }

    private Body createReachBox(float x, float y, float width, float height, World world) {
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.KinematicBody;
        bDef.position.set(new Vector2(x , y ));
        bDef.fixedRotation = true;
        bDef.linearDamping = 0f;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
        Body body = world.createBody(bDef);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.isSensor = true;
        body.createFixture(fDef).setUserData(this);
        shape.dispose();
        return body;
    }
}
