package com.mygdx.game;

public class Tenant {

    private String name;
    private int speed;
    private int attack;
    private int defense;

    public Tenant(String name, int speed, int attack, int defense) {
        this.name = name;
        this.speed = speed;
        this.attack = attack;
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }


    public void attack(Tenant enemy) {
        enemy.setDefense(enemy.getDefense() - this.attack);
    }

    public void upgradeStats() {
        double bonus = 1.5;
        this.speed *= bonus;
        this.attack *= bonus;
        this.defense *= bonus;
    }



}
