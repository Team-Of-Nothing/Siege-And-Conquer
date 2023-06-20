package com.mygdx.game;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONObject;


public class Client {
    Socket socket;
    OutputStream out;
    InputStream in;

    JSONObject dataJson;

    public Client(){

        dataJson = new JSONObject();
    }

    public void connect(String address, int port){
        try {
            socket = new Socket(address, port);
            out = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void endTurn(ArrayList<Mercenary> army, String name){
        dataJson.clear();
        dataJson.put("operation", "end");
        dataJson.put("player", name);
        dataJson.put("size", army.size());
        for(int i = 0; i<army.size(); i++){
            JSONObject unit = new JSONObject();
            unit.put("id", army.get(i).getId());
            unit.put("attack", army.get(i).getAttack());
            unit.put("defense", army.get(i).getDefense());
            unit.put("speed", army.get(i).getSpeed());
            dataJson.put(String.valueOf(i),unit);
        }
        String jsonData = dataJson.toString();
        byte[] jsonDataBytes = jsonData.getBytes(StandardCharsets.UTF_8);
        String response = null;

        Scanner scanner = new Scanner(System.in);
        String armyInformation = scanner.nextLine();
        
        try {
            out.write(jsonDataBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }

    public void refreshShop(){
        dataJson.clear();
        byte[] buffer = new byte[1000];
        int bytesRead = 0;
        dataJson.clear();
        dataJson.put("operation", "refresh");
        dataJson.put("size", 5);
        String jsonData = dataJson.toString();
        byte[] jsonDataBytes = jsonData.getBytes(StandardCharsets.UTF_8);
        try {
            out.write(jsonDataBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
};


