package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.View.mainMenu.MainMenuScreen;

public class SteampunkGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	private MainMenuScreen mainMenuScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.font = new BitmapFont();
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
