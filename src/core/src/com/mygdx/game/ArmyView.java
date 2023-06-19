package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
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



    ArmyView(Array<Integer> mercenaryIDs,float xOffset,float yOffset)
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

            // TODO one day refactor code below to use mercenaryPositions and mercenarySizes
            mercenaryViews.get(i).setPos(midX+(endingX-midX)*(-1*i%2),startingY-i*(startingY-endingY)/5 );
            mercenaryViews.get(i).setSize(0.1f*gfxwidth,0.1f*gfxwidth);
            
            System.out.println(mercenaryViews.get(i).getX()+","+ mercenaryViews.get(i).getY());
        }
    }

    private ArmyView(Array<Integer> mercenaryIDs)
    {
        for (int i = 0; i < mercenaryIDs.size; i++)
        {
            mercenaryViews.insert(i, new MercenaryView(mercenaryIDs.get(i)));
        }

    }

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
    
    @Override
    public boolean addListener (EventListener listener) 
    {
        for (int i = 0; i < mercenaryViews.size; i++)
        {
            mercenaryViews.get(i).addListener(listener);
        }
        return true;
    }


    @Override
    public void act(float delta) {
        for (int i = 0; i < mercenaryViews.size; i++)
        {
            mercenaryViews.get(i).act(delta);
        }
        super.act(delta);
    }
    Texture highlightTexture = new Texture("pajeczyna.png");
    @Override
    public void draw(Batch batch, float parentAlpha) {

            for (int i = 0; i < 5; i++) // max size of mercenaryViews
        {
            if (highlight)
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
            if (highlight)
            {


                float x = mercenaryViews.get(i).getX();
                float y = mercenaryViews.get(i).getY();
                float width = mercenaryViews.get(i).getWidth();
                float height = mercenaryViews.get(i).getHeight();

                batch.draw(highlightTexture, x-width*0.1f, y - height/2, width, height);
            }
            
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


    public void printActors()
    {
        for (int i = 0; i < mercenaryViews.size; i++)
        {
            System.out.println(mercenaryViews.get(i).getX()+"; "+ mercenaryViews.get(i).getY());
            System.out.println(mercenaryViews.get(i).getWidth()+"; "+ mercenaryViews.get(i).getHeight());
            System.out.println("\n");
            mercenaryViews.get(i).debug();
           // mercenaryViews.get(i).drawDebug(null);
           ShapeRenderer shapeRenderer = new ShapeRenderer();
              shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
              shapeRenderer.setAutoShapeType(true);
                shapeRenderer.setColor(Color.GREEN);
                 shapeRenderer.rect(mercenaryViews.get(i).getX(), mercenaryViews.get(i).getY(), mercenaryViews.get(i).getWidth(), mercenaryViews.get(i).getHeight());
                mercenaryViews.get(i).drawDebug(shapeRenderer);
                 shapeRenderer.end();

                 mercenaryViews.get(i).toFront();

        }
    }
    

}
