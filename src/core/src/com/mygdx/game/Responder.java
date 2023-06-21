package com.mygdx.game;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.json.JSONObject;

public class Responder implements Runnable {
    
    private Socket socket;
    private InputStream in;
    private JSONObject dataJson;
    private Mercenary_camp mercenary_camp = new Mercenary_camp();
    private Army enemyArmy = new Army();
    private String enemyName;
    private boolean first;
    private boolean respond = false;
    private boolean game = false;
    private boolean nextTurn = false;
    private boolean gameWon = false;

    Responder(Socket socket){
        this.socket = socket;
        try {
            in = socket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }

    @Override
    public void run(){
        while(true){
            byte[] buffer = new byte[1000];
            int bytesRead;
            try {
                bytesRead = in.read(buffer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        String jasonData = new String(buffer,0, bytesRead, StandardCharsets.UTF_8);
        dataJson = new JSONObject(jasonData);
        if(Objects.equals(dataJson.getString("operation"), "refresh")){
            String key;
            int id;
            mercenary_camp.mercenary_camp.clear();
            for(int i = 0; i<5; i++){
                key = String.valueOf(i);
                id = dataJson.getInt(key);
                mercenary_camp.addMercenary(new Mercenary(id), i);
            }
            respond = true;
        }
        if(Objects.equals(dataJson.getString("operation"), "end")){
            String key;
            enemyArmy.army.clear();
            int n = dataJson.getInt("size");
            JSONObject data = new JSONObject();
            for(int i = 0; i<n; i++){
                key = String.valueOf(i);
                data = dataJson.getJSONObject(key);
                enemyArmy.army.add(i, new Mercenary(data.getInt("id"), data.getInt("speed"), data.getInt("attack"), data.getInt("defense")));
            }
            enemyName = dataJson.getString("player");
            if(dataJson.getInt("start")==1){
                this.first = true;
            }
            else{
                this.first = false;
            }
            this.respond = true;
        }
        if(Objects.equals(dataJson.getString("operation"), "startGame")){
            this.game = true;
        }
        if(Objects.equals(dataJson.getString("operation"), "nextTurn")){
            this.nextTurn = true;
        }
        if(Objects.equals(dataJson.getString("operation"), "winGame")){
            this.gameWon = true;
        }
        }
    }

    public Mercenary_camp getMerceneryCamp(){
    return this.mercenary_camp;
}
    public Army getEnemyArmy(){
        return this.enemyArmy;
    }

    public String getEnemyName(){
        return this.enemyName;
    }

    public boolean getAttackPriority(){
        return this.first;
    }

    public synchronized boolean getReposndStatus(){
        return this.respond;
    }

    public void resetRespondStatus(){
        this.respond = false;
    }

    public synchronized boolean isGameStarted(){
        return this.game;
    }

    public synchronized boolean isNextTurn(){
        return this.nextTurn;
    }

    public synchronized boolean isGameWon(){
        return this.gameWon;
    }
}



