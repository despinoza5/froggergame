package edu.angelo.finalprojectespinoza;

// Darian Espinoza
// CS 3372

import java.util.ArrayList;
import java.util.List;

public class LogSet {
    public List<Log> logs;

    /**
     * Create Log objects using default x coordinate and list of y coordinates
     * @param xVelocity X velocity value that determines which way the log goes
     * @param yCoords Y-coordinates that determine where up and down on the screen the log starts
     */
    public LogSet(int xVelocity, int[] yCoords) {
        logs = new ArrayList<Log>();
        for (int i = 0; i < yCoords.length; i += 1) {
            Log temp = new Log(xVelocity, yCoords[i]);
            logs.add(temp);
        }
    }

    public void advance() {
        for (Log l : logs) {
            l.advance();
        }
    }
}
