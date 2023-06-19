package com.mygdx.game;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.Actor;
final public class SettingsBox extends VerticalGroup{
    
    BitmapFont font = new BitmapFont();
    Batch batchtmp;
    Texture frame;
    Image menuFrame;
    TextureRegionDrawable background;
    Music music;

    SettingsBox(Batch batch){

        this.batchtmp = batch;
        
        this.setSize(Gdx.app.getGraphics().getWidth()*540/2560, Gdx.app.getGraphics().getHeight()*820/1440);
        this.setPosition((Gdx.app.getGraphics().getWidth()/2) - (this.getWidth()/2), (Gdx.app.getGraphics().getHeight()/2) - (this.getHeight()/2));
        this.setDebug(true);

        //Image setPosition nie dziala bo grupa ;)
        background = new TextureRegionDrawable(new Texture("menuframe.png"));
        background.setMinWidth(Gdx.app.getGraphics().getWidth()*540/2560);
        background.setMinHeight(Gdx.app.getGraphics().getHeight()*820/1440);
        
        menuFrame = new Image(background);

        TextureRegionDrawable buttonInactive = new TextureRegionDrawable(new Texture ("blue-button.png"));
        TextureRegionDrawable buttonActive = new TextureRegionDrawable(new Texture("blue-button_active.png"));
        buttonInactive.setMinWidth(Gdx.app.getGraphics().getWidth()*320/2560);
        buttonActive.setMinWidth(Gdx.app.getGraphics().getWidth()*320/2560);
        buttonInactive.setMinHeight(Gdx.app.getGraphics().getHeight()*100/1440);
        buttonActive.setMinHeight(Gdx.app.getGraphics().getHeight()*100/1440);

        //this.setFillParent(true);
        music = Gdx.audio.newMusic(Gdx.files.internal("death.mp3"));
        ImageTextButton buttonSoundOnOff = new ImageTextButton("Sound On/Off", new ImageTextButton.ImageTextButtonStyle(
            buttonInactive,
            buttonActive, // something has to be wrong with this
            null,font));
        buttonSoundOnOff.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                //wlacz/wylacz
                if(music.isPlaying()){
                    music.pause();
                } else{
                    music.play();              
                }    


            }
        });
        buttonSoundOnOff.getLabel().setColor(Color.GOLD);
        this.addActor(buttonSoundOnOff);

        ImageTextButton buttonExit = new ImageTextButton("Exit", new ImageTextButton.ImageTextButtonStyle(
            buttonInactive,
            buttonActive, // something has to be wrong with this
            null,font));
            this.addActor(buttonExit);

        buttonExit.addListener(new CustomCLickListener(this));
        buttonSoundOnOff.getLabel().setColor(Color.GOLD);
    }
    
    @Override
    public void act(float delta) {
        super.act(delta);
    }
    

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batchtmp, parentAlpha);

        batchtmp.end();
        
        batchtmp.begin();
        
       // batchtmp.flush();
        
        // batchtmp.enableBlending();
        // Color c = batchtmp.getColor();
        // batchtmp.setColor(c.r, c.g, c.b, 0.5f); //set alpha to 1
        // batchtmp.disableBlending();

        menuFrame.getDrawable().draw(batchtmp,  this.getX(),  this.getY(), menuFrame.getWidth(), menuFrame.getHeight());
        //batchtmp.end();

        

    }

    public class CustomCLickListener extends ClickListener{
        
        SettingsBox settings;

        CustomCLickListener(SettingsBox settings){
            this.settings = settings;
        }

        @Override
        public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
            
            //System.out.println(event.getTarget().getParent());
            
            //nie da sie...
            settings.setVisible(false);
            settings.getParent().getChild(2).setVisible(true);
            settings.getParent().getChild(1).setVisible(true);
        }

    }
}


