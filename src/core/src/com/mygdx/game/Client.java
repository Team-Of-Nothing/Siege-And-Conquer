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
        // jsonObject.put("warrior", 1);
        // jsonObject.put("mage", 1);
        // jsonObject.put("passives", "attack");

        //Mercenary wojak = new Mercenary(1);
        // 0 - 15, 15 - 40
        jsonObject.put("a", 1);
        jsonObject.put("b", 2);
        jsonObject.put("c", 2);

        

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
        System.out.println(jsonData);

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


