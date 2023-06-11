package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;



public class GameScreen implements Screen {
    final private Stage stage;
    final public SAC game;

    BitmapFont font = new BitmapFont();

        MercenaryView mercenaryView = new MercenaryView(8);
    MercenaryView mercenaryView2 = new MercenaryView(1);

    GameScreen(final SAC game){

        this.game = game;
        System.out.println("\nshow SceneA");
        stage = new Stage(new StretchViewport(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight()));

        Image background = new Image(new Texture("Miasto.png"));
        background.setSize(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
        stage.addActor(background);

        VerticalGroup group = new VerticalGroup();
        group.setPosition(Gdx.app.getGraphics().getWidth()*150/2560, Gdx.app.getGraphics().getHeight()*300/1440);
        group.setSize(Gdx.app.getGraphics().getWidth()*150/2560, Gdx.app.getGraphics().getHeight()*300/1440);
        group.setDebug(true);

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


        group.addActor(returnButton);
        stage.addActor(group);

        // don't forget to call this to be able to handle stage inputs
        Gdx.input.setInputProcessor(stage);


        mercenaryView2.flip();
    }
    @Override
    public void show() {
                
    }

    static int id = 3;


    static int counter = 0;
    @Override
    public void render(float delta) {
        
        Gdx.gl.glClear(16384);
        stage.act(delta);
        stage.draw();
        mercenaryView.setPos(200, 200);

        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            counter++;
            mercenaryView.attack();
            if (counter < 4){
            mercenaryView2.damaged();
            }
            else mercenaryView2.death();
        }
        mercenaryView2.setPos(300, 200);

        game.batch.begin();
        //mercenary code is just a test, feel free to remove it/ adjust it to your needs
        mercenaryView.act(delta);
        mercenaryView.draw(game.batch, delta);
        mercenaryView2.act(delta);
        mercenaryView2.draw(game.batch, delta);
        game.batch.end();
        
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            System.out.println("\n\n");

        }
        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
            game.setScreen(new MainMenu(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

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
        mercenaryView.dispose();
        mercenaryView2.dispose();
    }
}
