package edu.digipen.coleshelly;

import edu.digipen.Game;
import edu.digipen.InputManager;
import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.level.GameLevel;

import java.awt.event.KeyEvent;

/**
 * Created by cole.shelly on 7/18/2017.
 */
public class DeathScreen extends GameLevel
{

	@Override public void create()
	{
		GameObject quitText = new GameObject("QuitText", 400, 100, "Day 2 Art/QuitText.png");
		ObjectManager.addGameObject(quitText);
		quitText.setPositionY(-100);
	}

	@Override public void initialize()
	{

	}

	@Override public void update(float v)
	{
		if(InputManager.isPressed(KeyEvent.VK_ESCAPE))
		{
			Game.quit();
		}
	}

	@Override public void uninitialize()
	{

	}
}
