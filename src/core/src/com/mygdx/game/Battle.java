package com.mygdx.game;

import java.util.ArrayList;
import java.util.Collections;



public class Battle {

    private final Player player1;

    private final Player player2;

    private final Army army1;

    private final Army army2;
    private int kolejka = 1;


    private final int result;

    private final int[] army1Queue;

    private final int[] army2Queue;

    public Battle(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.army1 = (Army) player1.getArmy().clone();
        this.army2 = (Army) player2.getArmy().clone();
        this.army1Queue = queue(this.army1);
        this.army2Queue = queue(this.army2);
        result=fight(this.army1, this.army1Queue, this.army2, this.army2Queue);
    }

    //Zwraca 0 jeśli wygrał Player1, 1 jeśli wygrał Player2
    public int getResult(){
        return result;
    }

    public void setKolejka(int kolejka){
        this.kolejka = kolejka;
    }


    int[] queue(Army army) {
        int pos = 0;
        int z = 0;
        int[] queue = new int[army.getArmy().size()];

        for (int i = 0; i < army.getArmy().size(); i++) {
            pos = i;
            if (army.getArmy().get(i) != null) {
                for (int j = i + 1; j < army.getArmy().size(); j++) {
                    if (army.getArmy().get(i) != null) {
                        if (army.getArmy().get(j).getSpeed() > army.getArmy().get(pos).getSpeed()) {
                            pos = j;
                        }
                    }
                }
            }
            queue[z] = pos;
            z++;
        }
        return queue;
    }

    public int fight(Army army1, int[] army1Queue, Army army2, int[] army2Queue) {
        while (!army1.getArmy().isEmpty()  && !army2.getArmy().isEmpty()) {
            int i = 0;
            int j = 0;
            int k = 0;
            int l = 0;
            while (i < army1.getArmy().size() && j < army2.getArmy().size()) {
                if (army1.getArmy().get(army1Queue[i]).getDefense()!=0 && army2.getArmy().get(army2Queue[j]).getDefense()!=0 ){

                    kolejka*=-1;
                    if (army1.getArmy().get(army1Queue[i]).getSpeed() > army2.getArmy().get(army2Queue[j]).getSpeed()) {
                        attack(army1.getArmy().get(army1Queue[i]), army2.getArmy().get(l));
                        if (army2.getArmy().get(l).getDefense() <= 0) {
                            l++;
                        }
                        i++;
                    } else if (army1.getArmy().get(army1Queue[i]).getSpeed() < army2.getArmy().get(army2Queue[j]).getSpeed()) {
                        attack(army2.getArmy().get(army2Queue[j]), army1.getArmy().get(k));
                        if (army1.getArmy().get(k).getDefense() <= 0) {
                            k++;
                        }
                        j++;
                    } else {
                        if (kolejka == 1) {
                            attack(army1.getArmy().get(army1Queue[i]), army2.getArmy().get(l));
                            if (army2.getArmy().get(l).getDefense() <= 0) {
                                l++;
                            }
                            i++;
                        } else {
                            attack(army2.getArmy().get(army2Queue[j]), army1.getArmy().get(k));
                            if (army1.getArmy().get(k).getDefense() <= 0) {
                                k++;
                            }
                            j++;
                        }
                    }

                } else if (army1.getArmy().get(army1Queue[i]).getDefense()==0) {
                    i++;
                } else {
                    j++;
                }
            }
        }
        if (army1.getArmy().isEmpty()) {
            return 1;
        } else {
            return 0;
        }


    }


    public void attack(Mercenary ally, Mercenary enemy) {
        MercenaryView allyView = new MercenaryView(ally.getId());
        MercenaryView enemyView= new MercenaryView(enemy.getId());
        allyView.attack();
        if (ally.getAttack() > enemy.getDefense()) {
            enemy.setDefense(0);
            enemyView.death();
        } else {
            enemy.setDefense(enemy.getDefense() - ally.getAttack());
            enemyView.damaged();
        }
    }
}

