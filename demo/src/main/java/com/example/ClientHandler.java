package com.example;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
// import org.json.JSONObject;
import java.util.Timer;
import java.util.TimerTask;
public class ClientHandler implements Runnable {

    private boolean response;
    private OutputStream out;
    private InputStream in;
    Socket clientSocket;

    private CyclicBarrier barrier;

    private String armyInfomration;
    ClientHandler(Socket socket, CyclicBarrier barrier, String stats){
        this.clientSocket = socket;
        this.barrier = barrier;
        this.armyInfomration = stats;
        try{
            out = socket.getOutputStream();
            in = socket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
            byte[] buffer = new byte[1000];
            // JSONObject jsonObject;
            while(clientSocket.isConnected()){

                try {
                    int bytesRead = in.read(buffer);
                    // String jasonData = new String(buffer,0, bytesRead, StandardCharsets.UTF_8);
                    // jsonObject = new JSONObject(jasonData);


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    barrier.await();
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

    }

    public String getArmyInfomration(){
        return armyInfomration;
    }

    public void setArmyInfomration(String armyInfomration){
        this.armyInfomration = armyInfomration;
    }
}
