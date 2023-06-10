package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// mvn exec:java -Dexec.mainClass=com.example.Server
//  komenda do odpalenia tego gówna

public class Server {

    private int port;

    private ServerSocket serverSocket;
    private CyclicBarrier cyclicBarrier;

    private int numberOfPlayers;

    private String[] armyStats;

    Server(int port, int number_of_players){
        this.port=port;
        this.numberOfPlayers = number_of_players;
    }

    public void start(){
        armyStats = new String[numberOfPlayers];
        cyclicBarrier = new CyclicBarrier(numberOfPlayers+1);

        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port x");
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
            clientHandlers[i] = new ClientHandler(clientSockets[i], cyclicBarrier, armyStats[i]);
            threads[i] = new Thread(clientHandlers[i]);
            threads[i].start();
        }

        while(true){

            while(cyclicBarrier.getNumberWaiting()!=numberOfPlayers){
                continue;
            }
            for(int i = 0; i<numberOfPlayers; i++){
                armyStats[i]=clientHandlers[i].getArmyInfomration();
            }
            for(int i = 0; i<numberOfPlayers; i++){
                System.out.println(armyStats[i]);
            }
            //some calculations about battle who wins and opponets army to let client display the battle
            for(int i = 0; i<numberOfPlayers; i++){
                clientHandlers[i].setArmyInfomration("player "+i+1);
            }
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




