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
    private int numberOfPlayers;
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

    public void run() throws IOException {
        BattleSimulator battleSimulator = new BattleSimulator();
        Socket[] clientSockets = new Socket[numberOfPlayers];
        Thread[] threads = new Thread[numberOfPlayers];
        ClientHandler[] clientHandlers = new ClientHandler[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            clientSockets[i] = serverSocket.accept();
            System.out.println("Client" + (i + 1) + "connected!");
            clientHandlers[i] = new ClientHandler(clientSockets[i], cyclicBarrier);
            threads[i] = new Thread(clientHandlers[i]);
            threads[i].start();
        }

        while (true) {

            while (cyclicBarrier.getNumberWaiting() != numberOfPlayers) {
                continue;
            }

            for (int i = 0; i < numberOfPlayers; i++) {
                playersData.put(clientHandlers[i].getPlayerInformation());
            }
            battleSimulator.shufflePlayers(numberOfPlayers);
            System.out.println(battleSimulator.getPlayersIDs());
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
            cyclicBarrier.reset();
        }

    }
}