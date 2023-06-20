package com.mygdx.game;
import java.util.ArrayList;


public class Mercenary_camp {


         ArrayList<Mercenary> mercenary_camp = new ArrayList<Mercenary>();


    public int getRandomInt() {
            return (int) ((Math.random() * (10 - 1)) + 1);
        }

        public Mercenary_camp() {
            this.mercenary_camp = new ArrayList<Mercenary>();
            this.mercenary_camp.ensureCapacity(5);
        }


        public void addMercenary(Mercenary mercenary) {
            this.mercenary_camp.add(mercenary);
        }

        public ArrayList<Mercenary> getMercenary_camp() {
            return mercenary_camp;
        }


        public void removeMercenary(int index) {
            this.mercenary_camp.remove(index);
        }


}
