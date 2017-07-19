package edu.digipen.coleshelly;

import edu.digipen.InputManager;
import edu.digipen.SoundManager;
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

	private boolean hasPlayed = false;

	@Override public void create()
	{
		// Add title
		GameObject title = new GameObject("Title", 1268 / 4, 495 / 4, "zmashLogoBackgroundless.png");
		title.setPosition(0, 50);
		ObjectManager.addGameObject(title);

		// Add background
		GameObject bg = new GameObject("Title", 2000, 2000, "backgroundSquare.png");
		bg.setZOrder(-1);
		ObjectManager.addGameObject(bg);

		GameObject Button = new GameObject("Title", 605 / 2, 75 / 2, "pressToPlayPrompt.png");
		Button.setPosition(0, -80);
		ObjectManager.addGameObject(Button);

		//////////////SOUND////////////////
		SoundManager.addBackgroundSound("Credits", "Credits.wav", true);
		// Load LevelMusic into computer's memory
		SoundManager.addBackgroundSound("Credits", "Credits.wav", true);
	}

	@Override public void initialize()
	{
		SoundManager.playBackgroundSound("Credits");

	}

	@Override public void update(float v)
	{
		// Check if space button is pressed
		if (InputManager.isTriggered(KeyEvent.VK_SPACE))
		{
			// change scene
			GameLevelManager.goToLevel(new Level1());

			if(InputManager.isTriggered(KeyEvent.VK_SPACE))
			{
				SoundManager.stopBackgroundSound("Credits");
			}
		}
	}

	@Override public void uninitialize()
	{

	}
}
