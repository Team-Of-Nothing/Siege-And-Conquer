package com.mygdx.game;

import javax.swing.plaf.TreeUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
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



public class GameScreen implements Screen {
    final private Stage stage;
    final public SAC game;
    private Viewport viewport = new StretchViewport(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
    Array<Integer> mockIDs = new Array<>(new Integer[]{1, 2, 3, 4,5});
    ArmyView armyView = new ArmyView(mockIDs,0,0);
    ArmyView armyView2 = new ArmyView(mockIDs,300,0);
    


    BitmapFont font = new BitmapFont();
    GameScreen(final SAC game){

        this.game = game;
        System.out.println("\nshow SceneA");
        stage = new Stage(viewport, game.batch);

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
        stage.addActor(armyView);
        stage.setDebugAll(true);


        armyView.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("clicked");
            }
        });
        armyView2.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("clickedarmy2");
            }
        });
    
        stage.addActor(armyView2);


        // don't forget to call this to be able to handle stage inputs
        Gdx.input.setInputProcessor(stage);





    }
    @Override
    public void show() {
                
    }

    @Override
    public void render(float delta) {
        
        Gdx.gl.glClear(16384);
        stage.act(delta);
        stage.draw();



       

        // game.batch.begin();
        // //mercenary code is just a test, feel free to remove it/ adjust it to your needs

        // armyView.act(delta);
        // armyView.draw(game.batch, delta);
        // armyView2.act(delta);


        armyView2.setHighlight(true);
        // armyView2.draw(game.batch, delta);
        // game.batch.end();
        
        if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
            System.out.println("\n\n");

        }
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            game.setScreen(new MainMenu(game));
            dispose();
        }
    
        if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
            // get mouse position print it
            Vector2 v = viewport.unproject(new Vector2( Gdx.input.getX(), Gdx.input.getY()));
            System.out.println(v);
            armyView.printActors();
            

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
