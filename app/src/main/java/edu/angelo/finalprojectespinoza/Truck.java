package edu.angelo.finalprojectespinoza;

// Darian Espinoza
// CS 3372

import java.util.Random;

public class Truck {

    /**
     * Random object to use
     */
    private static final Random random = new Random();

    /**
     * The farthest left the center of a truck can go, leaving room for the radius of the truck.
     */
    public static final int minX = 16;

    /**
     * The farthest right the center of a truck can go, leaving room for the radius of the truck.
     */
    public static final int maxX = 303;

    /**
     * X coordinate of truck
     */
    public int locationX;

    /**
     * Y coordinate of truck
     */
    public int locationY;

    /**
     * X velocity of truck
     */
    public int velocityX;

    /**
     * Method for advancing truck across the screen using velocityX
     */
    public void advance() {
        locationX += velocityX;
        if (locationX < minX) {
            // only reaches below minX if traveling left, so send truck back to right and let it keep advancing
            locationX = 303;
        }
        if (locationX > maxX) {
            // only reaches above maxX if traveling right, so send truck back to left and let it keep advancing
            locationX = 16;
        }
    }

    /**
     * Constructor for placing trucks in specific location and setting their velocity
     * @param xVelocity Velocity of truck that determines which way it travels
     * @param y Y coordinate of truck to be placed
     */
    public Truck(int xVelocity, int y) {
        // spawn truck at random location on its lane
        locationX = random.nextInt(284) + 17;
        locationY = y;
        velocityX = xVelocity;
    }

}
