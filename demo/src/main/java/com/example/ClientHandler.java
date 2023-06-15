package com.example;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import org.json.JSONObject;

import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ClientHandler implements Runnable {

    private boolean response;
    private OutputStream out;
    private InputStream in;
    private JSONObject jsonData;
    private Socket clientSocket;

    private CyclicBarrier barrier;

    private int turn = 1;

    private int pull = 6;

    ClientHandler(Socket socket, CyclicBarrier barrier) {
        this.clientSocket = socket;
        this.barrier = barrier;
        try {
            out = socket.getOutputStream();
            in = socket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1000];
        while (clientSocket.isConnected()) {
            if(turn % 2 == 0){
                this.pull+=2;
            }
            int bytesRead;
            try{
                bytesRead = in.read(buffer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String jasonData = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
            jsonData = new JSONObject(jasonData);
            System.out.println(jsonData);
            if (Objects.equals(jsonData.getString("operation"), "refresh")) {
                Random random = new Random();
                int size = jsonData.getInt("size");
                jsonData.clear();
                jsonData.put("operation", "refresh");
                for (int i = 0; i < size; i++) {
                    jsonData.put(String.valueOf(i), random.nextInt(pull) + 1);
                }
                    String jsonString = jsonData.toString();
                    byte[] jsonDataBytes = jsonString.getBytes(StandardCharsets.UTF_8);
                    try {
                        out.write(jsonDataBytes);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                continue;
            }
            if(Objects.equals(jsonData.getString("operation"), "end")){
                try {
                    barrier.await();
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String jsonString = jsonData.toString();
                byte[] jsonDataBytes = jsonString.getBytes(StandardCharsets.UTF_8);
                try {
                    out.write(jsonDataBytes);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            turn++;

        }

    }

    public JSONObject getPlayerInformation(){
        return jsonData;
    }

    void setPlayerInformation(JSONObject jsonData){
        this.jsonData = jsonData;
    }
}
