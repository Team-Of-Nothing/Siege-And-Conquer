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

    public void checkPlayers(){
        for(int i = 0; i<numberOfPlayers; i++){
            if(clientHandlers[i].isGameDone()){
                for(int j = i; j<numberOfPlayers-1; j++){
                    clientHandlers[j]=clientHandlers[j+1];
                    threads[j]=threads[j+1];
                    clientSockets[j]=clientSockets[j+1];
                }
                numberOfPlayers-=1;
                i-=1;
            }
        }
    }

    public void checkCyclicBarrier(){
        if(this.cyclicBarrier.getParties()!=numberOfPlayers+1){
            this.cyclicBarrier = new CyclicBarrier(numberOfPlayers+1);
            for(int i = 0; i<numberOfPlayers; i++){
                this.clientHandlers[i].setCyclicBarrier(this.cyclicBarrier);
            }
        }
    }
    public void run() throws IOException {
        BattleSimulator battleSimulator = new BattleSimulator();
        this.clientSockets = new Socket[numberOfPlayers];
        this.threads = new Thread[numberOfPlayers];
        this.clientHandlers = new ClientHandler[numberOfPlayers];
        int j = 0;
        for (j = 0; j < numberOfPlayers; j++) {
            clientSockets[j] = serverSocket.accept();
            System.out.println("Client" + (j + 1) + "connected!");
            clientHandlers[j] = new ClientHandler(clientSockets[j], cyclicBarrier);
            threads[j] = new Thread(clientHandlers[j]);
            threads[j].start();
        }
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        cyclicBarrier.reset();
        while (true) {
            while (cyclicBarrier.getNumberWaiting() != numberOfPlayers) {
                checkPlayers();
            }
            checkPlayers();
            for (int i = 0; i < numberOfPlayers; i++) {
                playersData.put(clientHandlers[i].getPlayerInformation());
            }
            System.out.println(numberOfPlayers);
            battleSimulator.shufflePlayers(numberOfPlayers);
            if (numberOfPlayers > 1) {
                for (int i = 0; i < numberOfPlayers; i += 2) {
                    playersData.getJSONObject(battleSimulator.getPlayersIDs().get(i)).put("start", 0);
                    playersData.getJSONObject(battleSimulator.getPlayersIDs().get(i + 1)).put("start", 1);
                    clientHandlers[i].setPlayerInformation(playersData.getJSONObject(battleSimulator.getPlayersIDs().get(i + 1)));
                    clientHandlers[i + 1].setPlayerInformation(playersData.getJSONObject(battleSimulator.getPlayersIDs().get(i)));
                }
            }
            if (numberOfPlayers % 2 != 0) {
                playersData.getJSONObject(battleSimulator.getPlayersIDs().get(numberOfPlayers - 1)).put("start", 1);
                playersData.getJSONObject(battleSimulator.getPlayersIDs().get(numberOfPlayers - 1)).put("player", "bot");
                clientHandlers[battleSimulator.getPlayersIDs().get(numberOfPlayers - 1)].setPlayerInformation(playersData.getJSONObject(battleSimulator.getPlayersIDs().get(numberOfPlayers - 1)));
            }
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            System.out.println("powinno byc po oddaniu tury(gracze dostaja armie przeciwnika)");
            cyclicBarrier.reset();
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            checkPlayers();
            checkCyclicBarrier();
            if(numberOfPlayers==1){
                clientHandlers[0].EndGame();
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }

    }
}