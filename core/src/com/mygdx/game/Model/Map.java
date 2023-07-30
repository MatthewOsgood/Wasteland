package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.enums.TiledMapPath;

public class Map {
    private final SteampunkGame game;
    private final TiledMap tiledMap;
    private final Player player;
    private final Array<NPC> npcs;
    private final Array<Projectile> projectiles;

    public Map(final SteampunkGame game, TiledMapPath path, Player player) {
        this.game = game;
        this.tiledMap = game.assetManager.get(path.getPath());
        this.player = player;
        this.npcs = new Array<NPC>();
        this.projectiles = new Array<Projectile>();
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

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
    }

    public void update() {
        for (Projectile p : this.projectiles) {
            p.update();
        }
    }
}
