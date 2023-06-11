package com.mygdx.game;

public class Mercenary_camp {

        private final Mercenary[] mercenary_camp;

        public int getRandomInt() {
            int min = 1;
            int max = 10;
            return (int) ((Math.random() * (max - min)) + min);
        }

        public Mercenary_camp() {
            int size = 5;
            this.mercenary_camp = new Mercenary[size];
        }

        public void CreateMercenaryCamp() {
            for (int i = 0; i < this.mercenary_camp.length; i++) {
                this.mercenary_camp[i] = new Mercenary(this.getRandomInt());
            }
        }

        public Mercenary[] getMercenary_camp() {
            return mercenary_camp;
        }

        public void addMercenary(int index) {
            this.mercenary_camp[index] = new Mercenary(this.getRandomInt());
        }

        public void removeMercenary(int index) {
            this.mercenary_camp[index] = null;
        }


}
