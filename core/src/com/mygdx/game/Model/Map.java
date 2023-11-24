package com.mygdx.game.Model;

import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Model.ai.RaycastCollision;
import com.mygdx.game.Model.ai.pfa.ManhattanHeuristic;
import com.mygdx.game.Model.ai.pfa.MapGraph;
import com.mygdx.game.Model.ai.pfa.MapNode;
import com.mygdx.game.Model.ai.pfa.SmoothablePath;
import com.mygdx.game.Wasteland;
import com.mygdx.game.enums.TiledMapPath;

public class Map {
    private final Wasteland game;
    private final World world;
    private final TiledMap tiledMap;
    private Player player;
    private final Array<NPC> npcs;
    private final Array<Enemy<?>> enemies;
    private final Array<Projectile<?>> projectiles;
    private final Array<Movable<?>> toDestroy;
    private final MapGraph graph;
    private final PathSmoother<MapNode, Vector2> pathSmoother;

    public Map(final Wasteland game, World world, TiledMapPath path) {
        this.game = game;
        this.world = world;
        this.tiledMap = game.assetManager.get(path.getPath());
        this.npcs = new Array<>();
        this.enemies = new Array<Enemy<?>>();
        this.projectiles = new Array<Projectile<?>>();
        this.toDestroy = new Array<Movable<?>>();
        this.graph = new MapGraph(new ManhattanHeuristic(), this.tiledMap);
        this.pathSmoother = new PathSmoother<MapNode, Vector2>(new RaycastCollision(this.world));
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
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
        for (Enemy<?> enemy : this.enemies) {
            enemy.draw(batch);
        }
        for (Projectile<?> p : this.projectiles) {
            p.draw(batch);
        }
//        this.shapeRenderer.setProjectionMatrix(this.game.camera.combined);
//        this.shapeRenderer.setAutoShapeType(true);
//        this.shapeRenderer.begin();
//        this.graph.draw(this.shapeRenderer);
//        for (Enemy enemy : this.enemies) {
//            enemy.drawPath(this.shapeRenderer);
//        }
        this.shapeRenderer.end();
    }

    public void dispose() {
        this.tiledMap.dispose();
        for (NPC npc : this.npcs) {
            npc.dispose();
        }
        this.player.dispose();
        for (Projectile<?> p : this.projectiles) {
            p.dispose();
        }
        for (Enemy<?> e : this.enemies){
            e.dispose();
        }
    }

    public void addNPC(NPC npc) {
        this.npcs.add(npc);
    }

    public void addEnemy(Enemy<?> enemy) {
        this.enemies.add(enemy);
    }

    public TiledMap getTiledMap() {
        return this.tiledMap;
    }

    public void addProjectile(Projectile<?> projectile) {
        this.projectiles.add(projectile);
    }

    public void update() {
        for (Movable<?> m : this.toDestroy) {
            m.dispose();
            if (m instanceof Projectile) {
                this.projectiles.removeValue((Projectile<?>) m, true);
            } else if (m instanceof Enemy) {
                this.enemies.removeValue((Enemy<?>) m, true);
            }
        }
        this.toDestroy.clear();
        this.player.update();
        for (Enemy<?> enemy : this.enemies) {
            enemy.setPath(this.getWaypoints(enemy, this.player));
            enemy.update();
        }
        for (Projectile<?> p : this.projectiles) {
            p.update();
        }
    }

    /**
     * sets the given Projectile to be destroyed after the simulation step ends
     *
     * @param movable the movable to be destroyed
     */
    public void setToDestroy(Movable<?> movable) {
        this.toDestroy.add(movable);
    }

    /**
     * gets the path between the given Movables as a list of waypoints
     *
     * @param from the start position
     * @param to the end position
     * @return a list of points that lead to the end position
     */
    public Array<Vector2> getWaypoints(Movable<?> from, Movable<?> to) {
        SmoothablePath graphPath = this.graph.findPath(this.graph.getNodeAt(from.getPosition()), this.graph.getNodeAt(to.getPosition()));
        this.pathSmoother.smoothPath(graphPath);
        Array<Vector2> waypoints = new Array<Vector2>(graphPath.getCount());
        for (MapNode node : graphPath) {
            waypoints.add(node.getWorldPosition());
        }
        return waypoints;
    }
}
