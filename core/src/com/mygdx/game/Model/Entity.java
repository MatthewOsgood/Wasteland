package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * represents any character on the map
 */
public interface Entity {

    void setPosition(float x, float y);
    void setVelocity(float x, float y);
    void setVelocity(Vector2 v);
    void draw(SpriteBatch batch);
    void dispose();
    Vector2 getCenter();
    Vector2 getVelocity();

}
