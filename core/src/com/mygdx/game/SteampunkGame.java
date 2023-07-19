package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.View.mainMenu.MainMenuScreen;

public class SteampunkGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public Json json;
	private MainMenuScreen mainMenuScreen;

	public static final float GAME_WIDTH = 2400;
	public static final float GAME_HEIGHT = 1430;
	public static final float VIEW_WIDTH = 160;
	public static final float VIEW_HEIGHT = 90;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.font = new BitmapFont();
		this.json = new Json();
		this.mainMenuScreen = new MainMenuScreen(this);
		this.setScreen(this.mainMenuScreen);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		this.batch.dispose();
		this.mainMenuScreen.dispose();
	}
}
