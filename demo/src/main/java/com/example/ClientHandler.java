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

    private boolean gameDone = false;

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
            if(this.gameDone){
                jsonData.clear();
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
                jsonData.put("operation", "winGame");
                String jsonString = jsonData.toString();
                byte[] jsonDataBytes = jsonString.getBytes(StandardCharsets.UTF_8);
                try {
                    out.write(jsonDataBytes);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
                } catch (InterruptedException e) {
                }
                System.out.println(jsonData);
                String jsonString = jsonData.toString();
                byte[] jsonDataBytes = jsonString.getBytes(StandardCharsets.UTF_8);
                try {
                    out.write(jsonDataBytes);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                continue;
            }
            if(Objects.equals(jsonData.getString("operation"), "wait")){
                try {
                    barrier.await();
                } catch (BrokenBarrierException e) {
                } catch (InterruptedException e) {

                }
                jsonData.clear();
                jsonData.put("operation", "startGame");
                String jsonString = jsonData.toString();
                byte[] jsonDataBytes = jsonString.getBytes(StandardCharsets.UTF_8);
                try {
                    out.write(jsonDataBytes);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                continue;
            }
            if(Objects.equals(jsonData.getString("operation"), "endBattle")){
                if(jsonData.getInt("hp")==0){
                    this.gameDone = true;
                    try {
                        barrier.await();
                    } catch (BrokenBarrierException e) {
                    } catch (InterruptedException e) {
                    }
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                else{
                    try {
                        barrier.await();
                    } catch (BrokenBarrierException e) {
                    } catch (InterruptedException e) {
                    }
                    jsonData.clear();
                    jsonData.put("operation", "nextTurn");
                    String jsonString = jsonData.toString();
                    byte[] jsonDataBytes = jsonString.getBytes(StandardCharsets.UTF_8);
                    try {
                        out.write(jsonDataBytes);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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

    void  setCyclicBarrier(CyclicBarrier barrier){
        this.barrier = barrier;
    }

    public boolean isGameDone(){
        return this.gameDone;
    }

    public void EndGame(){
        this.gameDone = true;
    }
}