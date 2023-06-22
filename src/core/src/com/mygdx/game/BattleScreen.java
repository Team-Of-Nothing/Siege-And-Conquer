package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import com.badlogic.gdx.utils.viewport.Viewport;

import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;


import java.util.ArrayList;


//import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class BattleScreen implements Screen {
    final private Stage stage;
    final public SAC game;

    private Viewport viewport = new StretchViewport(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());

    ArmyView armyView;
    ArmyView army2View;

    private int Army2Index = -1;
    private int Army1Index = -1;
    private int k = 0;
    private int l = 0;

    private int i = 0;

    private int j = 0;

    public boolean xy = false;

    private int random;

    private int[] army1Queue;
    private int[] army2Queue;

    private float time = 0;
    Battle battle;

    ArrayList<Mercenary> ally= new ArrayList<Mercenary>();
    ArrayList<Mercenary> enemy= new ArrayList<Mercenary>();
    BitmapFont font = new BitmapFont();

    final static private String DEFAULT_GAME_SCREEN_BACKGROUND = "Walka_Scena.png";
    //SpriteBatch spriteBatch;
    BattleScreen(final SAC game) {
        this.game = game;

        stage = new Stage(viewport, game.batch);


        float Offsetxwidth = Gdx.graphics.getWidth();
        Offsetxwidth= Offsetxwidth/2.4f;

        // possibly refactor into standalone function
        Array<Integer> army1IDs = new Array <Integer>(game.player.getArmy().size());
        for (int i = 0; i < game.player.getArmy().size(); i++) {
            army1IDs.add(game.player.getArmy().get(i).getId());
        }

        Array<Integer> army2IDs = new Array <Integer>(game.getResponder().getEnemyArmy().getArmy().size());
        System.out.println("size :" + game.getResponder().getEnemyArmy().getArmy().size());
        for (int i = 0; i < game.getResponder().getEnemyArmy().getArmy().size(); i++) {
            army2IDs.add(game.getResponder().getEnemyArmy().getArmy().get(i).getId());
        }


        armyView = new ArmyView(army1IDs,0,0,stage); // TODO not hard coded ;(

        army2View = new ArmyView(army2IDs,Offsetxwidth,0,stage);




        Image background = new Image(new Texture(DEFAULT_GAME_SCREEN_BACKGROUND));
        background.setSize(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
        stage.addActor(background);


        VerticalGroup group = new VerticalGroup();
        group.setPosition(Gdx.app.getGraphics().getWidth()*150/2560, Gdx.app.getGraphics().getHeight()*300/1440);
        group.setSize(Gdx.app.getGraphics().getWidth()*150/2560, Gdx.app.getGraphics().getHeight()*300/1440);
        group.setDebug(true);

        game.enemy.initArmy(game.responder.getEnemyArmy());
        for(int i = 0; i < game.enemy.getArmy().size(); i++){
            army2View.getMercenaryView(i).flip();
        }


        stage.addActor(army2View);
        stage.addActor(armyView);
        stage.setDebugAll(true);


        for(int i=0;i<game.player.getArmy().size();i++){
            ally.add(game.player.getArmy().get(i));
        }
        for(int i=0;i<game.getResponder().getEnemyArmy().getArmy().size();i++){
            enemy.add(game.getResponder().getEnemyArmy().getArmy().get(i));
        }

//        ally = (ArrayList<Mercenary>) game.player.getArmy().clone();
//        enemy = (ArrayList<Mercenary>) game.getResponder().getEnemyArmy().getArmy().clone();
        battle = new Battle();
        army1Queue = battle.queue(ally);
        army2Queue = battle.queue(enemy);
        xy=game.responder.getAttackPriority();
        if(xy==true){
random=1;
        }
        else{
random=-1;
        }

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


        
        //group.setScale(2f, 2f);
        //group.setPosition(Gdx.app.getGraphics().getWidth()*850/2560, Gdx.app.getGraphics().getHeight()*400/1440);
        //useless but funny
        //group.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 1f), Actions.scaleTo(1f, 1f, 1f)));
        //stage.addActor(group);




        Gdx.input.setInputProcessor(stage);
    }
    public void allyattack(int allyindex, int enemyindex)
    {
        battle.attack(ally.get(allyindex), enemy.get(enemyindex));
        armyView.getMercenaryView(army1Queue[allyindex]).attack();
        if (enemy.get(enemyindex).getDefense() == 0) {
            army2View.getMercenaryView(enemyindex).death();
            l++;
        } else {
            army2View.getMercenaryView(enemyindex).damaged();
        }
        i++;

    }

    public void enemyattack(int enemyindex, int allyindex)
    {
        battle.attack(enemy.get(enemyindex), ally.get(allyindex));
        army2View.getMercenaryView(army2Queue[enemyindex]).attack();
        if (ally.get(allyindex).getDefense() == 0) {
            armyView.getMercenaryView(allyindex).death();
            k++;
        } else {
            armyView.getMercenaryView(allyindex).damaged();
        }


        j++;

    }




    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        System.out.println("k: "+k+" l: "+l+" i: "+i+" j: "+j);

        Gdx.gl.glClear(16384);
        time += delta;
        stage.draw();
        stage.act(delta);
        stage.draw();
        int a=0;
        if (time > 1.5f) {
            if (k < ally.size() && l < enemy.size()) {
                if(i==army1Queue.length&&j<army2Queue.length)
                {
                    if(enemy.get(army2Queue[j]).getDefense()!=0) {
                        enemyattack(j, k);
                    }
                    else {
                        j++;
                    }
                    a=1;
                }
                if(j==army2Queue.length&&i<army1Queue.length)
                {
                    if(ally.get(army1Queue[i]).getDefense()!=0) {
                        allyattack(i, l);
                    }
                    else {
                        i++;
                    }
                    a=1;
                }
                if(a==0) {
                    if (i > army1Queue.length - 1) {
                        i = 0;
                    }
                    if (j > army2Queue.length - 1) {
                        j = 0;
                    }
                    while (ally.get(army1Queue[i]).getDefense() == 0) {
                        i++;
                        if (i > army1Queue.length - 1) {
                            i = 0;
                        }
                    }
                    while (enemy.get(army2Queue[j]).getDefense() == 0) {
                        j++;
                        if (j > army2Queue.length - 1) {
                            j = 0;
                        }
                    }


                    if (ally.get(army1Queue[i]).getSpeed() > enemy.get(army2Queue[j]).getSpeed()) {
                        allyattack(i, j);
                    } else if (enemy.get(army2Queue[j]).getSpeed() > ally.get(army1Queue[i]).getSpeed()) {
                        enemyattack(j, k);
                    } else {
                        if (random == 1) {
                            enemyattack(j, k);
                        } else {
                            allyattack(i, j);
                        }

                    }
                    random *= -1;
                }


            }
            armyView.act(delta);
            army2View.act(delta);
            time = 0.13f;
        }


        if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
            System.out.println("\n\n\n\n");
        }
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            game.setScreen(new MainMenu(game));
            dispose();
        }

        if (k == ally.size() )
        {
            game.player.defeat();
            game.setScreen(new GameScreen(game));
            game.client.refreshShop();
            while(game.responder.getReposndStatus()==false)
            {
                continue;
            }
            game.responder.resetRespondStatus();
            game.player.setMercenaryCamp(game.responder.getMerceneryCamp());
            game.player.newTurn();
            dispose();
        }
        if(l==enemy.size())
        {
            game.setScreen(new GameScreen(game));
            game.client.refreshShop();
            while(game.responder.getReposndStatus()==false)
            {
                continue;
            }
            game.responder.resetRespondStatus();
            game.player.setMercenaryCamp(game.responder.getMerceneryCamp());
            game.player.newTurn();
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
