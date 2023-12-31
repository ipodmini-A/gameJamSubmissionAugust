package cchase.platformergame.screens;

import cchase.platformergame.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * GameScreen is the main platformer screen of the game.
 * TODO: Rename this class.
 */
public class GameScreen extends ScreenAdapter
{
    PlatformerGame game;
    private SpriteBatch batch;
    World world;
    Player player;

    float x = 632f; // Spawn point
    float y = 806f;
    private boolean firstSpawnCheck = false;
    Texture darkOverlayTexture;
    Pixmap darkPixmap;
    float brightness = 0.7f;

    /**
     * Constructor to GameScreen. Accepts a PlatformerGame and assigns game, while creating a new SpriteBatch,
     * player and world.
     * @param game
     */
    public GameScreen(PlatformerGame game)
    {
        System.out.println("GameScreen created");
        this.game = game;
        batch = new SpriteBatch();
        player = new Player(x,y);
        //Gdx.input.setInputProcessor(inputProcessor);
        world = new World(player);
        darkPixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        darkPixmap.setColor(0, 0, 0, 1f); // Adjust the alpha value to control the darkness
        darkPixmap.fillRectangle(0, 0, darkPixmap.getWidth(), darkPixmap.getHeight());
        darkOverlayTexture = new Texture(darkPixmap);
        darkPixmap.dispose();
    }

    @Override
    public void show()
    {
        player.setDisableControls(false);
        if (firstSpawnCheck)
        {
            player.setPositionX(GameState.lastRecordedPlayerX);
            player.setPositionY(GameState.lastRecordedPlayerY);
        }
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new NewPlatformerInput(player));
        Gdx.input.setInputProcessor(multiplexer);

        // Resetting players movement
        player.setDownMove(false);
        player.setLeftMove(false);
        player.setRightMove(false);

        // Resetting players velocity
        player.setVelocity(0,0);

        world.music.play();

        firstSpawnCheck = true;
        super.show();
    }

    @Override
    public void hide()
    {
        GameState.gameScreen = game.getScreen();
        System.out.println("Hidden");
    }

    /**
     * render Renders the images on screen. First by clearing the screen, then running render from different classes
     * In this method you will also find game objectives, such as touching the end goal or touching an enemy.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0.0f, 0.1f, 0.2f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Render your game elements here

        batch.draw(darkOverlayTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        darkPixmap.setColor(0, 0, 0, brightness); // Adjust alpha value as needed

        batch.end();

        world.WorldUpdate(player); // Removing this results in a null crash. idk
        world.render(delta);
        /*
        if (world.isTouchingEndGoal())
        {
            game.setScreen(new EndScreen(game));
        }
         */

        /*
        Following code stores the state of the game screen and the player.
        TODO: Put into a method
         */
        if (world.isCollidingWithEnemy())
        {
            GameState.gameScreen = game.getScreen();
            player.setDisableControls(true);
            GameState.lastRecordedPlayerX = player.getPosition().x;
            System.out.println(GameState.lastRecordedPlayerX);
            GameState.lastRecordedPlayerY = player.getPosition().y;
            System.out.println(GameState.lastRecordedPlayerY);
            world.music.pause();
        }

        if (player.getHealth() <= 0)
        {
            game.setScreen(new EndScreen(game));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.I))
        {
            // idk
        }

        //System.out.println(player.isTouchingWall());
        //System.out.println(player.isTouchingWall());
    }

    @Override
    public void dispose()
    {
        batch.dispose();
        player.dispose();
        world.dispose();
        darkOverlayTexture.dispose();
    }
}
