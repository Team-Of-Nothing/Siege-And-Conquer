package com.mygdx.game;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SAC extends Game {
	public SpriteBatch batch;
	protected Client client = new Client();
	private final static String DEFAULT_BACKGROUND_MUSIC = "castle.mp3";
	public Music backgroundMusic; //nie moze byc static bo create() nie jest

	protected static Player player = new Player("Player");

	protected Player enemy = new Player("Enemy");

	protected Responder responder;


	@Override
	public void create () {

		batch = new SpriteBatch();
		setScreen(new MainMenu(this));
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(DEFAULT_BACKGROUND_MUSIC));
		backgroundMusic.play();
	}

	public Responder getResponder(){
		return responder;
	}

	@Override
	public void render () {
		super.render();

		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		backgroundMusic.dispose();
	}
}
