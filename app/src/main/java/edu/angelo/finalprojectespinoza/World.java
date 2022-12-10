package edu.angelo.finalprojectespinoza;

// Darian Espinoza
// CS 3372

// Increasing efficiency technique: LogSet and TruckSet, instead of creating and destroying objects over and over,
// these two classes reuse the same Log and Truck objects when they reach
// the edge of the screen, and simply send them back to the other side of the screen.
public class World {
    static final int SCORE_INCREMENT = 10;
    // changed tick initial from 0.50 to 0.36 to make the snake go slightly faster
    // felt that the initial speed was too slow to be both enjoyable and challenging
    // now it provides more of a challenge while still not being too fast to be enjoyable
    // and tick decrement was decreased from 0.05 to 0.02 so that the change in speed is more gradual
    // and very long games are more feasible
    static final float TICK_INITIAL = 0.36f;
    static final float TICK_DECREMENT = 0.02f;

    public Frog frog;
    // TruckSet for keeping track of trucks moving to the right
    public TruckSet rightTrucks;
    // array for right truck y locations
    int[] yRightTrucks  = {7, 9, 11};
    // TruckSet for keeping track of trucks moving to the left
    public TruckSet leftTrucks;
    // array for left truck y locations
    int[] yLeftTrucks = {8, 10};
    // LogSet for logs moving to the right
    public LogSet rightLogs;
    // array for y coordinates of right moving logs
    int[] yRightLogs = {2, 4};
    // LogSet for logs moving to the left
    public LogSet leftLogs;
    // array for y coordinates of left moving logs
    int[] yLeftLogs = {1, 3, 5};
    // int to make logs move slower
    int logSpeed;
    // boolean for tracking if frogger is on a right moving log
    boolean frogOnRightLog;
    // boolean for tracking if frogger is on left moving log
    boolean frogOnLeftLog;

    public boolean gameOver = false;
    public int score = 0;
    float tickTime = 0;
    float tick = TICK_INITIAL;

    public World() {
        frog = new Frog();
        rightTrucks = new TruckSet(5, yRightTrucks);
        leftTrucks = new TruckSet(-5, yLeftTrucks);
        rightLogs = new LogSet(1, yRightLogs);
        leftLogs = new LogSet(-1, yLeftLogs);
        logSpeed = 0;
        frogOnRightLog = false;
        frogOnLeftLog = false;
    }



    public void update(float deltaTime) {
        if (gameOver) {
            return;
        }

        // check if the right trucks and frog have collided
        for (Truck t : rightTrucks.trucks) {
            // if the truck and frog are in the same lane, check if frog is within hitbox
            if (t.locationY == frog.y) {
                 // if frog is within width of truck, truck is 64 pixels long
                if (Math.abs((frog.x * 32) - t.locationX) < 32) {
                    gameOver = true;
                    return;
                }
            }
        }
        // check if the left trucks and frog have collided
        for (Truck t : leftTrucks.trucks) {
            // if the truck and frog are in the same lane, check if frog is within hitbox
            if (t.locationY == frog.y) {
                // if frog is within width of truck, truck is 64 pixels long
                if (Math.abs((frog.x * 32) - t.locationX) < 32) {
                    gameOver = true;
                    return;
                }
            }
        }
        // check if frog is on top of right moving log, if so, move frog with log
        for (Log l : rightLogs.logs) {
            if (l.locationY == frog.y) {
                if (l.locationX == frog.x || l.locationX2 == frog.x) {
                    frogOnRightLog = true;
                    break;
                } else {
                    // frog is not on a log even though it is in that log's y-coordinate lane
                    // this means the frog is in the water, game over
                    gameOver = true;
                    break;
                }
            }
            frogOnRightLog = false;
        }
        // check if frog is on top of left moving log, if so, move frog with log
        for (Log l : leftLogs.logs) {
            if (l.locationY == frog.y) {
                if (l.locationX == frog.x || l.locationX2 == frog.x) {
                    frogOnLeftLog = true;
                    break;
                } else {
                    // frog is not on a log even though it is in that log's y-coordinate lane
                    // this means the frog is in the water, game over
                    gameOver = true;
                    break;
                }
            }
            frogOnLeftLog = false;
        }

        tickTime += deltaTime;

        while (tickTime > tick) {
            tickTime -= tick;
            rightTrucks.advance();
            leftTrucks.advance();
            logSpeed += 1;
            if (logSpeed > 2) {
                rightLogs.advance();
                leftLogs.advance();
                // when logs advance, if frog is on top of one, move him with them
                if (frogOnRightLog) {
                    // move frog to right
                    if (frog.x < 9) {
                        frog.x += 1;
                    }
                }
                if (frogOnLeftLog) {
                    if (frog.x > 0) {
                        frog.x -= 1;
                    }
                }
                logSpeed = 0;
            }
            // if the frog reaches the top of the screen, increment the score and send him back to the start
            if (frog.y == 0) {
                score += SCORE_INCREMENT;
                if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
                    tick -= TICK_DECREMENT;
                }
                frog.y = 12;
                frog.x = 5;
            }


        }
    }
}
