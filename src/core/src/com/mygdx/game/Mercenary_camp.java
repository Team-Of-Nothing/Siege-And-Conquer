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
            CreateMercenaryCamp();
        }

        public void CreateMercenaryCamp() {
            if(mercenary_camp.isEmpty()){
                int size = 5;
                for (int i = 0; i < size; i++) {
                this.mercenary_camp.add(new Mercenary(this.getRandomInt()));
            }}
            else{
                this.mercenary_camp.clear();
            }
        }

        public ArrayList<Mercenary> getMercenary_camp() {
            return mercenary_camp;
        }


        public void removeMercenary(int index) {
            this.mercenary_camp.remove(index);
        }


}
