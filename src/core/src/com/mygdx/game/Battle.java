package com.mygdx.game;

import java.util.ArrayList;


public class Battle {


    private final ArrayList<Mercenary> army1;

    private final ArrayList<Mercenary> army2;
    private int kolejka = 1;


    private  int result;

    private final int[] army1Queue;

    private final int[] army2Queue;

    public Battle(ArrayList<Mercenary> player1, ArrayList<Mercenary> player2) {
        this.army1 = player1;
        this.army2 = player2;
        this.army1Queue = queue(this.army1);
        this.army2Queue = queue(this.army2);
    }

    //Zwraca 0 jeśli wygrał Player1, 1 jeśli wygrał Player2
    public int getResult(){
        return result;
    }

    public void setResult(int result){
        this.result = result;
    }



    public void setKolejka(int kolejka){
        this.kolejka = kolejka;
    }
    public int getKolejka(){
        return kolejka;
    }


int[] queue(ArrayList<Mercenary> army) {
        int pos = 0;
        int z = 0;
        int [] queue = new int[army.size()];
        ArrayList<Mercenary> armysorted= (ArrayList<Mercenary>) army.clone();
    for (int i = 0; i < armysorted.size(); i++) {
            for (int j = 0; j < armysorted.size() - 1; j++) {
                if (armysorted.get(j).getSpeed() < armysorted.get(j + 1).getSpeed()) {
                    Mercenary temp = armysorted.get(j);
                    armysorted.set(j, armysorted.get(j + 1));
                    armysorted.set(j + 1, temp);
                }
            }
        }
    for(int i = 0; i < armysorted.size(); i++)
    {
        for(int j=0;j<army.size();j++)
        {
            if(armysorted.get(i).equals(army.get(j)))
            {
                queue[i]=j;
                break;
            }
        }

    }



        return queue;
    }

//    public int fight(ArrayList<Mercenary> army1, int[] army1Queue, ArrayList<Mercenary> army2, int[] army2Queue) {
////        int k = 0;
////        int l = 0;
////        while (k<army1.size()  && l<army2.size())
////        {
////            int i = 0;
////            int j = 0;
////            while (i < army1.size() && j < army2.size()) {
////                if(k>=army1.size()  || l>=army2.size())
////                {
////                    break;
////                }
////                if (army1.get(army1Queue[i]).getDefense()!=0 && army2.get(army2Queue[j]).getDefense()!=0 ){
////
////                    kolejka*=-1;
////                    if (army1.get(army1Queue[i]).getSpeed() > army2.get(army2Queue[j]).getSpeed()) {
////                        attack(army1.get(army1Queue[i]), army2.get(l));
////                        if (army2.get(l).getDefense() <= 0) {
////                            l++;
////                        }
////                        i++;
////                    } else if (army1.get(army1Queue[i]).getSpeed() < army2.get(army2Queue[j]).getSpeed()) {
////                        attack(army2.get(army2Queue[j]), army1.get(k));
////                        if (army1.get(k).getDefense() <= 0) {
////                            k++;
////                        }
////                        j++;
////                    } else {
////                        if (kolejka == 1) {
////                            attack(army1.get(army1Queue[i]), army2.get(l));
////                            if (army2.get(l).getDefense() <= 0) {
////                                l++;
////                            }
////                            i++;
////                        } else {
////                            attack(army2.get(army2Queue[j]), army1.get(k));
////                            if (army1.get(k).getDefense() <= 0) {
////                                k++;
////                            }
////                            j++;
////                        }
////                    }
////
////                } else if (army1.get(army1Queue[i]).getDefense()==0) {
////                    i++;
////                } else {
////                    j++;
////                }
////            }
////        }
////        if (army1.isEmpty()) {
////            return 1;
////        } else {
////            return 0;
////        }
////
//return 0;
//    }


    public void attack(Mercenary ally, Mercenary enemy) {
        if (ally.getAttack() > enemy.getDefense()) {
            enemy.setDefense(0);
        } else {
            enemy.setDefense(enemy.getDefense() - ally.getAttack());
        }
    }
}

