package com.mygdx.game.contactListeners;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Model.NPC;
import com.mygdx.game.Model.Player;

public class InteractListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (a.isSensor() && a.getUserData() instanceof NPC && b.getUserData() instanceof Player) {
            ((Player) b.getUserData()).setInteract((NPC) a.getUserData());
        } else if (b.isSensor() && b.getUserData() instanceof NPC && a.getUserData() instanceof Player) {
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
