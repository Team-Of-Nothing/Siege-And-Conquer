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
    
    final static private String COST_OF_PASSIVES = "200";
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

        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        int[] costs_offsets = {65, -15};
        int[] counter_offsets = {27, -7};
        int[] money_table_offsets = {65, -5};

        ImageButton moneyTable = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tabliczka_koszt_passives.png")))));        
        moneyTable.setPosition(Gdx.app.getGraphics().getWidth()*1150/2560, Gdx.app.getGraphics().getHeight()*1315/1440);
        moneyTable.setSize(Gdx.app.getGraphics().getWidth()*230/2560, Gdx.app.getGraphics().getHeight()*140/1440);
        //settingsButton.setDebug(true);
        stage.addActor(moneyTable);

        Label moneyAmount = new Label(COST_OF_PASSIVES, labelStyle);
        moneyAmount.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        moneyAmount.setPosition(moneyTable.getX()+money_table_offsets[0], moneyTable.getY()+money_table_offsets[1]);
        stage.addActor(moneyAmount);

        //HEARTS
        ImageButton[] arrayOfHearts = new ImageButton[5];
        int hp = SAC.player.getHp();
        for(int i = 0; i < hp; i++){
            arrayOfHearts[i] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Heart.png")))));  
            arrayOfHearts[i].setPosition((((Gdx.app.getGraphics().getWidth()*1470))/2560)+(i*60), Gdx.app.getGraphics().getHeight()*1320/1440);
            arrayOfHearts[i].setSize(Gdx.app.getGraphics().getWidth()*60/2560, Gdx.app.getGraphics().getHeight()*60/1440);
            stage.addActor(arrayOfHearts[i]);
        }
        //HEARTS

        //group.setDebug(true);
        
        addBattleButton();
        addSetttingsButton();

        

        ImageButton CostOfPassiveTable = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("money_table.png")))));        
        CostOfPassiveTable.setPosition(Gdx.app.getGraphics().getWidth()*2350/2560, Gdx.app.getGraphics().getHeight()*1360/1440);
        CostOfPassiveTable.setSize(Gdx.app.getGraphics().getWidth()*200/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        //settingsButton.setDebug(true);
        stage.addActor(CostOfPassiveTable);
        //Integer.toString(SAC.player.getAttackBonus());
        Label CostOfPassive = new Label(COST_OF_PASSIVES, labelStyle);
        CostOfPassive.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        CostOfPassive.setPosition(CostOfPassiveTable.getX()+costs_offsets[0], CostOfPassiveTable.getY()+costs_offsets[1]);
        stage.addActor(CostOfPassive);




        addPassiveDefenceButton();
        ImageButton defenceCounterTable = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tabliczka_passives.png")))));        
        defenceCounterTable.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
            }
        });
        defenceCounterTable.setPosition(Gdx.app.getGraphics().getWidth()*2469/2560, Gdx.app.getGraphics().getHeight()*1200/1440);
        defenceCounterTable.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        //settingsButton.setDebug(true);
        stage.addActor(defenceCounterTable);
        //Integer.toString(SAC.player.getAttackBonus());
        Label defenceCounter = new Label("2", labelStyle);
        defenceCounter.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        defenceCounter.setPosition(defenceCounterTable.getX()+counter_offsets[0], defenceCounterTable.getY()+counter_offsets[1]);
        stage.addActor(defenceCounter);
        

        

        addPassiveSpeedButton();
        ImageButton speedCounterTable = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tabliczka_passives.png")))));        
        defenceCounterTable.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
            }
        });
        speedCounterTable.setPosition(Gdx.app.getGraphics().getWidth()*2469/2560, Gdx.app.getGraphics().getHeight()*1050/1440);
        speedCounterTable.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        //settingsButton.setDebug(true);

        stage.addActor(speedCounterTable);
        //Integer.toString(SAC.player.getAttackBonus());
        Label speedCounter = new Label("1", labelStyle);
        speedCounter.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        speedCounter.setPosition(speedCounterTable.getX()+counter_offsets[0], speedCounterTable.getY()+counter_offsets[1]);
        stage.addActor(speedCounter);




        addPassiveAttackButton();
        ImageButton attackCounterTable = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tabliczka_passives.png")))));        
        defenceCounterTable.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
            }
        });
        attackCounterTable.setPosition(Gdx.app.getGraphics().getWidth()*2469/2560, Gdx.app.getGraphics().getHeight()*900/1440);
        attackCounterTable.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        //settingsButton.setDebug(true);
        stage.addActor(attackCounterTable);

        //Integer.toString(SAC.player.getAttackBonus());
        Label attackCounter = new Label("3", labelStyle);
        attackCounter.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        attackCounter.setPosition(attackCounterTable.getX()+counter_offsets[0], attackCounterTable.getY()+counter_offsets[1]);
        stage.addActor(attackCounter);




        addPassiveGoldButton();
        ImageButton goldCounterTable = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tabliczka_passives.png")))));        
        goldCounterTable.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
            }
        });
        goldCounterTable.setPosition(Gdx.app.getGraphics().getWidth()*2469/2560, Gdx.app.getGraphics().getHeight()*750/1440);
        goldCounterTable.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        //settingsButton.setDebug(true);
        stage.addActor(goldCounterTable);

        //Integer.toString(SAC.player.getAttackBonus());
        Label goldCounter = new Label("7", labelStyle);
        goldCounter.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        goldCounter.setPosition(goldCounterTable.getX()+counter_offsets[0], goldCounterTable.getY()+counter_offsets[1]);
        stage.addActor(goldCounter);




        stage.addActor(marketView);
        stage.addActor(armyView);
        //stage.setDebugAll(true);

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



    private void addSetttingsButton(){
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
    }

    private void addBattleButton() {
        VerticalGroup group = new VerticalGroup();
        group.setPosition(Gdx.app.getGraphics().getWidth()*2290/2560, Gdx.app.getGraphics().getHeight()*90/1440);
        group.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*50/1440);
        TextureRegionDrawable buttonInactive = new TextureRegionDrawable(new Texture ("blue-button.png"));
        TextureRegionDrawable buttonActive = new TextureRegionDrawable(new Texture("blue-button_active.png"));

        buttonInactive.setMinWidth(Gdx.app.getGraphics().getWidth()*320/2560);
        buttonActive.setMinWidth(Gdx.app.getGraphics().getWidth()*320/2560);
        buttonInactive.setMinHeight(Gdx.app.getGraphics().getHeight()*100/1440);
        buttonActive.setMinHeight(Gdx.app.getGraphics().getHeight()*100/1440);
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
        group.addActor(battleButton);
        stage.addActor(group);

    }

    private void addPassiveDefenceButton(){
        TextureRegionDrawable passiveDefenceButtonImage = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("defence_pasive.jpg"))));
        ImageButton passiveDefenceButton = new ImageButton(passiveDefenceButtonImage); 
        passiveDefenceButton.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                SAC.player.increaseDefenseBonus();
            }
        });
        passiveDefenceButton.setPosition(Gdx.app.getGraphics().getWidth()*2350/2560, Gdx.app.getGraphics().getHeight()*1200/1440);
        passiveDefenceButton.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        //passiveSpeedButton.setDebug(true);
        stage.addActor(passiveDefenceButton);
    }
    
    private void addPassiveSpeedButton(){
        TextureRegionDrawable passiveSpeedButtonImage = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("speed_pasive.jpg"))));
        ImageButton passiveSpeedButton = new ImageButton(passiveSpeedButtonImage); 
        passiveSpeedButton.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                SAC.player.increaseSpeedBonus();
            }
        });
        passiveSpeedButton.setPosition(Gdx.app.getGraphics().getWidth()*2350/2560, Gdx.app.getGraphics().getHeight()*1050/1440);
        passiveSpeedButton.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        //passiveSpeedButton.setDebug(true);
        stage.addActor(passiveSpeedButton);
    }

    private void addPassiveAttackButton(){
        TextureRegionDrawable passiveAtackButtonImage = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("attack_passive.jpg"))));
        ImageButton passiveAtackButton = new ImageButton(passiveAtackButtonImage); 
        passiveAtackButton.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                SAC.player.increaseAttackBonus();
            }
        });
        passiveAtackButton.setPosition(Gdx.app.getGraphics().getWidth()*2350/2560, Gdx.app.getGraphics().getHeight()*900/1440);
        passiveAtackButton.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        //passiveSpeedButton.setDebug(true);
        stage.addActor(passiveAtackButton);
    }

    private void addPassiveGoldButton(){
        TextureRegionDrawable passiveGoldButtonImage = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("money_pasive.jpg"))));
        ImageButton passiveGoldButton = new ImageButton(passiveGoldButtonImage); 
        passiveGoldButton.addListener(new ClickListener(){
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                SAC.player.increaseGoldBonus();
            }
        });
        passiveGoldButton.setPosition(Gdx.app.getGraphics().getWidth()*2350/2560, Gdx.app.getGraphics().getHeight()*750/1440);
        passiveGoldButton.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        //passiveSpeedButton.setDebug(true);
        stage.addActor(passiveGoldButton);
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
