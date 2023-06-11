package com.mygdx.game;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;
import org.json.JSONObject;


public class Client {
    Socket socket;
    OutputStream out;
    InputStream in;

    JSONObject jsonObject;

    public Client(){

        jsonObject = new JSONObject();
        jsonObject.put("warrior", 1);
        jsonObject.put("mage", 1);
        jsonObject.put("passives", "attack");
    }

    public void connect(String address, int port){
        try {
            socket = new Socket(address, port);
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendToServer(){
        String jsonData = jsonObject.toString();
        byte[] jsonDataBytes = jsonData.getBytes(StandardCharsets.UTF_8);
        String response = null;

        Scanner scanncer = new Scanner(System.in);
        String armyInformation = scanncer.nextLine();
        try {
            out.write(jsonDataBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getFromServer(){

        byte[] buffer = new byte[1000];

        int bytesRead = 0;
        try {
            bytesRead = in.read(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String jasonData = new String(buffer,0, bytesRead, StandardCharsets.UTF_8);
        jsonObject = new JSONObject(jasonData);
        System.out.println(jasonData);
    }



};


