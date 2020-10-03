package com.oliveshark.pathworks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.oliveshark.pathworks.framework.ViewStage;
import net.spookygames.gdx.nativefilechooser.NativeFileChooser;

public class Pathworks extends ApplicationAdapter {
	private ViewStage viewStage;

	private NativeFileChooser nativeFileChooser;

	public Pathworks(NativeFileChooser nativeFileChooser) {
		this.nativeFileChooser = nativeFileChooser;
	}

	@Override
	public void create () {
	    viewStage = new ViewStage(nativeFileChooser);
	    Gdx.input.setInputProcessor(viewStage);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		viewStage.act(Gdx.graphics.getDeltaTime());
		viewStage.draw();
	}
	
	@Override
	public void dispose () {
		viewStage.dispose();
	}
}
