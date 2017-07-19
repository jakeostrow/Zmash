package edu.digipen.coleshelly;

import edu.digipen.InputManager;
import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.level.GameLevel;
import edu.digipen.level.GameLevelManager;

import java.awt.event.KeyEvent;

/**
 * Created by jake.ostrow on 7/18/2017.
 */
public class HomeScreen extends GameLevel
{

	@Override public void create()
	{
		// add title
		GameObject title = new GameObject("Title", 1268 / 4, 495 / 4, "zmashLogoBackgroundless.png");
		ObjectManager.addGameObject(title);

		// add background
		GameObject bg = new GameObject("Title", 2000, 2000, "backgroundSquare.png");
		bg.setZOrder(-1);
		ObjectManager.addGameObject(bg);

		GameObject Button = new GameObject("Title", 605 / 2, 75 / 2, "pressToPlayPrompt.png");
		Button.setPosition(-0, -150);
		ObjectManager.addGameObject(Button);
	}

	@Override public void initialize()
	{

	}

	@Override public void update(float v)
	{
		// check if space button is pressed
		if (InputManager.isTriggered(KeyEvent.VK_SPACE))
		{
			// change scene
			GameLevelManager.goToLevel(new Level1());
		}
	}

	@Override public void uninitialize()
	{

	}
}
