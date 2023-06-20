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
        if(this.gold < 200) {
            return;
        }
        this.gold -= 200;

            this.attackBonus += 1;
            for(Mercenary mercenary : this.army.getArmy()){
                mercenary.setAttack(mercenary.getAttack()+1);
            }
    }
    public void increaseDefenseBonus() {
        if(this.gold < 200) {
            return;
        }
        this.gold -= 200;

            this.defenseBonus += 1;
            for(Mercenary mercenary : this.army.getArmy()){
                mercenary.setDefense(mercenary.getDefense()+1);
            }
    }
    public void increaseSpeedBonus() {
        if(this.gold < 200) {
            return;
        }
        this.gold -= 200;

            this.speedBonus += 1;
            for(Mercenary mercenary : this.army.getArmy()){
                mercenary.setSpeed(mercenary.getSpeed()+1);
            }
    }
    public void increaseGoldBonus() {
        if(this.gold < 200) {
            return;
        }
        this.gold -= 200;
            this.goldBonus += 1;
    }


        public ArrayList<Mercenary> getMercenary_camp() {
            return mercenary_camp.getMercenary_camp();
        }

        public int getGold() {
            return gold;
        }

        public void addBonuses(Mercenary mercenary){
            mercenary.setAttack(mercenary.getAttack()+this.attackBonus);
            mercenary.setDefense(mercenary.getDefense()+this.defenseBonus);
            mercenary.setSpeed(mercenary.getSpeed()+this.speedBonus);
        }

        public void eraseBonuses(Mercenary mercenary){
            mercenary.setAttack(mercenary.getAttack()-this.attackBonus);
            mercenary.setDefense(mercenary.getDefense()-this.defenseBonus);
            mercenary.setSpeed(mercenary.getSpeed()-this.speedBonus);
        }
        public void addGold() {
            this.gold += 500+this.goldBonus*50;
        }

        public void buyMercenary(int index,int index2) {
            if(this.gold < 100) {
                return;
            }
            if((this.army.getArmy().get(index2)==null)) {
                this.army.addMercenary(this.mercenary_camp.getMercenary_camp().get(index), index2);
                this.addBonuses(this.army.getArmy().get(index2));
                this.mercenary_camp.removeMercenary(index);
                this.gold -= 100;
                return;
            }
            else if(this.army.getArmy().get(index2).getId() == this.mercenary_camp.getMercenary_camp().get(index).getId()) {
                this.eraseBonuses(this.army.getArmy().get(index2));
                this.getArmy().get(index2).merge();
                this.addBonuses(this.army.getArmy().get(index2));
                this.mercenary_camp.removeMercenary(index);
                this.gold -= 100;
                return;
            }


        }

        public void sellMercenary(int index) {
            this.army.removeMercenary(index);
            this.gold += 50;
        }


        public void swapMercenary(int index1, int index2) {
        Mercenary temp = this.army.getArmy().get(index1);
        if(this.army.getArmy().get(index2) == null) {
            this.army.addMercenary(temp, index2);
            this.army.removeMercenary(index1);
            return;
         }
        else if(this.army.getArmy().get(index2).getId() == temp.getId()) {
            eraseBonuses(this.army.getArmy().get(index2));
            this.army.getArmy().get(index2).merge();
            addBonuses(this.army.getArmy().get(index2));
            this.army.removeMercenary(index1);
            return;
        }
        else {
            this.army.addMercenary(this.army.getArmy().get(index2), index1);
            this.army.addMercenary(temp, index2);
            return;
        }
        }
        public void newTurn() {
            addGold();
            this.mercenary_camp.CreateMercenaryCamp();
        }



}
