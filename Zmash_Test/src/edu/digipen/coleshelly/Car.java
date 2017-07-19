package edu.digipen.coleshelly;

import edu.digipen.InputManager;
import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.graphics.Graphics;
import edu.digipen.math.PFRandom;

import java.awt.event.KeyEvent;

/**
 * Created by jake.ostrow on 7/17/2017.
 */
public class Car extends Movement
{
	// Maximum Health
	public int MaxHealth = 10000;
	// Current Health
	public int Health = 10000;

	public Car()
	{
		super("Car", 20, 20, "");

		// add facade to front
		GameObject carFacade = new CarFacade();
		ObjectManager.addGameObject(carFacade);
	}

	@Override
	public void update(float dt)
	{
		// move car through movement class
		checkInput(dt);

		// camera follow
		Graphics.setCameraPosition(this.getPosition());

		// rotation
		float rotation = (float)Math.toRadians(this.getRotation() + 90);

		// x offset
		float xOffset1 = (float) (Math.cos(rotation) * -10);
		// y offset
		float yOffset1 = (float) (Math.sin(rotation) * -10);

		// x offset
		float xOffset2 = (float)(Math.cos(rotation) * 10);
		// y offset
		float yOffset2 = (float)(Math.sin(rotation) * 10);

		// add trail
		GameObject carTrail1 = new CarTrail(this.getPositionX() + xOffset1, this.getPositionY() + yOffset1, 3);
		ObjectManager.addGameObject(carTrail1);

		GameObject carTrail2 = new CarTrail(this.getPositionX() + xOffset2, this.getPositionY() + yOffset2, 3);
		ObjectManager.addGameObject(carTrail2);

		// if s key is pressed
		if (InputManager.isPressed(KeyEvent.VK_S))
		{
			// shake screen
			Graphics.setCameraPosition(Graphics.getCameraPosition().getX() + PFRandom.randomRange(-5, 5),
					                   Graphics.getCameraPosition().getY() + PFRandom.randomRange(-5, 5));
		}

	}

	public void applyDamage(int damage)
	{
		// check that damage is positive
		if (damage > 0)
		{
			// subtract damage from health
			Health -= damage;
		}
		if (Health < 0)
		{
			EndGameDropDown endGameDropDown = new EndGameDropDown(false);
			endGameDropDown.bringDown();
		}
	}

	public void heal(int amount)
	{
		// check that amount is positive
		if (amount > 0)
		{
			// add health by amount
			Health += amount;
		}
	}
}
