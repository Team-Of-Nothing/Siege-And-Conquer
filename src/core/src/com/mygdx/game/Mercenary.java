package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.io.File;

public class Mercenary extends Actor {

    private int id;
    private int speed;
    private int attack;
    private int defense;

    private File img;
    private File mp3_attack;
    private File mp3_death;


    public File getMp3_attack() {
        return mp3_attack;
    }
    public File setMp3(File mp3) {
        return this.mp3_attack = mp3;
    }
    public File getMp3_death() {
        return mp3_death;
    }
    public File setMp3_death(File mp3) {
        return this.mp3_death = mp3;
    }



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

    public void setStats(int id){
        this.mp3_attack = new File("core/assets/"+id+"/attack.mp3");
        this.mp3_death = new File("core/assets/death.mp3");
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

    //Zwraca plik z obrazkiem akcji do animacji
    public void Action(int action){
        this.img = new File("core/assets/"+id+action+".png");
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





    public void attack(Mercenary enemy) {
        enemy.setDefense(enemy.getDefense() - this.attack);
    }

    public void Merge() {
        double bonus = 1.5;
        this.speed *= bonus;
        this.attack *= bonus;
        this.defense *= bonus;
    }


}
