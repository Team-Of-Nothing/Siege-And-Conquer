package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Mercenary extends Actor {

    private String name;
    private int speed;
    private int attack;
    private int defense;

    public Image img;

    public Mercenary(String name, int speed, int attack, int defense, Image img) {
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


    public void attack(Mercenary enemy) {
        enemy.setDefense(enemy.getDefense() - this.attack);
    }

    public void upgradeStats() {
        double bonus = 1.5;
        this.speed *= bonus;
        this.attack *= bonus;
        this.defense *= bonus;
    }



}
