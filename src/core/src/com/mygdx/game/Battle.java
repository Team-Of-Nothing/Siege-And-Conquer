package com.mygdx.game;

public class Battle {

    private final Player player1;

    private final Player player2;

    private final Army army1;

    private final Army army2;

    private final int result;

    private final int[] army1Queue;

    private final int[] army2Queue;

    public Battle(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.army1 = player1.getArmy();
        this.army2 = player2.getArmy();
        this.army1Queue = queue(this.army1);
        this.army2Queue = queue(this.army2);
        result=fight(this.army1, this.army1Queue, this.army2, this.army2Queue);
    }

    //Zwraca 0 jeśli wygrał Player1, 1 jeśli wygrał Player2
    public int getResult(){
        return result;
    }

    int[] queue(Army army) {
        int pos = 0;
        int z = 0;
        int[] queue = new int[army.getArmy().length];
        for (int i = 0; i < army.getArmy().length; i++) {
            pos = i;
            if (army.getArmy()[i] != null) {
                for (int j = i + 1; j < army.getArmy().length; j++) {
                    if (army.getArmy()[j] != null) {
                        if (army.getArmy()[j].getSpeed() > army.getArmy()[pos].getSpeed()) {
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
        while (army1.getArmy().length != 0 && army2.getArmy().length != 0) {
            int i = 0;
            int j = 0;
            while (i < army1.getArmy().length && j < army2.getArmy().length) {
                if (army1.getArmy()[army1Queue[i]] != null && army2.getArmy()[army2Queue[j]] != null) {

                    if (army1.getArmy()[army1Queue[i]].getSpeed() > army2.getArmy()[army2Queue[j]].getSpeed()) {
                        attack(army1.getArmy()[army1Queue[i]], army2.getArmy()[j]);
                        if (army2.getArmy()[j].getDefense() <= 0) {
                            army2.removeMercenary(j);
                        }
                        i++;
                    } else if (army1.getArmy()[army1Queue[i]].getSpeed() < army2.getArmy()[army2Queue[j]].getSpeed()) {
                        attack(army2.getArmy()[army2Queue[j]], army1.getArmy()[i]);
                        if (army1.getArmy()[i].getDefense() <= 0) {
                            army1.removeMercenary(i);
                        }
                        j++;
                    } else {
                        int random = (int) (Math.random() * 2);
                        if (random == 0) {
                            attack(army1.getArmy()[army1Queue[i]], army2.getArmy()[j]);
                            if (army2.getArmy()[j].getDefense() <= 0) {
                                army2.removeMercenary(j);
                            }
                            i++;
                        } else {
                            attack(army2.getArmy()[army2Queue[j]], army1.getArmy()[i]);
                            if (army1.getArmy()[i].getDefense() <= 0) {
                                army1.removeMercenary(i);
                            }
                            j++;
                        }
                    }

                } else if (army1.getArmy()[army1Queue[i]] == null) {
                    i++;
                } else {
                    j++;
                }
            }
        }
        if (army1.getArmy().length == 0) {
            return 1;
        } else {
            return 0;
        }


    }


    public void attack(Mercenary ally, Mercenary enemy) {
        if (ally.getAttack() > enemy.getDefense()) {
            enemy.setDefense(0);
        } else {
            enemy.setDefense(enemy.getDefense() - ally.getAttack());
        }
    }
}

