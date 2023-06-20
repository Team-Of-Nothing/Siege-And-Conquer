package com.mygdx.game;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SAC extends Game {
	public SpriteBatch batch;
	private Client client = new Client();
	private final static String DEFAULT_BACKGROUND_MUSIC = "castle.mp3";
	public Music backgroundMusic; //nie moze byc static bo create() nie jest
	private Player player = new Player("Player");

	@Override
	public void create () {
		player.getArmy().add(0, new Mercenary(1));
		player.getArmy().add(1, new Mercenary(5));
		player.getArmy().add(2,new Mercenary(6));
		player.defeat();
		player.defeat();
		player.defeat();
		player.defeat();
		player.defeat();
		try {
			client.connect("localhost", 2137);
			Responder responder = new Responder(client.socket);
			Thread thread = new Thread(responder);
			thread.start();
			client.enterLobby();
			System.out.println("waiting for players");
			while(responder.isGameStarted()==false){
				continue;
			}
			System.out.println("game started");
			client.endTurn(player.getArmy(), player.getName());
			while(responder.getReposndStatus()!=true){
			}
			responder.resetRespondStatus();
			Army army = responder.getEnemyArmy();
			System.out.println(responder.getEnemyName());
			for(int i = 0; i<responder.getEnemyArmy().army.size(); i++){
				System.out.println(army.army.get(i).getId());
				System.out.println(army.army.get(i).getAttack());
				System.out.println(army.army.get(i).getDefense());
				System.out.println(army.army.get(i).getSpeed());
				System.out.println();
			}
			client.endBattle(player.getHp());
			while(responder.isNextTurn()==false){
				continue;
			}
			System.out.println("nastepna tura sie zaczyna :)");
			client.endTurn(player.getArmy(), player.getName());

			while(responder.getReposndStatus()!=true){
			}
			responder.resetRespondStatus();
			army = responder.getEnemyArmy();
			System.out.println(responder.getEnemyName());
			for(int i = 0; i<responder.getEnemyArmy().army.size(); i++){
				System.out.println(army.army.get(i).getId());
				System.out.println(army.army.get(i).getAttack());
				System.out.println(army.army.get(i).getDefense());
				System.out.println(army.army.get(i).getSpeed());
				System.out.println();
			}
			
		} catch (Exception e) {
			System.out.println("Connection failed");
		}
		batch = new SpriteBatch();
		setScreen(new MainMenu(this));
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(DEFAULT_BACKGROUND_MUSIC));
		backgroundMusic.play();
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
