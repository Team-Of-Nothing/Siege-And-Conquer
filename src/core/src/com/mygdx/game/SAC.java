package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SAC extends Game {
	public SpriteBatch batch;
	//private Client client = new Client();

	public SAC(){
		//client.connect("20.117.180.142", 2137);
		//sendDataToServer();
		
	}
	
	public void sendDataToServer() {
		//client.sendToServer();

	}


	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainMenu(this));

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
