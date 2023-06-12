package com.mygdx.game;

import java.util.ArrayList;

public class Player {

        private final Army army;
        private final Mercenary_camp mercenary_camp;
        private int gold;
        private int hp;

        private final String name;

        private int attackBonus;
        private int defenseBonus;
        private int speedBonus;
        private int goldBonus;




        public Player(String name) {
            this.name = name;
            this.army = new Army();
            this.mercenary_camp = new Mercenary_camp();
            this.gold = 500;
            this.hp = 5;
            this.attackBonus = 0;
            this.defenseBonus = 0;
            this.speedBonus = 0;
            this.goldBonus = 0;
        }

    public ArrayList<Mercenary> getArmy() {
        return this.army.getArmy();
    }

    public void defeat() {
        this.hp -= 1;
    }

    public int getHp() {
        return hp;
    }

    public String getName() {
        return name;
    }

    public void increaseAttackBonus() {
        this.attackBonus += 1;
    }
    public void increaseDefenseBonus() {
        this.defenseBonus += 1;
    }
    public void increaseSpeedBonus() {
        this.speedBonus += 1;
    }
    public void increaseGoldBonus() {
        this.goldBonus += 1;
    }


        public ArrayList<Mercenary> getMercenary_camp() {
            return mercenary_camp.getMercenary_camp();
        }

        public int getGold() {
            return gold;
        }

        public void addGold() {
            this.gold += 500+this.goldBonus*50;
        }

        public void buyMercenary(int index,int index2) {
            this.army.addMercenary(this.mercenary_camp.getMercenary_camp().get(index), index2);
            this.mercenary_camp.removeMercenary(index);
            this.gold -= 100;
        }

        public void sellMercenary(int index) {
            this.army.removeMercenary(index);
            this.gold += 50;
        }

        public void newTurn() {
            addGold();
            for (int i = 0; i <this.army.getArmy().size(); i++) {
                    this.army.getArmy().get(i).setStats((this.army.getArmy().get(i).getId()));
                    this.army.getArmy().get(i).setAttack((this.army.getArmy().get(i).getAttack()+this.attackBonus));
                    this.army.getArmy().get(i).setDefense((this.army.getArmy().get(i).getDefense()+this.defenseBonus));
                    this.army.getArmy().get(i).setSpeed((this.army.getArmy().get(i).getSpeed()+this.speedBonus));
            }
        }



}
