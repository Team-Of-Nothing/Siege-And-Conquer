package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;



//import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class GameScreen implements Screen {
    final private Stage stage;
    final public SAC game;
    private Viewport viewport = new StretchViewport(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
    Array<Integer> mockIDs = new Array<>(new Integer[]{4, 7, 2,3,9});
    ArmyView marketView;
    ArmyView armyView;
    
    BitmapFont font = new BitmapFont();
    final static private String DEFAULT_GAME_SCREEN_BACKGROUND = "Miasto.png";

        MercenaryView mercenaryView = new MercenaryView(8);
    MercenaryView mercenaryView2 = new MercenaryView(1);

    GameScreen(final SAC game){
        this.game = game;
        stage = new Stage(viewport, game.batch);

        marketView = new ArmyView(mockIDs,0,0,stage);
        armyView = new ArmyView(mockIDs,300,0,stage); // TODO not hard coded ;(

        Image background = new Image(new Texture(DEFAULT_GAME_SCREEN_BACKGROUND));
        background.setSize(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
        stage.addActor(background);

        VerticalGroup group = new VerticalGroup();
        group.setPosition(Gdx.app.getGraphics().getWidth()*150/2560, Gdx.app.getGraphics().getHeight()*300/1440);
        group.setSize(Gdx.app.getGraphics().getWidth()*150/2560, Gdx.app.getGraphics().getHeight()*300/1440);
        //group.setDebug(true);

        TextureRegionDrawable buttonInactive = new TextureRegionDrawable(new Texture ("blue-button.png"));
        TextureRegionDrawable buttonActive = new TextureRegionDrawable(new Texture("blue-button_active.png"));

        buttonInactive.setMinWidth(Gdx.app.getGraphics().getWidth()*320/2560);
        buttonActive.setMinWidth(Gdx.app.getGraphics().getWidth()*320/2560);
        buttonInactive.setMinHeight(Gdx.app.getGraphics().getHeight()*100/1440);
        buttonActive.setMinHeight(Gdx.app.getGraphics().getHeight()*100/1440);

        ImageTextButton returnButton = new ImageTextButton("Return", new ImageTextButton.ImageTextButtonStyle(
                buttonInactive,
                buttonActive, // something has to be wrong with this
                null,font));
        returnButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenu(game));
                dispose();
            }
        });
        returnButton.getLabel().setColor(Color.GOLD);

        final ImageTextButton battleButton = new ImageTextButton("Ready!", new ImageTextButton.ImageTextButtonStyle(
            buttonInactive,
            buttonActive,
            null,font));
            battleButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //battleButton.setText("Waiting.."); //to sie przyda ;)
                //todo send info do servera a ten battle screen dopiero jak response 
                game.setScreen(new BattleScreen(game));
                dispose();
            }
        });
        battleButton.getLabel().setColor(Color.GOLD);

        //SETTINGS BUTTON
        TextureRegionDrawable settingsbuttonInactive = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Settings_Button.png"))));
        TextureRegionDrawable settingsbuttonActive = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Settings_Button_active.png"))));
        ImageButtonStyle style = new ImageButtonStyle();
        style.up           = settingsbuttonActive;
        style.down         = settingsbuttonInactive;
        ImageButton settingsButton = new ImageButton(style); 
        final Screen actualScreen = this;
        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                stage.addActor(new SettingsBox(actualScreen, game.batch));
            }
        });
        settingsButton.setPosition(Gdx.app.getGraphics().getWidth()*30/2560, Gdx.app.getGraphics().getHeight()*1290/1440);
        settingsButton.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        //settingsButton.setDebug(true);
        stage.addActor(settingsButton);
        //SETTINGS BUTTON

        //SPEED PASIVE BUTTON
        TextureRegionDrawable passiveSpeedButtonImage = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Pasive_speed.png"))));
        ImageButton passiveSpeedButton = new ImageButton(passiveSpeedButtonImage); 
        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                SAC.player.increaseSpeedBonus();
            }
        });
        passiveSpeedButton.setPosition(Gdx.app.getGraphics().getWidth()*1900/2560, Gdx.app.getGraphics().getHeight()*900/1440);
        passiveSpeedButton.setSize(Gdx.app.getGraphics().getWidth()*500/2560, Gdx.app.getGraphics().getHeight()*500/1440);
        //passiveSpeedButton.setDebug(true);
        stage.addActor(passiveSpeedButton);
        //SPEED PASIVE BUTTON

        //ATACK PASIVE BUTTON
        TextureRegionDrawable passiveAtackButtonImage = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Pasive_speed.png"))));
        ImageButton passiveAtackButton = new ImageButton(passiveAtackButtonImage); 
        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                SAC.player.increaseAttackBonus();
            }
        });
        passiveAtackButton.setPosition(Gdx.app.getGraphics().getWidth()*1900/2560, Gdx.app.getGraphics().getHeight()*800/1440);
        passiveAtackButton.setSize(Gdx.app.getGraphics().getWidth()*500/2560, Gdx.app.getGraphics().getHeight()*500/1440);
        //passiveSpeedButton.setDebug(true);
        stage.addActor(passiveAtackButton);
        //ATACK PASIVE BUTTON

        //ARMOUR PASIVE BUTTON
        TextureRegionDrawable passiveArmourButtonImage = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Pasive_speed.png"))));
        ImageButton passiveArmourButton = new ImageButton(passiveArmourButtonImage); 
        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                SAC.player.increaseAttackBonus();
            }
        });
        passiveArmourButton.setPosition(Gdx.app.getGraphics().getWidth()*1900/2560, Gdx.app.getGraphics().getHeight()*700/1440);
        passiveArmourButton.setSize(Gdx.app.getGraphics().getWidth()*500/2560, Gdx.app.getGraphics().getHeight()*500/1440);
        //passiveSpeedButton.setDebug(true);
        stage.addActor(passiveArmourButton);
        //ARMOUR PASIVE BUTTON

        //GOLD PASIVE BUTTON
        TextureRegionDrawable passiveGoldButtonImage = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Pasive_speed.png"))));
        ImageButton passiveGoldButton = new ImageButton(passiveGoldButtonImage); 
        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                SAC.player.increaseAttackBonus();
            }
        });
        passiveGoldButton.setPosition(Gdx.app.getGraphics().getWidth()*1900/2560, Gdx.app.getGraphics().getHeight()*600/1440);
        passiveGoldButton.setSize(Gdx.app.getGraphics().getWidth()*500/2560, Gdx.app.getGraphics().getHeight()*500/1440);
        //passiveSpeedButton.setDebug(true);
        stage.addActor(passiveGoldButton);
        //GOLD PASIVE BUTTON






        group.addActor(returnButton);
        group.addActor(battleButton);
        stage.addActor(group);
        stage.addActor(marketView);
        stage.addActor(armyView);
        //stage.setDebugAll(true);

        // don't forget to call this to be able to handle stage inputs
        Gdx.input.setInputProcessor(stage);


        armyView.setHighlight(true);
        
        marketView.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                MercenaryView a  = (MercenaryView)event.getTarget();
                
                for (int i = 0; i < marketView.getSize();i++)
                {
                    if (a == marketView.getMercenaryView(i))
                    {
                        System.out.println("position in army: " + i);
                        return;
                    }
                }
                

            }
        });


    }


    @Override
    public void show() {
                
    }

    @Override
    public void render(float delta) {
        
        Gdx.gl.glClear(16384);
        
        stage.draw();
        stage.act(delta);
        stage.draw();
        
        if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
            System.out.println("\n\n\n\n");
        }
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            game.setScreen(new MainMenu(game));
            dispose();
        }
    
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        
        stage.dispose();
        font.dispose();
    }
}
