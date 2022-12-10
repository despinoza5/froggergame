package edu.angelo.finalprojectespinoza;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.AndroidGame;

public class FroggerGame extends AndroidGame {

    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}
