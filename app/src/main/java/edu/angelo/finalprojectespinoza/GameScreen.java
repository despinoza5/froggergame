package edu.angelo.finalprojectespinoza;

// Darian Espinoza
// CS 3372

import java.util.List;

import android.graphics.Color;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Screen;

public class GameScreen extends Screen {
    enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }

    GameState state = GameState.Ready;
    World world;
    int oldScore = 0;
    String score = "0";

    public GameScreen(Game game) {
        super(game);
        world = new World();
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        if (state == GameState.Ready) {
            updateReady(touchEvents);
        }
        if (state == GameState.Running) {
            updateRunning(touchEvents, deltaTime);
        }
        if (state == GameState.Paused) {
            updatePaused(touchEvents);
        }
        if (state == GameState.GameOver) {
            updateGameOver(touchEvents);
        }
    }

    private void updateReady(List<TouchEvent> touchEvents) {
        if (touchEvents.size() > 0) {
            state = GameState.Running;
        }
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i += 1) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x < 64 && event.y < 64) {
                    if (Settings.soundEnabled) {
                        Assets.paused.play(1);
                    }
                    state = GameState.Paused;
                    return;
                }
            }
            if (event.type == TouchEvent.TOUCH_DOWN) {
                if (event.x < 64 && event.y > 416) {
                    world.frog.hopLeft();
                }
                if (event.x > 256 && event.y > 416) {
                    world.frog.hopRight();
                }
                // up button input
                if (event.x > 192 && event.x < 256 && event.y > 416) {
                    world.frog.hopUp();
                }
                // down button input
                if (event.x < 128 && event.x > 64 && event.y > 416) {
                    world.frog.hopDown();
                }
            }
        }

        world.update(deltaTime);
        if (world.gameOver) {
            if (Settings.soundEnabled) {
                Assets.bitten.play(1);
            }
            state = GameState.GameOver;
        }
        if (oldScore != world.score) {
            oldScore = world.score;
            score = "" + oldScore;
            if (Settings.soundEnabled) {
                Assets.eat.play(1);
            }
        }
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i += 1) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x > 80 && event.x <= 240) {
                    if (event.y > 100 && event.y <= 148) {
                        if (Settings.soundEnabled) {
                            Assets.click.play(1);
                        }
                        state = GameState.Running;
                        return;
                    }
                    if (event.y > 148 && event.y < 196) {
                        if (Settings.soundEnabled) {
                            Assets.click.play(1);
                        }
                        game.setScreen(new MainMenuScreen(game));
                        return;
                    }
                }
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i += 1) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x >= 128 && event.x <= 192 &&
                        event.y >= 200 && event.y <= 264) {
                    if (Settings.soundEnabled) {
                        Assets.click.play(1);
                    }
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.background, 0, 0);
        drawWorld(world);
        if (state == GameState.Ready) {
            drawReadyUI();
        }
        if (state == GameState.Running) {
            drawRunningUI();
        }
        if (state == GameState.Paused) {
            drawPausedUI();
        }
        if (state == GameState.GameOver) {
            drawGameOverUI();
        }

        drawText(g, score, g.getWidth() / 2 - score.length() * 20 / 2, g.getHeight() - 42);
    }

    private void drawWorld(World world) {
        Graphics g = game.getGraphics();
        Frog frog = world.frog;
        int x, y;

        // draw a rectangle across the whole screen to set the background color
        g.drawRect(0, 0, g.getWidth(), g.getHeight(), Color.rgb(195, 225, 255));

        // draw the grass for the safe zones
        // starting zone
        g.drawRect(0, 384, 320, 32, Color.rgb(203, 245,66));
        // middle safe zone
        g.drawRect(0, 192, 320, 32, Color.rgb(203, 245, 66));
        // scoring zone at the end
        g.drawRect(0, 0, 320, 32, Color.rgb(203, 245, 66));

        // draw the road
        g.drawRect(0, 224, 320, 160, Color.rgb(96, 97, 93));

        // draw the right moving trucks
        for (Truck t : world.rightTrucks.trucks) {
            g.drawPixmap(Assets.truckRight, t.locationX - 32, (t.locationY * 32) - 16);
        }
        // draw the left moving trucks
        for (Truck t : world.leftTrucks.trucks) {
            g.drawPixmap(Assets.truckLeft, t.locationX - 32, (t.locationY * 32) - 16);
        }
        // draw the right moving logs
        for (Log l : world.rightLogs.logs) {
            g.drawPixmap(Assets.log, (l.locationX * 32) - 32, (l.locationY * 32));
        }
        // draw the left moving logs
        for (Log l : world.leftLogs.logs) {
            g.drawPixmap(Assets.log, (l.locationX * 32) - 32, (l.locationY * 32));
        }

        Pixmap frogMap = null;
        if (frog.direction == Frog.UP) {
            frogMap = Assets.frogUp;
        }
        if (frog.direction == Frog.LEFT) {
            frogMap = Assets.frogLeft;
        }
        if (frog.direction == Frog.DOWN) {
            frogMap = Assets.frogDown;
        }
        if (frog.direction == Frog.RIGHT) {
            frogMap = Assets.frogRight;
        }

        x = frog.x * 32 + 16;
        y = frog.y * 32 + 16;
        g.drawPixmap(frogMap, x - frogMap.getWidth() / 2, y - frogMap.getHeight() / 2);
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.ready, 47, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);
        g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);
        // draw the up arrow from buttons.png
        g.drawPixmap(Assets.buttons, 192, 416, 0, 192, 64, 64);
        // draw the down arrow from buttons.png
        g.drawPixmap(Assets.buttons, 64, 416, 64, 192, 64, 64);
    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.pause, 80, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.gameOver, 62, 100);
        g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    public void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i += 1) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    @Override
    public void pause() {
        if (state == GameState.Running) {
            state = GameState.Paused;
        }
        if (world.gameOver) {
            Settings.addScore(world.score);
            Settings.save(game.getFileIO());
        }
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
