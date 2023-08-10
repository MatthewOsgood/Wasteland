package com.mygdx.game;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;


// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setTitle("Steampunk-Game");
		Graphics.DisplayMode desktopMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
		config.setForegroundFPS(desktopMode.refreshRate);
		config.setMaximized(true);
		new Lwjgl3Application(new SteampunkGame().setRefreshRate(desktopMode.refreshRate), config);
	}
}
