package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * represents any character on the map
 */
public interface Entity {

    void setPosition(float x, float y);
    void translate(float x, float y);
    void draw(SpriteBatch batch);
    void dispose();
    Vector2 getCenter();
    boolean canInteract(Character that);

}
