package com.mygdx.game.Model.Items.Weapons;

public abstract class BasicGun implements Weapon {

    /**
     * time in seconds between attacks
     */
    private final float ATTACK_COOLDOWN;
    private float timeSinceLastAttack;

    protected BasicGun(float attackCooldown) {
        ATTACK_COOLDOWN = attackCooldown;
        this.timeSinceLastAttack = attackCooldown;
    }

    /**
     * @return if the weapon can be fired
     */
    @Override
    public boolean canShoot() {
        return this.timeSinceLastAttack >= this.ATTACK_COOLDOWN;
    }

    /**
     * must be called every frame
     *
     * @param delta the amount of time that has passed between frames
     */
    @Override
    public void update(float delta) {
        this.timeSinceLastAttack += delta;
    }

    @Override
    public void resetCooldown() {
        this.timeSinceLastAttack = 0f;
    }
}
