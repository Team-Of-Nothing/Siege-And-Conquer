
package com.example;

import java.io.IOException;

public class App 
{
    static int port = 2137;
    static int number_of_players = 2;
    public static void main( String[] args )
    {
        Server server = new Server(port,number_of_players);
        server.start();
        try {
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

