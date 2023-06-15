package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BattleSimulator {
    private List<Integer> playersIDs = new ArrayList<>();

    BattleSimulator() {

    }

    public void shufflePlayers(int numberOfPlayers) {
        playersIDs.clear();
        for (int i = 0; i < numberOfPlayers; i++) {
            playersIDs.add(i);
        }
        Collections.shuffle(playersIDs);
    }

    public List<Integer> getPlayersIDs() {
        return this.playersIDs;
    }

}
