package edu.angelo.finalprojectespinoza;

// Darian Espinoza
// CS 3372

import java.util.Random;

public class Log {
    /**
     * Random object to use
     */
    private static final Random random = new Random();

    /**
     * The farthest left the center of a log can go, leaving room for the radius of the truck.
     */
    public static final int minX = 0;

    /**
     * The farthest right the center of a log can go, leaving room for the radius of the truck.
     */
    public static final int maxX = 10;

    /**
     * X coordinate of log
     */
    public int locationX;

    /**
     * Log is 2 blocks long, so use this for detection of other part of log
     */
    public int locationX2;

    /**
     * Y coordinate of log
     */
    public int locationY;

    /**
     * X velocity of log
     */
    public int velocityX;

    /**
     * Method for advancing log across the screen using velocityX
     */
    public void advance() {
        locationX += velocityX;
        if (locationX < minX) {
            // only reaches below minX if traveling left, so send log back to right and let it keep advancing
            locationX = 9;
        }
        if (locationX > maxX) {
            // only reaches above maxX if traveling right, so send log back to left and let it keep advancing
            locationX = 0;
        }
        locationX2 = locationX - 1;
    }

    /**
     * Constructor for placing log in specific location and setting their velocity
     * @param xVelocity Velocity of log that determines which way it travels
     * @param y Y coordinate of log to be placed
     */
    public Log(int xVelocity, int y) {
        // spawn log at random location on its lane
        locationX = random.nextInt(10);
        locationY = y;
        velocityX = xVelocity;
        locationX2 = locationX - 1;
    }
}
