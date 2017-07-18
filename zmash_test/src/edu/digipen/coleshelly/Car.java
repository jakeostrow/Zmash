package edu.digipen.coleshelly;

import edu.digipen.graphics.Graphics;

/**
 * Created by jake.ostrow on 7/17/2017.
 */
public class Car extends Movement
{
	// Maximum Health
	int maxHealth = 10000;
	// Current Health
	int health = 10000;

	public Car()
	{
		super("Car", 48 * 2, 48 * 2, "carSpritesheetSmall.png");

		// Set as animation
		this.animationData.numberOfColumns = 24;
		this.animationData.numberOfRows = 1;
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
			health -= damage;
		}
	}

	public void heal(int amount)
	{
		// check that amount is positive
		if (amount > 0)
		{
			// add health by amount
			health += amount;
		}
	}

}
