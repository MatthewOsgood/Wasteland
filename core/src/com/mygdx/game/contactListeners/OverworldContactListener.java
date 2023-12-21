package com.mygdx.game.contactListeners;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Model.Entities.Movable;
import com.mygdx.game.Model.Entities.NPC;
import com.mygdx.game.Model.Entities.Player.Player;
import com.mygdx.game.Model.Entities.Projectile;

public class OverworldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (a.getUserData() instanceof Projectile) {
            ((Projectile) a.getUserData()).setToDestroy();
            if (b.getUserData() instanceof Movable) {
                ((Movable) b.getUserData()).damage(((Projectile) a.getUserData()).getDamage());
            }
            return;
        }
        if (b.getUserData() instanceof Projectile) {
            ((Projectile) b.getUserData()).setToDestroy();
            if (a.getUserData() instanceof Movable) {
                ((Movable) a.getUserData()).damage(((Projectile) b.getUserData()).getDamage());
            }
            return;
        }

        if (a.isSensor() && a.getUserData() instanceof NPC && b.getUserData() instanceof Player) {
            ((Player) b.getUserData()).setInteract((NPC) a.getUserData());
            return;
        }
        if (b.isSensor() && b.getUserData() instanceof NPC && a.getUserData() instanceof Player) {
            ((Player) a.getUserData()).setInteract((NPC) b.getUserData());
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.isSensor() && a.getUserData() instanceof NPC && b.getUserData() instanceof Player) {
            ((Player) b.getUserData()).canInteract = false;
        } else if (b.isSensor() && b.getUserData() instanceof NPC && a.getUserData() instanceof Player) {
            ((Player) a.getUserData()).canInteract = false;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
