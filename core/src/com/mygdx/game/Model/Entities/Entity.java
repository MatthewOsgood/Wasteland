package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 * represents any character on the map
 */
public interface Entity extends Disposable {

    void setPosition(float x, float y);
    void setVelocity(float x, float y);
    void setVelocity(Vector2 v);
    void draw(SpriteBatch batch);
    Vector2 getVelocity();
    void setToDestroy();
}
