package com.mygdx.game;

public class Mercenary {

    private int id;
    private int speed;
    private int attack;
    private int defense;

    public Mercenary (int id) {
        this.id = id;
        this.setStats(id);
    }

    public Mercenary (int id, int speed, int attack, int defesne) {
        this.id = id;
        this.speed = speed;
        this.attack = attack;
        this.defense = defesne;
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
                this.attack = 2;
                this.defense = 6;
                break;
            case 2:
                this.speed = 2;
                this.attack = 2;
                this.defense = 6;
                break;
            case 3:
                this.speed = 2;
                this.attack = 2;
                this.defense = 6;
                break;
            case 4:
                this.speed = 2;
                this.attack = 2;
                this.defense = 6;
                break;
            case 5:
                this.speed = 2;
                this.attack = 2;
                this.defense = 6;
                break;
            case 6:
                this.speed = 2;
                this.attack = 2;
                this.defense = 6;
                break;
            case 7:
                this.speed = 2;
                this.attack = 2;
                this.defense = 6;
                break;
            case 8:
                this.speed = 2;
                this.attack = 2;
                this.defense = 6;
                break;
            case 9:
                this.speed = 2;
                this.attack = 2;
                this.defense = 6;
                break;
            case 10:
                this.speed = 2;
                this.attack = 2;
                this.defense = 6;
                break;
            default:
                break;

        }
    }




    public void merge() {
        this.speed *= 1.5;
        this.attack *= 1.5;
        this.defense *= 1.5;
    }



}