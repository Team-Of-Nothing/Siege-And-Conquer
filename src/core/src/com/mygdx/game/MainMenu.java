package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenu implements Screen{

    // mainly for access to batch
    SAC game;

    // manages UI
    Stage stage;

    BitmapFont font = new BitmapFont();
    
    // manages UI size
    Viewport viewport = new StretchViewport(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());

    MainMenu(SAC game)
    {
        this.game = game;
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
        Image background = new Image(new Texture("background.jpg"));
        background.setSize(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
        stage.addActor(background);

        VerticalGroup group = new VerticalGroup();
        group.setPosition(Gdx.app.getGraphics().getWidth()*260/2560, Gdx.app.getGraphics().getHeight()*320/1440);
        group.setSize(Gdx.app.getGraphics().getWidth()*540/2560, Gdx.app.getGraphics().getHeight()*820/1440);
        group.setDebug(true);

        Image menuFrame = new Image(new Texture("menuframe.png"));
        menuFrame.setPosition(Gdx.app.getGraphics().getWidth()*260/2560, Gdx.app.getGraphics().getHeight()*320/1440 );
        menuFrame.setSize(Gdx.app.getGraphics().getWidth()*540/2560, Gdx.app.getGraphics().getHeight()*820/1440);

        TextureRegionDrawable buttonInactive = new TextureRegionDrawable(new Texture ("blue-button.png"));
        TextureRegionDrawable buttonActive = new TextureRegionDrawable(new Texture("blue-button_active.png"));
        buttonInactive.setMinWidth(Gdx.app.getGraphics().getWidth()*320/2560);
        buttonActive.setMinWidth(Gdx.app.getGraphics().getWidth()*320/2560);
        buttonInactive.setMinHeight(Gdx.app.getGraphics().getHeight()*100/1440);
        buttonActive.setMinHeight(Gdx.app.getGraphics().getHeight()*100/1440);
        
        
        ImageTextButton buttonPlay = new ImageTextButton("Play", new ImageTextButton.ImageTextButtonStyle(
            buttonInactive,
            buttonActive, // something has to be wrong with this
            null,font));
            buttonPlay.getLabel().setColor(Color.GOLD);
        

        ImageTextButton buttonOptions = new ImageTextButton("Options", new ImageTextButton.ImageTextButtonStyle(
            buttonInactive,
            buttonActive, // something has to be wrong with this
            null,font));
            buttonOptions.getLabel().setColor(Color.GOLD);


        ImageTextButton buttonExit = new ImageTextButton("Exit", new ImageTextButton.ImageTextButtonStyle(
            buttonInactive,
            buttonActive, // something has to be wrong with this
            null,font));
            buttonExit.addListener(new ClickListener(){
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    Gdx.app.exit();
                }
            });
            buttonExit.getLabel().setColor(Color.GOLD);


            group.align(Align.center);
            // space by height of gfx * 50/1440
            group.space(Gdx.app.getGraphics().getHeight()*50/1440);
            

  
        group.addActor(buttonPlay);
        group.addActor(buttonOptions);
        group.addActor(buttonExit);
        group.getPrefWidth();


       // group.addActor(menuFrame);

       stage.addActor(menuFrame);

        stage.addActor(group);

    }


    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    @Override
    public void render(float delta) {
        
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        // TODO Auto-generated method stub
    }

    
}
