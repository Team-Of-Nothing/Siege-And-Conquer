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

    public void refreshArmy() {
        for (int i = 0; i < this.army.length; i++) {
            if (this.army[i] != null) {
                army[i].setAttack(army[i].getAttack());
                army[i].setDefense(army[i].getDefense());
                army[i].setSpeed(army[i].getSpeed());
                }
            }
    }

    public void swapMercenary(int index1, int index2) {
        Mercenary temp = this.army[index1];
        this.army[index1] = this.army[index2];
        this.army[index2] = temp;
    }

}








