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
import com.badlogic.gdx.utils.viewport.StretchViewport;



//import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class GameScreen implements Screen {
    final private Stage stage;
    final public SAC game;
    BitmapFont font = new BitmapFont();
    SpriteBatch spriteBatch;
    public Animation<TextureRegion> attackAnimation;
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

       /* Texture test = new Texture("./Lightning Mage/Attack_2.png");
        TextureRegion[][] tmp = TextureRegion.split(test, test.getWidth()/4, test.getHeight());
        TextureRegion[] frames = new TextureRegion[4];
        for (int i = 0; i < 4; i++)
        {
            frames[i] = tmp[0][i];
        }
        idleAnimation = new Animation<>(1 / 5f, frames);

        // Instantiate a SpriteBatch for drawing and reset the elapsed animation
        // time to 0
        spriteBatch = new SpriteBatch();
        stateTime = 0f;*/

        //Image warrior1jpg= new Image(new  Texture("./Lightning Mage/Idle.png"));

        //Mercenary warrior1 = new Mercenary("kulak", 5,5, 5, warrior1jpg);

        //stage.addActor(warrior1jpg);

        TextureAtlas atlas = null;
        attackAnimation = new Animation<TextureRegion>(0.033f, atlas.findRegions("running"), Animation.PlayMode.LOOP);

        group.addActor(returnButton);
        stage.addActor(group);

        // don't forget to call this to be able to handle stage inputs
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(16384);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
        //stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        //TextureRegion currentFrame = idleAnimation.getKeyFrame(stateTime, true);
        //spriteBatch.begin();
        //spriteBatch.draw(currentFrame, 50, 50); // Draw current frame at (50, 50)
        //spriteBatch.end();

        stage.act(delta);
        stage.draw();
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
        spriteBatch.dispose();
        System.out.println("\ndispose SceneA");
    }
}
