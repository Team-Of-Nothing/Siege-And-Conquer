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

        public void addGold(int amount) {
            this.amount = amount * bonus;
        }

        // Returns true if the amount of gold is greater than or equal to the amount
        public boolean subtractGold(int amount) {
            if (this.amount >= amount) {
                this.amount -= amount;
                return true;
            }
            else {
                return false;
            }
        }
}
