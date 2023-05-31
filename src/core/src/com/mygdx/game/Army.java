package com.mygdx.game;

public class Army {

    private final Mercenary[] army;


    public Army() {
        int size = 5;
        this.army = new Mercenary[size];
    }

    public Mercenary[] getArmy() {
        return army;
    }

    public void addMercenary(Mercenary mercenary, int index) {
        this.army[index] = mercenary;
    }

    public void removeMercenary(int index) {
        this.army[index] = null;
    }


    public void swapMercenary(int index1, int index2) {
        Mercenary temp = this.army[index1];
        this.army[index1] = this.army[index2];
        this.army[index2] = temp;
    }

}








