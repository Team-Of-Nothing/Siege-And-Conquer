
package com.example;

import java.io.IOException;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        Server server = new Server(2137,4);
        server.start();
        try {
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

