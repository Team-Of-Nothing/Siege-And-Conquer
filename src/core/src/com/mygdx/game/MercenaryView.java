package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.Actor;

import com.badlogic.gdx.utils.Array;

public class MercenaryView extends Actor  {
    private Sprite sprite;
    
    private Sound mp3_attack;
    static private Sound mp3_death = Gdx.audio.newSound(Gdx.files.internal("death.mp3"));
    private int id;
    private Texture idle;
    private Texture attack;
    private Texture death;
    private Texture walk;
    private Texture damaged;
    private float time = 0;
    private int action = 0; // 0 = idle, 1 = attack, 2 = death, 3 = walk, 4 = damaged
    private boolean flip = false;
    private Array<Animation<TextureRegion>> animations;

    public MercenaryView (int id) {
        System.out.println("MercenaryView");
        this.id = id;
        idle = new Texture( "./"+ id +"/1.png");
        attack = new Texture("./" + id +"/2.png");
        death = new Texture("./" + id +"/3.png");
        walk = new Texture("./" + id +"/4.png");
        damaged = new Texture("./" + id +"/5.png");
        sprite = new Sprite(idle);
        mp3_attack = Gdx.audio.newSound(Gdx.files.internal("./" + id +"/attack.mp3"));
        sprite.setPosition(200, 200);
        sprite.setSize(200,200);
        //array of animations of frames for each mercenary type
        final int numberOfFrames[][] = {{7,7,8,5,5,7,7,6,5,5},{4,4,9,4,4,4,5,4,4,4},{5,6,4,4,5,5,4,4,4,4},{8,8,8,6,6,6,8,6,6,6},{3,3,4,2,2,3,2,2,3,2}};

        final Texture [] textures = {idle, attack, death, walk, damaged};
        animations = new Array<Animation<TextureRegion>>(textures.length);
        for (int j=0; j <textures.length;j++)
        {
            TextureRegion[][] tmp = TextureRegion.split(textures[j], textures[j].getWidth()/numberOfFrames[j][id-1], textures[j].getHeight());
            TextureRegion[] frames = new TextureRegion[numberOfFrames[j][id-1]];
            for (int i = 0; i < numberOfFrames[j][id-1]; i++)
            {
                frames[i] = tmp[0][i];
            }
            if (j == 1)
            animations.insert(j,new Animation<TextureRegion>(1f/numberOfFrames[j][id-1],frames)); 
            else animations.insert(j,new Animation<TextureRegion>(1/5f,frames));
        }
        
        animations.get(0).setPlayMode(Animation.PlayMode.LOOP);
        animations.get(1).setPlayMode(Animation.PlayMode.NORMAL);
        animations.get(2).setPlayMode(Animation.PlayMode.NORMAL);
        animations.get(3).setPlayMode(Animation.PlayMode.LOOP);
        animations.get(4).setPlayMode(Animation.PlayMode.NORMAL);
        

        
    }

    boolean played = false;
    public void setAction(int action) {
        
        if (this.action == 2 && animations.get(2).isAnimationFinished(time)) return; // death animation is finished, do nothing

        if (this.action == 0) {
            time = 0.01f;
            played = false;
        }

        if (action == 1 && !played) {
            mp3_attack.play();
            System.out.println("attack");
            played = true;
        }
        if (action == 2 && !played) {
            
            System.out.println("death"+mp3_death.play());
            played = true;

        }


        this.action = action;
        //System.out.println("action: " + action +" " + animations.get(2).isAnimationFinished(time) + " " + time );
    }

    public void attack() {
        //sprite.setColor(Color.RED);
        setAction(1);
    }

    public void death(){
        sprite.setColor(Color.BLACK);
        setAction(2);
    }

    public void walk(){
        setAction(3);
    }

    public void damaged(){
        sprite.setColor(Color.RED);
        setAction(4);
    }

    public void setPos(int x, int y) {
        sprite.setPosition(x, y);
    }

    public void flip() {
        
        flip = !flip;
        if (flip)
        {
            for (int i = 0; i< animations.size; i++)
            {
                for (int j = 0; j< animations.get(i).getKeyFrames().length; j++)
                {
                    animations.get(i).getKeyFrames()[j].flip(true, false);
                }
            }
        }
        
    }

    @Override
    public void act(float delta) {
        
        super.act(delta);
        time += delta;

        sprite.setRegion(animations.get(action).getKeyFrame(time, false));

        if (action == 1 && time > animations.get(action).getAnimationDuration()) {
            time = 0.01f;
            action = 0;
            played = false;
        }
        if (action == 4 && time > animations.get(action).getAnimationDuration())
        {
            time = 0.01f;
            action = 0;
            sprite.setColor(Color.WHITE);
            played = false;
        }


    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);

    }
    
    
    public void dispose()
    {
        idle.dispose();
        attack.dispose();
        death.dispose();
        walk.dispose();
        damaged.dispose();
        mp3_attack.dispose();
    }

}
