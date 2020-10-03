package com.oliveshark.pathworks.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.oliveshark.pathworks.Pathworks;
import net.spookygames.gdx.nativefilechooser.desktop.DesktopFileChooser;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 768;
		new LwjglApplication(new Pathworks(new DesktopFileChooser()), config);
	}
}
