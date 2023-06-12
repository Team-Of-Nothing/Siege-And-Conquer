package com.mygdx.game;
import java.util.ArrayList;


public class Mercenary_camp {

         ArrayList<Mercenary> mercenary_camp = new ArrayList<Mercenary>();

        public Mercenary_camp() {
            this.mercenary_camp = new ArrayList<Mercenary>();
            this.mercenary_camp.ensureCapacity(5);
        }

        public ArrayList<Mercenary> getMercenary_camp() {
            return mercenary_camp;
        }

        public void addMercenary(Mercenary mercenary, int index) {
            this.mercenary_camp.add(index, mercenary);
        }

        public void removeMercenary(int index) {
            this.mercenary_camp.remove(index);
        }
        
}
