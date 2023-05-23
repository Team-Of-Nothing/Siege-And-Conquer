package com.mygdx.game;

public class Gold {
        private int amount;
        private  int bonus;

        public Gold(int amount) {
            this.amount = amount;
            this.bonus = 1;
        }

        public int getAmount() {
            return amount;
        }

        public void setBonus(int bonus) {
            this.bonus = bonus;
        }

        public void addGold() {
            this.amount *= bonus;
        }

        public void subtractGold(int amount) {
            if (this.amount >= amount) {
                this.amount -= amount;
            }
            System.out.println("Not enough gold");
        }
}
