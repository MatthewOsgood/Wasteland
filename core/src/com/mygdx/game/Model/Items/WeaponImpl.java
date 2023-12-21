package com.mygdx.game.Model.Items;

public abstract class WeaponImpl implements Weapon {

    /**
     * time in seconds between attacks
     */
    private final float ATTACK_COOLDOWN;
    private float timeSinceLastAttack;

    protected WeaponImpl(float attackCooldown) {
        ATTACK_COOLDOWN = attackCooldown;
        this.timeSinceLastAttack = 0f;
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
