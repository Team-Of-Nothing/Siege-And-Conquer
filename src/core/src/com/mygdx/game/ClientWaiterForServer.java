package com.mygdx.game;

import java.net.*;
import java.io.*;

// TODO OGOLNIE CALOSC

public class ClientWaiterForServer extends Thread {

    private final Socket socket;
    private final BufferedReader in;

    public ClientWaiterForServer(Socket socket, BufferedReader in) {
        this.socket = socket;
        this.in = in;
    }

    public void run() {
        String line;
        System.out.println("Listening..");
        //Use syntax notofication|time Listening...
        while (socket.isConnected()) {
            try {
                line = in.readLine();
            } catch (IOException e) {
                return;
            }
            System.out.println(line);
        }
        System.out.println("End...\n");
    }

}

