package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.View.loadingScreen.LoadingScreen;

public class SteampunkGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public AssetManager assetManager;
	public OrthographicCamera camera;
	private LoadingScreen loadingScreen;

	public static final float VIEW_WIDTH = 32;
	public static final float VIEW_HEIGHT = 18;
	public static final float PPT = 16;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.font = new BitmapFont();
		this.assetManager = new AssetManager();
		this.assetManager.setLoader(TiledMap.class, new TmxMapLoader());
		this.camera = new OrthographicCamera(VIEW_WIDTH, VIEW_HEIGHT);
		this.camera.setToOrtho(false, VIEW_WIDTH, VIEW_HEIGHT);
		this.loadingScreen = new LoadingScreen(this);
		this.setScreen(this.loadingScreen);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		this.batch.dispose();
		this.loadingScreen.dispose();
		this.assetManager.dispose();
	}
}
