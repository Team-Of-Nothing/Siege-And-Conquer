package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CoinView extends Actor {
    
    private Animation<TextureRegion> animation;
    private Texture wheelingCoin;
    private Sprite sprite;
    private float time;

    CoinView(){
        wheelingCoin = new Texture(Gdx.files.internal("SpinningCoin.png"));
        sprite = new Sprite(wheelingCoin);
        sprite.setSize(50,60);
        final int numberOfFrames = 7;
        time = 0.01f;


        TextureRegion[][] tmp = TextureRegion.split(wheelingCoin, wheelingCoin.getWidth()/numberOfFrames, wheelingCoin.getHeight());
        TextureRegion[] frames = new TextureRegion[numberOfFrames];
        for (int i = 0; i < numberOfFrames; i++)
        {
            frames[i] = tmp[0][i];
        }
        
        animation = new Animation<TextureRegion>(0.17f, frames);
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void moveTo(float x,float y){
        sprite.setPosition(x, y);
        this.setPosition(x, y);
    }

    @Override
    public void act(float delta) {
        
        super.act(delta);
        time += delta;

        sprite.setRegion(animation.getKeyFrame(time, false));

        if (time > animation.getAnimationDuration()) {
            time = 0.01f;
        }
        if (time > animation.getAnimationDuration())
        {
            time = 0.01f;
            sprite.setColor(Color.WHITE);
        }
    }

    public void setPos(float x, float y) {
        sprite.setPosition(x, y);
        this.setPosition(x, y);
    }
    public void setPos(Vector2 v)
    {
        sprite.setPosition(v.x, v.y);
        this.setPosition(v.x, v.y);
    } 
    // first call setPos before calling this
    public void setSize(float width, float height) {
        sprite.setSize(width, height);
        this.setBounds(sprite.getX(), sprite.getY(), width, height);
    }
        public void setSize(Vector2 v) {
        sprite.setSize(v.x, v.y);
        this.setBounds(sprite.getX(), sprite.getY(), v.x, v.y);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);

    }
    
    public void dispose()
    {
        wheelingCoin.dispose();
    }

}
