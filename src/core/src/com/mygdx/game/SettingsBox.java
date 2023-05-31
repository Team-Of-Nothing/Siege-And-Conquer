package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.Actor;
public class SettingsBox extends VerticalGroup{
    //set visible na actor
    
    BitmapFont font = new BitmapFont();


    SettingsBox(){

        
        this.setSize(Gdx.app.getGraphics().getWidth()*540/2560, Gdx.app.getGraphics().getHeight()*820/1440);
        this.setPosition((Gdx.app.getGraphics().getWidth()/2) - (this.getWidth()/2), (Gdx.app.getGraphics().getHeight()/2) - (this.getHeight()/2));
        this.setDebug(true);

        //Image setPosition nie dziala bo grupa ;)

        TextureRegionDrawable background = new TextureRegionDrawable(new Texture("menuframe.png"));
        background.setMinWidth(Gdx.app.getGraphics().getWidth()*540/2560);
        background.setMinHeight(Gdx.app.getGraphics().getHeight()*820/1440);
        
        Image menuFrame = new Image(background);

        TextureRegionDrawable buttonInactive = new TextureRegionDrawable(new Texture ("blue-button.png"));
        TextureRegionDrawable buttonActive = new TextureRegionDrawable(new Texture("blue-button_active.png"));
        buttonInactive.setMinWidth(Gdx.app.getGraphics().getWidth()*320/2560);
        buttonActive.setMinWidth(Gdx.app.getGraphics().getWidth()*320/2560);
        buttonInactive.setMinHeight(Gdx.app.getGraphics().getHeight()*100/1440);
        buttonActive.setMinHeight(Gdx.app.getGraphics().getHeight()*100/1440);

        //this.setFillParent(true);
        

        ImageTextButton buttonSoundOnOff = new ImageTextButton("Sound On/Off", new ImageTextButton.ImageTextButtonStyle(
            buttonInactive,
            buttonActive, // something has to be wrong with this
            null,font)){
                @Override
                public float getMinWidth(){return 0;}
                @Override
                public float getMinHeight(){return 0;}
            };
        buttonSoundOnOff.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                //wlacz/wylacz
            }
        });
        buttonSoundOnOff.getLabel().setColor(Color.GOLD);


        ImageTextButton buttonExit = new ImageTextButton("Exit", new ImageTextButton.ImageTextButtonStyle(
            buttonInactive,
            buttonActive, // something has to be wrong with this
            null,font)){
                @Override
                public float getMinWidth(){return 0;}
                @Override
                public float getMinHeight(){return 0;}
            };
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                
            }
        });
        buttonSoundOnOff.getLabel().setColor(Color.GOLD);
        
        this.addActor(buttonSoundOnOff);
        this.addActor(buttonExit);
        //this.getPrefWidth();
        //this.setFillParent(true);
        this.addActor(menuFrame);
        
    }
    

}
