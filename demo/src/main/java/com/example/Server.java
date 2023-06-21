package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import org.json.JSONArray;

public class Server {
    private int port;
    private ServerSocket serverSocket;
    private CyclicBarrier cyclicBarrier;
    private Socket[] clientSockets;
    private Thread[] threads;
    private ClientHandler[] clientHandlers;
    private int numberOfPlayers;

    private int playersLeft = 0;
    JSONArray playersData = new JSONArray();

    Server(int port, int number_of_players) {
        this.port = port;
        this.numberOfPlayers = number_of_players;

    }

    public void start() {
        cyclicBarrier = new CyclicBarrier(numberOfPlayers + 1);
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void waitForClients(){
        for(int i = 0; i<numberOfPlayers; i++){
            if(!threads[i].isAlive()){
                updateClientsStatuses();
                i-=1;
                continue;
            }
            if(!(clientHandlers[i].isWaiting())){
                i=-1;
            }
        }
    }

    public void updateClientsStatuses(){
        for(int i = 0; i<numberOfPlayers; i++){
            if(!threads[i].isAlive()){
                for(int j = i; j<numberOfPlayers-1; j++){
                    threads[i]=threads[i+1];
                    clientSockets[i]=clientSockets[i+1];
                    clientHandlers[i]=clientHandlers[i+1];
                }
                numberOfPlayers-=1;
                i-=1;
            }

        }
    }

    public void resetClientFlags(){
        for(int i = 0; i<numberOfPlayers; i++){
            clientHandlers[i].resetWaiting();
        }
    }


    public void run() throws IOException {
        BattleSimulator battleSimulator = new BattleSimulator();
        this.clientSockets = new Socket[numberOfPlayers];
        this.threads = new Thread[numberOfPlayers];
        this.clientHandlers = new ClientHandler[numberOfPlayers];
        for (int j = 0; j < numberOfPlayers; j++) {
            clientSockets[j] = serverSocket.accept();
            System.out.println("Client" + (j + 1) + "connected!");
            clientHandlers[j] = new ClientHandler(clientSockets[j], cyclicBarrier);
            threads[j] = new Thread(clientHandlers[j]);
            threads[j].start();
        }
        waitForClients();
        resetClientFlags();
        while (true) {
            waitForClients();
            for (int i = 0; i < numberOfPlayers; i++) {
                playersData.put(clientHandlers[i].getPlayerInformation());
            }
            battleSimulator.shufflePlayers(numberOfPlayers);
            for(int i = 0; i<numberOfPlayers; i++){
                System.out.println(battleSimulator.getPlayersIDs().get(i));
            }
            if (numberOfPlayers > 1) {
                for (int i = 0; i < numberOfPlayers; i += 2) {
                    playersData.getJSONObject(battleSimulator.getPlayersIDs().get(i)).put("start", 0);
                    playersData.getJSONObject(battleSimulator.getPlayersIDs().get(i + 1)).put("start", 1);
                    clientHandlers[battleSimulator.getPlayersIDs().get(i)].setPlayerInformation(playersData.getJSONObject(battleSimulator.getPlayersIDs().get(i + 1)));
                    clientHandlers[battleSimulator.getPlayersIDs().get(i+1)].setPlayerInformation(playersData.getJSONObject(battleSimulator.getPlayersIDs().get(i)));
                }
            }
            if (numberOfPlayers % 2 != 0) {
                playersData.getJSONObject(battleSimulator.getPlayersIDs().get(numberOfPlayers - 1)).put("start", 1);
                playersData.getJSONObject(battleSimulator.getPlayersIDs().get(numberOfPlayers - 1)).put("player", "bot");
                clientHandlers[battleSimulator.getPlayersIDs().get(numberOfPlayers - 1)].setPlayerInformation(playersData.getJSONObject(battleSimulator.getPlayersIDs().get(numberOfPlayers - 1)));
            }
            resetClientFlags();
            waitForClients();
            if(numberOfPlayers==1){
                clientHandlers[0].EndGame();
                waitForClients();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            resetClientFlags();
        }

    }
}
