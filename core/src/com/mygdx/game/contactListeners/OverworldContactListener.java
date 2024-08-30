package com.mygdx.game.contactListeners;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Model.Entities.Movable;
import com.mygdx.game.Model.Entities.NPC;
import com.mygdx.game.Model.Entities.Player;
import com.mygdx.game.Model.Entities.Projectile;

public class OverworldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (this.handleProjectileCollision(a, b)) return;
        if (this.handleProjectileCollision(b, a)) return;

        if (a.isSensor() && a.getUserData() instanceof NPC && b.getUserData() instanceof Player) {
            ((Player) b.getUserData()).setInteract((NPC) a.getUserData());
            return;
        }
        if (b.isSensor() && b.getUserData() instanceof NPC && a.getUserData() instanceof Player) {
            ((Player) a.getUserData()).setInteract((NPC) b.getUserData());
        }
    }

    private boolean handleProjectileCollision(Fixture a, Fixture b) {
        if (a.getUserData() instanceof Projectile) {
            ((Projectile<?>) a.getUserData()).setToDestroy();
            if (b.getUserData() instanceof Movable) {
                ((Movable<?>) b.getUserData()).damage(((Projectile<?>) a.getUserData()).getDamage());
            }
            return true;
        }
        return false;
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
