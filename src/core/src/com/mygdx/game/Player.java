package com.mygdx.game;

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

    public Army getArmy() {
        return army;
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


    public Mercenary_camp getMercenary_camp() {
            return mercenary_camp;
        }

        public int getGold() {
            return gold;
        }

        public void addGold() {
            this.gold += 500+this.goldBonus*50;
        }

        public void buyMercenary() {
            this.gold -= 100;
        }

        public void newTurn() {
            this.addGold();
            for (int i = 0; i < this.army.getArmy().length; i++) {
                if (this.army.getArmy()[i] != null) {
                    this.army.getArmy()[i].setStats((this.army.getArmy()[i].getId()));
                    this.army.getArmy()[i].setAttack(this.army.getArmy()[i].getAttack() + this.attackBonus);
                    this.army.getArmy()[i].setDefense(this.army.getArmy()[i].getDefense() + this.defenseBonus);
                    this.army.getArmy()[i].setSpeed(this.army.getArmy()[i].getSpeed() + this.speedBonus);
                }
            }
            this.mercenary_camp.CreateMercenaryCamp();

        }

}
