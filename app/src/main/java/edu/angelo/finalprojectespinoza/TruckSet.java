package edu.angelo.finalprojectespinoza;

// Darian Espinoza
// CS 3372

import java.util.ArrayList;
import java.util.List;

public class TruckSet {
    public List<Truck> trucks;

    /**
     * Create Truck objects using default x coordinate and list of y coordinates
     * @param xVelocity X velocity value that determines which way the truck goes
     * @param yCoords Y-coordinates that determine where up and down on the screen the truck starts
     */
    public TruckSet(int xVelocity, int[] yCoords) {
        trucks = new ArrayList<Truck>();
        for (int i = 0; i < yCoords.length; i += 1) {
            Truck temp = new Truck(xVelocity, yCoords[i]);
            trucks.add(temp);
        }
    }

    public void advance() {
        for (Truck t : trucks) {
            t.advance();
        }
    }
}
