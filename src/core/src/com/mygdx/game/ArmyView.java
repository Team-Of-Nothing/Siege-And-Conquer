package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class ArmyView extends Actor {

    // composition
    private Array<MercenaryView> mercenaryViews = new Array<MercenaryView>(5);
    private Array<Vector2> mercenaryPositions = new Array<Vector2>(5);
    private Array<Vector2> mercenarySizes = new Array<Vector2>(5);
    boolean highlight = false; // whether there should be a visual indication of mercenary placement
    float gfxheight = Gdx.graphics.getHeight();
    float gfxwidth = Gdx.graphics.getWidth();

    // position related working only on one specific map
        float startingX = gfxwidth*0.3f;
        float endingX = startingX * 0.41f/0.3f;
        float midX = (startingX+endingX)/2;
        float startingY = 0.55f * gfxheight;
        float endingY = startingY * 0.08f/0.55f;



    ArmyView(Array<Integer> mercenaryIDs,float xOffset,float yOffset,Stage stage)
    {
        this(mercenaryIDs);
        this.startingX += xOffset;
        this.endingX = startingX * 0.41f/0.3f;;
        this.midX = (startingX+endingX)/2;
        this.startingY += yOffset;
        this.endingY = startingY * 0.08f/0.55f;

        for (int i = 0; i < 5; i++) // max size of mercenaryViews
        {
            mercenaryPositions.insert(i, new Vector2(midX+(endingX-midX)*(-1*i%2),startingY-i*(startingY-endingY)/5));
            mercenarySizes.insert(i, new Vector2(0.1f*gfxwidth,0.1f*gfxwidth));
        }


                for (int i = 0; i < mercenaryViews.size; i++)
        {

            // TODO make one function doing both
            mercenaryViews.get(i).setPos(mercenaryPositions.get(i) );
            mercenaryViews.get(i).setSize(mercenarySizes.get(i));
            stage.addActor(mercenaryViews.get(i));
            
        }
    }

// idk why this exists
    private ArmyView(Array<Integer> mercenaryIDs)
    {
        for (int i = 0; i < mercenaryIDs.size; i++)
        {
            mercenaryViews.insert(i, new MercenaryView(mercenaryIDs.get(i)));
        }
    }

    // not used anymore but might be useful in the future, though it didn't work as intended
    void resize(float width,float height)
    {
        this.gfxheight = height;
        this.gfxwidth = width;
        float startingX = width*0.3f;
        float endingX = width*0.41f;
        float midX = (startingX+endingX)/2;
        float startingY = 0.55f * height;
        float endingY = 0.08f * height;
        for (int i = 0; i < mercenaryViews.size;i++ )
        {
            mercenaryViews.get(i).setPos(
                midX+(endingX-midX)*(-1*i%2),
                startingY-i*(startingY-endingY)/5
                );
            mercenaryViews.get(i).setSize(0.1f*width,0.1f*height);

        }
    } 
    
    // not sure if that's a good idea
    @Override
    public boolean addListener (EventListener listener) 
    {
        boolean ret = true;
        for (int i = 0; i < mercenaryViews.size; i++)
        {
            ret &= mercenaryViews.get(i).addListener(listener);
        }
        return ret;
    }


    @Override
    public void act(float delta) {
        for (int i = 0; i < mercenaryViews.size; i++)
        {
            mercenaryViews.get(i).act(delta);
        }
        super.act(delta);
    }
    static Texture highlightTexture = new Texture("pajeczyna.png");
    @Override
    public void draw(Batch batch, float parentAlpha) {

            for (int i = 0; i < 5; i++) // TODO max size of mercenaryViews 
        {
            if (highlight) // highlight is(should be set) true when the armyView is a marketPlace
            {
                float x = mercenaryPositions.get(i).x;
                float y = mercenaryPositions.get(i).y;
                float width = mercenarySizes.get(i).x;
                float height = mercenarySizes.get(i).y;

                batch.draw(highlightTexture, x-width*0.1f, y - height/2, width, height);
            }
        }


        for (int i = 0; i < mercenaryViews.size; i++)
        {
             mercenaryViews.get(i).draw(batch, parentAlpha);
        }
    }

    void setHighlight(boolean b)
    {
        this.highlight = b;
    }
    
    void flip()
    {
        for (int i = 0; i < mercenaryViews.size; i++)
        {
            mercenaryViews.get(i).flip();
        }
    }

}
