package cchase.platformergame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;

public class CollectableObject
{
    private Rectangle bounds;
    protected SpriteBatch spriteBatch;
    protected Texture texture;
    protected Sprite sprite;
    protected OrthographicCamera camera;
    protected boolean touchingPlayer;
    protected boolean objectCollected;
    public CollectableObject(float x, float y)
    {
        spriteBatch = new SpriteBatch();
        texture = new Texture("debugSquare.png");
        sprite = new Sprite(texture);
        bounds = new Rectangle();
        bounds.x = x + 16f;
        bounds.y = y + 16f;
        sprite.setBounds(x,y,16f,16f);
        objectCollected = false;
    }

    public void render(SpriteBatch spriteBatch,float delta, OrthographicCamera camera)
    {
        this.spriteBatch = spriteBatch;
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        sprite.draw(spriteBatch);
        //input();
        //update(delta);
        //drawSprite("standing", position.x, position.y);
        spriteBatch.end();

        //System.out.println("Sprite X:" + sprite.getX() + " Sprite Y:" + sprite.getY());
        //System.out.println("Bounding X:" + bounds.getX() + " Bounding Y:" + bounds.getY());
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public void updateCamera(OrthographicCamera camera)
    {
        this.camera = camera;
    }

    public boolean isTouchingPlayer()
    {
        return touchingPlayer;
    }

    public void setTouchingPlayer(boolean touchingPlayer)
    {
        this.touchingPlayer = touchingPlayer;
    }

    public boolean isObjectCollected()
    {
        return objectCollected;
    }

    public void setObjectCollected(boolean objectCollected)
    {
        this.objectCollected = objectCollected;
    }
}
