package net.sleepystudios.ld35.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.sleepystudios.ld35.LD35;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "LD35";
		config.resizable = false;
		config.width = 1064;
		config.height = 600;
		config.foregroundFPS = 60;
		
		new LwjglApplication(new LD35(), config);
	}
}
