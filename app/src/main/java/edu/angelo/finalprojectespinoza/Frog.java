package edu.angelo.finalprojectespinoza;

// Darian Espinoza
// CS 3372

public class Frog {
    // directional variables for keeping track of frog's facing direction to know which frog image to draw
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;
    public int direction;

    // x and y coordinates of the frog
    public int x;
    public int y;

    // initialize the frog facing upwards and its position near center of bottom row of playing area
    public Frog() {
        direction = UP;
        x = 5;
        y = 12;
    }

    // hop to the left
    public void hopLeft() {
        direction = LEFT;
        if (x == 0) {
            // do nothing
        } else {
            x -= 1;
        }
    }

    // hop to the right
    public void hopRight() {
        direction = RIGHT;
        if (x == 9) {
            // do nothing
        } else {
            x += 1;
        }
    }

    // hop upwards
    public void hopUp() {
        direction = UP;
        if (y == 0) {
            // do nothing
        } else {
            y -= 1;
        }
    }

    public void hopDown() {
        direction = DOWN;
        if (y == 12) {
            // do nothing
        } else {
            y += 1;
        }
    }
}
