package com.mygdx.game.Model.ai;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * utility for vector-angle conversion
 */
public final class SteeringUtils {

    public static float vectorToAngle(Vector2 vector) {
        return MathUtils.atan2(vector.y, vector.x);
    }

    public static Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = -MathUtils.sin(angle);
        outVector.y = MathUtils.cos(angle);
        return outVector;
    }

}
