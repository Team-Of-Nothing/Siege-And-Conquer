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

    int ShopIndex = -1;
    int ArmyIndex = -1;
    Label[] labelList = new Label[3];

    
    final static private String COST_OF_PASSIVES = "200";
    BitmapFont font = new BitmapFont();
    final static private String DEFAULT_GAME_SCREEN_BACKGROUND = "Miasto.png";



        MercenaryView mercenaryView = new MercenaryView(8);
        MercenaryView mercenaryView2 = new MercenaryView(1);
        CoinView coinAnimation = new CoinView();

    private LabelStyle labelStyle;

    protected int[] costs_offsets = {65, -15};
    protected int[] counter_offsets = {27, -7};
    protected int[] money_table_offsets = {65, -5};


    GameScreen(final SAC game){
        this.game = game;
        stage = new Stage(viewport, game.batch);

        float Offsetxwidth = Gdx.graphics.getWidth();
        Offsetxwidth= Offsetxwidth/2.6f;

        // possibly refactor into standalone function
        Array<Integer> armyIDs = new Array <Integer>(game.player.getArmy().size());
        for (int i = 0; i < game.player.getArmy().size(); i++) {
        armyIDs.add(game.player.getArmy().get(i).getId());
        }
        Array<Integer> marketIDs = new Array <Integer>(game.player.getMercenary_camp().size());
        for (int i = 0; i < game.player.getMercenary_camp().size(); i++) {
        marketIDs.add(game.player.getMercenary_camp().get(i).getId());
        }


        armyView = new ArmyView(armyIDs,Offsetxwidth,0,stage); // TODO not hard coded ;(

        if (marketIDs.size > 0)
            marketView = new ArmyView(marketIDs,0,0,stage);
        else
            marketView = new ArmyView(mockIDs,0,0,stage);



        Image background = new Image(new Texture(DEFAULT_GAME_SCREEN_BACKGROUND));
        background.setSize(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
        stage.addActor(background);
        labelStyle = new LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        setHearts();

        addBattleButton();
        addSetttingsButton();

        addCostOfPasives();
        
        addPassiveDefenceButton();
        addPassiveSpeedButton();
        addPassiveAttackButton();
        addPassiveGoldButton();



        stage.addActor(marketView);
        stage.addActor(armyView);
        //stage.setDebugAll(true);

        for(int i = 0; i < SAC.player.getArmy().size(); i++){
            int[] posOfMercenery = armyView.getMercenaryView(i).getpos();
            Label[] labelList = new Label[3];
            labelList[0] = new Label("defence: "+Integer.toString(SAC.player.getArmy().get(i).getDefense()), labelStyle);
            labelList[0].setPosition( posOfMercenery[0],  posOfMercenery[1]);
            stage.addActor(labelList[0]);
            labelList[1] = new Label("attack : "+Integer.toString(SAC.player.getArmy().get(i).getAttack()), labelStyle);
            labelList[1].setPosition( posOfMercenery[0],  posOfMercenery[1]-15);
            stage.addActor(labelList[1]);
            labelList[2] = new Label("speed : "+Integer.toString(SAC.player.getArmy().get(i).getSpeed()), labelStyle);
            labelList[2].setPosition( posOfMercenery[0],  posOfMercenery[1]-30);
            stage.addActor(labelList[2]);
        }
        for(int i = 0; i < SAC.player.getMercenary_camp().size(); i++){
            int[] posOfMercenery = marketView.getMercenaryView(i).getpos();
            Label[] labelList = new Label[3];
            labelList[0] = new Label("defence: "+Integer.toString(SAC.player.getMercenary_camp().get(i).getDefense()), labelStyle);
            labelList[0].setPosition( posOfMercenery[0],  posOfMercenery[1]);
            stage.addActor(labelList[0]);
            labelList[1] = new Label("attack : "+Integer.toString(SAC.player.getMercenary_camp().get(i).getAttack()), labelStyle);
            labelList[1].setPosition( posOfMercenery[0],  posOfMercenery[1]-15);
            stage.addActor(labelList[1]);
            labelList[2] = new Label("speed : "+Integer.toString(SAC.player.getMercenary_camp().get(i).getSpeed()), labelStyle);
            labelList[2].setPosition( posOfMercenery[0],  posOfMercenery[1]-30);
            stage.addActor(labelList[2]);
        }
        

        // Label ad1 = new Label(Integer.toString(6666), labelStyle);
        // ad1.setPosition( posOfMercenery1[0],  posOfMercenery1[1]);
        // stage.addActor(ad1);
        //idk what am doing rn want to show info about marcenery stats

        Gdx.input.setInputProcessor(stage);
        armyView.setHighlight(true);
        

        marketView.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                MercenaryView a  = (MercenaryView)event.getTarget();
                

                for (int i = 0; i < marketView.getSize();i++)
                {
                    if (a == marketView.getMercenaryView(i))
                    {
                        
                        System.out.println("position in shop: " + i);
                        ShopIndex = i;
                        return;
                    }
                }

            }
        });

        armyView.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                MercenaryView a  = (MercenaryView)event.getTarget();
                

                for (int i = 0; i < armyView.getSize();i++)
                {
                    if (a == armyView.getMercenaryView(i))
                    {
                        
                        System.out.println("position in armyView: " + i);
                        ArmyIndex = i;
                        return;
                    }
                }

            }
        });



    }
    private void addLabels(){

    }


    private void addMoneyInfoAndAnimation(){ //TODO RENDER

        ImageButton moneyTable = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tabliczka_koszt_passives.png")))));        
        moneyTable.setPosition(Gdx.app.getGraphics().getWidth()*1150/2560, Gdx.app.getGraphics().getHeight()*1315/1440);
        moneyTable.setSize(Gdx.app.getGraphics().getWidth()*230/2560, Gdx.app.getGraphics().getHeight()*140/1440);
        //settingsButton.setDebug(true);
        stage.addActor(moneyTable);
        coinAnimation.setPos(Gdx.app.getGraphics().getWidth()*1190/2560, Gdx.app.getGraphics().getHeight()*1335/1440);
        coinAnimation.setSize(30, 30);
        stage.addActor(coinAnimation);

        Label goldAmount = new Label(Integer.toString(SAC.player.getGold()), labelStyle);
        //player1Name.setSize(300, 300);
        goldAmount.setPosition(Gdx.app.getGraphics().getWidth()*1260/2560, Gdx.app.getGraphics().getHeight()*1344/1440);
        stage.addActor(goldAmount);

    }

    private void addPassiveGoldInfo(){ //TODO RENDER
        ImageButton goldCounterTable = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tabliczka_passives.png")))));        
        goldCounterTable.setPosition(Gdx.app.getGraphics().getWidth()*2469/2560, Gdx.app.getGraphics().getHeight()*750/1440);
        goldCounterTable.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        //settingsButton.setDebug(true);
        stage.addActor(goldCounterTable);

        //Integer.toString(SAC.player.getAttackBonus());
        Label goldCounter = new Label(Integer.toString(SAC.player.getGoldBonus()), labelStyle);
        goldCounter.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        goldCounter.setPosition(goldCounterTable.getX()+counter_offsets[0], goldCounterTable.getY()+counter_offsets[1]);
        stage.addActor(goldCounter);
    }

    private void addAttackPassiveInfo(){ //TODO RENDER
        ImageButton attackCounterTable = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tabliczka_passives.png")))));        

        attackCounterTable.setPosition(Gdx.app.getGraphics().getWidth()*2469/2560, Gdx.app.getGraphics().getHeight()*900/1440);
        attackCounterTable.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        //settingsButton.setDebug(true);
        stage.addActor(attackCounterTable);
        //Integer.toString(SAC.player.getAttackBonus());
        Label attackCounter = new Label(Integer.toString(SAC.player.getAttackBonus()), labelStyle);
        attackCounter.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        attackCounter.setPosition(attackCounterTable.getX()+counter_offsets[0], attackCounterTable.getY()+counter_offsets[1]);
        stage.addActor(attackCounter);
    }

    private void addSpeedPassiveInfo(){ //TODO RENDER
        ImageButton speedCounterTable = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tabliczka_passives.png")))));        
        speedCounterTable.setPosition(Gdx.app.getGraphics().getWidth()*2469/2560, Gdx.app.getGraphics().getHeight()*1050/1440);
        speedCounterTable.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        //settingsButton.setDebug(true);

        stage.addActor(speedCounterTable);
        //Integer.toString(SAC.player.getAttackBonus());
        Label speedCounter = new Label(Integer.toString(SAC.player.getSpeedBonus()), labelStyle);
        speedCounter.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        speedCounter.setPosition(speedCounterTable.getX()+counter_offsets[0], speedCounterTable.getY()+counter_offsets[1]);
        stage.addActor(speedCounter);
    }

    private void addDefencePassiveInfo(){ //TODO RENDER
        ImageButton defenceCounterTable = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tabliczka_passives.png")))));        
        defenceCounterTable.setPosition(Gdx.app.getGraphics().getWidth()*2469/2560, Gdx.app.getGraphics().getHeight()*1200/1440);
        defenceCounterTable.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        //settingsButton.setDebug(true);
        stage.addActor(defenceCounterTable);
        //Integer.toString(SAC.player.getAttackBonus());
        Label defenceCounter = new Label(Integer.toString(SAC.player.getDefenseBonus()), labelStyle);
        defenceCounter.setSize(Gdx.app.getGraphics().getWidth()*100/2560, Gdx.app.getGraphics().getHeight()*100/1440);
        defenceCounter.setPosition(defenceCounterTable.getX()+counter_offsets[0], defenceCounterTable.getY()+counter_offsets[1]);
        stage.addActor(defenceCounter);
    }

    private void addCostOfPasives(){
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
                game.client.endTurn(game.player.getArmy(),game.player.getName());
                while(game.responder.getReposndStatus() != true);
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

    private void setHearts(){
        ImageButton[] arrayOfHearts = new ImageButton[5];
        int hp = SAC.player.getHp();
        for(int i = 0; i < hp; i++){
            arrayOfHearts[i] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Heart.png")))));  
            arrayOfHearts[i].setPosition((((Gdx.app.getGraphics().getWidth()*1470))/2560)+(i*60), Gdx.app.getGraphics().getHeight()*1320/1440);
            arrayOfHearts[i].setSize(Gdx.app.getGraphics().getWidth()*60/2560, Gdx.app.getGraphics().getHeight()*60/1440);
            stage.addActor(arrayOfHearts[i]);
        }
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


        if (ShopIndex != -1 && ArmyIndex != -1)
        {

            if (game.player.buyMercenary(ShopIndex, ArmyIndex))
            {
                float Offsetxwidth = Gdx.graphics.getWidth();
                Offsetxwidth= Offsetxwidth/2.6f;
                marketView.removeMercenary(ShopIndex);
                
                Array<Integer> armyIds = new Array <Integer>(game.player.getArmy().size());
                for (int i = 0; i < game.player.getArmy().size(); i++)
                    armyIds.add(game.player.getArmy().get(i).getId());
                armyView.dispose();
                armyView = new ArmyView(armyIds,Offsetxwidth,0,stage); // TODO not hard coded ;(
                stage.addActor(armyView);
                armyView.setHighlight(true);
                armyView.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        MercenaryView a  = (MercenaryView)event.getTarget();
                        

                        for (int i = 0; i < armyView.getSize();i++)
                        {
                            if (a == armyView.getMercenaryView(i))
                            {
                                
                                System.out.println("position in armyView: " + i);
                                ArmyIndex = i;
                                return;
                            }
                        }
                    }
                });
            }
                ShopIndex = -1;
                ArmyIndex = -1;

        }
        
        addMoneyInfoAndAnimation();
        addAttackPassiveInfo();
        addDefencePassiveInfo();
        addSpeedPassiveInfo();
        addPassiveGoldInfo();

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
