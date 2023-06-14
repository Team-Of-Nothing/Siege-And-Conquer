package com.mygdx.game;

import java.io.File;

public class Mercenary {

    private int id;
    private int speed;
    private int attack;
    private int defense;

    public Mercenary (int id) {
        this.id = id;
        this.setStats(id);
    }


    public int getId() {
        return id;
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

    public void setStats(int id){
        switch (id)
        {
            case 1:
                this.speed = 2;
                this.attack = 1;
                this.defense = 50;
                break;
            case 2:
                this.speed = 2;
                this.attack = 1;
                this.defense = 50;
                break;
            case 3:
                this.speed = 2;
                this.attack = 1;
                this.defense = 50;
                break;
            case 4:
                this.speed = 2;
                this.attack = 1;
                this.defense = 50;
                break;
            case 5:
                this.speed = 2;
                this.attack = 1;
                this.defense = 50;
                break;
            case 6:
                this.speed = 2;
                this.attack = 1;
                this.defense = 50;
                break;
            case 7:
                this.speed = 2;
                this.attack = 1;
                this.defense = 50;
                break;
            case 8:
                this.speed = 2;
                this.attack = 1;
                this.defense = 50;
                break;
            case 9:
                this.speed = 2;
                this.attack = 1;
                this.defense = 50;
                break;
            case 10:
                this.speed = 2;
                this.attack = 1;
                this.defense = 50;
                break;
            default:
                break;

        }
    }



    public void attack(Mercenary enemy) {
        enemy.setDefense(enemy.getDefense() - this.attack);
    }

    public void Merge() {
        this.speed *= 1.5;
        this.attack *= 1.5;
        this.defense *= 1.5;
    }



}