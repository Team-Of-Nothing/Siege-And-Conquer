package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


//import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class BattleScreen implements Screen {
    final private Stage stage;
    final public SAC game;

    private Viewport viewport = new StretchViewport(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
    final static private String DEFAULT_BATTLE_SCREEN_BACKGROUND = "Walka_Scena.png";

    ArmyView armyView;
    ArmyView army2View;

    int Army2Index = -1;
    int Army1Index = -1;
    BitmapFont font = new BitmapFont();
    //SpriteBatch spriteBatch;
    BattleScreen(final SAC game) {
        this.game = game;
        stage = new Stage(viewport, game.batch);

        float Offsetxwidth = Gdx.graphics.getWidth();
        Offsetxwidth = Offsetxwidth / 2.4f;

        // possibly refactor into standalone function
        Array<Integer> army1IDs = new Array<Integer>(game.player.getArmy().size());
        for (int i = 0; i < game.player.getArmy().size(); i++) {
            army1IDs.add(game.player.getArmy().get(i).getId());
        }

        Array<Integer> army2IDs = new Array<Integer>(game.getResponder().getEnemyArmy().getArmy().size());
        for (int i = 0; i < game.getResponder().getEnemyArmy().getArmy().size(); i++) {
            army2IDs.add(game.getResponder().getEnemyArmy().getArmy().get(i).getId());
        }

        armyView = new ArmyView(army1IDs, 0, 0, stage);


        army2View = new ArmyView(army2IDs, Offsetxwidth, 0, stage);

        Image background = new Image(new Texture(DEFAULT_BATTLE_SCREEN_BACKGROUND));
        background.setSize(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
        stage.addActor(background);

        VerticalGroup group = new VerticalGroup();
        group.setPosition(Gdx.app.getGraphics().getWidth() * 150 / 2560, Gdx.app.getGraphics().getHeight() * 300 / 1440);
        group.setSize(Gdx.app.getGraphics().getWidth() * 150 / 2560, Gdx.app.getGraphics().getHeight() * 300 / 1440);
        group.setDebug(true);

        army2View.flip();

        stage.addActor(armyView);
        stage.addActor(army2View);
        stage.setDebugAll(true);

        Gdx.input.setInputProcessor(stage);
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
