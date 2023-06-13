package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.json.JSONArray;

// mvn exec:java -Dexec.mainClass=com.example.Server
//  komenda do odpalenia tego gówna

public class Server {

    private int port;

    private ServerSocket serverSocket;
    private CyclicBarrier cyclicBarrier;

    private int numberOfPlayers;
    JSONArray playersData = new JSONArray();

    Server(int port, int number_of_players){
        this.port=port;
        this.numberOfPlayers = number_of_players;
    }

    public void start(){
        cyclicBarrier = new CyclicBarrier(numberOfPlayers+1);
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port "+port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() throws IOException {

        Socket[] clientSockets = new Socket[numberOfPlayers];
        Thread[] threads = new Thread[numberOfPlayers];
        ClientHandler[] clientHandlers = new ClientHandler[numberOfPlayers];
        for(int i = 0; i<numberOfPlayers; i++){
            clientSockets[i] = serverSocket.accept();
            System.out.println("Client"+(i+1)+"connected!");
            clientHandlers[i] = new ClientHandler(clientSockets[i], cyclicBarrier);
            threads[i] = new Thread(clientHandlers[i]);
            threads[i].start();
        }

        while(true){

            while(cyclicBarrier.getNumberWaiting()!=numberOfPlayers){
                continue;
            }

            //getting array of json objects, each json object contains data about army and obtained passives
            for(int i = 0; i<numberOfPlayers; i++){
                playersData.put(clientHandlers[i].getPlayerInformation());
            }
            System.out.println(playersData);
            //DO WHOLE LOGIC HERE :( >>>
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            cyclicBarrier.reset();
        }

    }

    public static void main(String[] args) {
        Server server = new Server(2137,2);
        server.start();
        try {
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}




