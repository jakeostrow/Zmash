package edu.digipen.coleshelly;

import edu.digipen.InputManager;
import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.graphics.Graphics;

import java.awt.event.KeyEvent;

/**
 * Created by cole.shelly on 7/21/2017.
 */
public class ArrowKeyPrompt extends GameObject
{

	GameObject car;

	public ArrowKeyPrompt()
	{
		super("ArrowKeyPrompt", 100, 100, "Key Prompt.png", 8, 1, 8, 0.5f);
		car = ObjectManager.getGameObjectByName("Car");
		this.setZOrder(4);

		this.play();
	}

	@Override public void update(float dt)
	{

		this.setPosition(Graphics.getCameraPosition());

		this.setPositionY(this.getPositionY() + Graphics.getWindowHeight() / 2 - 100);

		if (InputManager.isTriggered(KeyEvent.VK_UP) || InputManager.isTriggered(KeyEvent.VK_DOWN) || InputManager.isTriggered(KeyEvent.VK_LEFT) || InputManager.isTriggered(KeyEvent.VK_RIGHT))
		{
			this.kill();
		}

	}

}
