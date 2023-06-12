package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SAC extends Game {
	public SpriteBatch batch;
	private Client client = new Client();
	private Player player = new Player("Venom");

	@Override
	public void create () {
		player.getArmy().add(0, new Mercenary(1));
		player.getArmy().add(1, new Mercenary(2));
		player.getArmy().add(2,new Mercenary(3));
		try {
			client.connect("localhost", 2137);
			Responder responder = new Responder(client.socket);
			Thread thread = new Thread(responder);
			thread.start();
			// client.endTurn(player.getArmy(), player.getName());
			client.endTurn(player.getArmy(), player.getName());
			//refresh dziala jak szef :)
			// client.refreshShop();
			// client.refreshShop();
			// client.refreshShop();
		} catch (Exception e) {
			System.out.println("Connection failed");
		}
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
