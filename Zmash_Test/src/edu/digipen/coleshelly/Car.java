package edu.digipen.coleshelly;

import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.graphics.Graphics;

/**
 * Created by jake.ostrow on 7/17/2017.
 */
public class Car extends Movement
{
	// Maximum Health
	int MaxHealth = 10000;
	// Current Health
	int Health = 10000;

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
	}

	public void applyDamage(int damage)
	{
		// check that damage is positive
		if (damage > 0)
		{
			// subtract damage from health
			Health -= damage;
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
