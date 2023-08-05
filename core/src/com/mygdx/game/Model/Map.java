package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.enums.TiledMapPath;

public class Map {
    private final SteampunkGame game;
    private final World world;
    private final TiledMap tiledMap;
    private Player player;
    private final Array<NPC> npcs;
    private final Array<Enemy> enemies;
    private final Array<Projectile> projectiles;
    private final Array<Projectile> toDestroy;

    public Map(final SteampunkGame game, World world, TiledMapPath path) {
        this.game = game;
        this.world = world;
        this.tiledMap = game.assetManager.get(path.getPath());
        this.npcs = new Array<NPC>();
        this.enemies = new Array<Enemy>();
        this.projectiles = new Array<Projectile>();
        toDestroy = new Array<Projectile>();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * draws this map and everything on it
     *
     * @param batch the {@link SpriteBatch} this map will be drawn on
     */
    public void draw(SpriteBatch batch) {
        this.player.draw(batch);
        for (NPC npc : this.npcs) {
            npc.draw(batch);
        }
        for (Enemy enemy : this.enemies) {
            enemy.draw(batch);
        }
        for (Projectile p : this.projectiles) {
            p.draw(batch);
        }
    }

    public void dispose() {
        this.tiledMap.dispose();
        for (NPC npc : this.npcs) {
            npc.dispose();
        }
        this.player.dispose();
        for (Projectile p : this.projectiles) {
            p.dispose();
        }
    }

    public void addNPC(NPC npc) {
        this.npcs.add(npc);
    }

    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
    }

    public void update() {
        for (Projectile p : this.toDestroy) {
            p.dispose();
            this.projectiles.removeValue(p, true);
        }
        this.toDestroy.clear();
        for (Enemy enemy : this.enemies) {
            enemy.update();
        }
        for (Projectile p : this.projectiles) {
            p.update();
        }
    }

    /**
     * sets the given Projectile to be destroyed after the simulation step ends
     *
     * @param projectile the projectile to be destroyed
     */
    public void setToDestroy(Projectile projectile) {
        this.toDestroy.add(projectile);
    }
}
