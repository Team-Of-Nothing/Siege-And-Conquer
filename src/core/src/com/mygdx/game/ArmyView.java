package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class ArmyView extends Actor {

    // composition
    private Array<MercenaryView> mercenaryViews = new Array<MercenaryView>(5);
    boolean highlight = false; // whether there should be a visual indication of mercenary placement
    float height = Gdx.graphics.getHeight();
    float width = Gdx.graphics.getWidth();
    ArmyView(Array<Integer> mercenaryIDs)
    {
        for (int i = 0; i < mercenaryIDs.size; i++)
        {
            mercenaryViews.insert(i, new MercenaryView(mercenaryIDs.get(i)));
        }
        
        float startingX = width*0.3f;
        float endingX = width*0.41f;
        float midX = (startingX+endingX)/2;
        float startingY = 0.55f * height;
        float endingY = 0.08f * height;
        for (int i = 0; i < mercenaryViews.size; i++)
        {
            mercenaryViews.get(i).setPos(midX+(endingX-midX)*(-1*i%2),startingY-i*(startingY-endingY)/5 );
            mercenaryViews.get(i).setSize(0.1f*width,0.1f*width);
        }
    }

    void resize(float width,float height)
    {
        this.height = height;
        this.width = width;
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
    
    @Override
    public void act(float delta) {
        for (int i = 0; i < mercenaryViews.size; i++)
        {
            mercenaryViews.get(i).act(delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int i = 0; i < mercenaryViews.size; i++)
        {
            mercenaryViews.get(i).draw(batch, parentAlpha);
        }
    }

    void setHighlight(boolean b)
    {
        this.highlight = b;
    }
    
    

    
}
