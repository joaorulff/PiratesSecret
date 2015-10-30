package com.monmouth.piratesecret.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.monmouth.game.PirateGame;
import com.monmouth.piratesecret.PirateScret;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//new LwjglApplication(new PirateScret(), config);
		new LwjglApplication(new PirateGame(), config);
	}
}
