package edu.angelo.finalprojectespinoza;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.Graphics.PixmapFormat;

public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }

    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.background = g.newPixmap("background.png", PixmapFormat.ARGB8888);
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB8888);
        Assets.mainMenu = g.newPixmap("mainmenu.png", PixmapFormat.ARGB8888);
        Assets.buttons = g.newPixmap("buttons.png", PixmapFormat.ARGB8888);
        // load in frog assets
        Assets.frogUp = g.newPixmap("frogup.png", PixmapFormat.ARGB8888);
        Assets.frogLeft = g.newPixmap("frogleft.png", PixmapFormat.ARGB8888);
        Assets.frogDown = g.newPixmap("frogdown.png", PixmapFormat.ARGB8888);
        Assets.frogRight = g.newPixmap("frogright.png", PixmapFormat.ARGB8888);
        Assets.help1 = g.newPixmap("help1.png", PixmapFormat.ARGB8888);
        Assets.help2 = g.newPixmap("help2.png", PixmapFormat.ARGB8888);
        Assets.help3 = g.newPixmap("help3.png", PixmapFormat.ARGB8888);
        Assets.log = g.newPixmap("log.png", PixmapFormat.ARGB8888);
        Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB8888);
        Assets.ready = g.newPixmap("ready.png", PixmapFormat.ARGB8888);
        Assets.pause = g.newPixmap("pausemenu.png", PixmapFormat.ARGB8888);
        Assets.gameOver = g.newPixmap("gameover.png", PixmapFormat.ARGB8888);

        // load in truck assets
        Assets.truckRight = g.newPixmap("truckright.png", PixmapFormat.ARGB8888);
        Assets.truckLeft = g.newPixmap("truckleft.png", PixmapFormat.ARGB8888);
        Assets.click = game.getAudio().newSound("click.ogg");
        Assets.eat = game.getAudio().newSound("eat.ogg");
        Assets.bitten = game.getAudio().newSound("gameover.ogg");
        Assets.paused = game.getAudio().newSound("paused.ogg");
        Settings.load(game.getFileIO());
        game.setScreen(new MainMenuScreen(game));
    }

    public void present(float deltaTime) {
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
    }
}
