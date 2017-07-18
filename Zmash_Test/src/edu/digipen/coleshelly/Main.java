package edu.digipen.coleshelly;

import edu.digipen.Game;

public class Main {

    public static void main(String[] args)
    {
        Game.initialize(800, 600, 60, new HomeScreen());

        while(Game.getQuit() == false)
        {
            Game.update();
        }

        Game.destroy();

    }
}
