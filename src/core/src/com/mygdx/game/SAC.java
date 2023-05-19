package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class SAC extends ApplicationAdapter {
	SpriteBatch batch;

	
	@Override
	public void create () {
		batch = new SpriteBatch();

	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.BLACK);
		batch.begin();

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
