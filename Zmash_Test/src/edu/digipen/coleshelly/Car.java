package edu.digipen.coleshelly;

import edu.digipen.InputManager;
import edu.digipen.SoundManager;
import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.graphics.Graphics;
import edu.digipen.math.PFRandom;
import edu.digipen.math.Vec2;

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
	// is the car drowning
	private boolean drowning = false;

	private boolean hasPlayed = false;


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

		/////////////////////////////////// CAR TRAIL ///////////////////////////////////////////

		// rotation
		float rotation1 = (float)Math.toRadians(this.getRotation() - 15);
		// rotation
		float rotation2 = (float)Math.toRadians(this.getRotation() + 15);

		// x offset
		float xOffset1 = (float) (Math.cos(rotation1) * -20);
		// y offset
		float yOffset1 = (float) (Math.sin(rotation1) * -20);

		// x offset
		float xOffset2 = (float)(Math.cos(rotation2) * -20);
		// y offset
		float yOffset2 = (float)(Math.sin(rotation2) * -20);

		// x offset
		float xOffset3 = (float) (Math.cos(rotation1) * 18);
		// y offset
		float yOffset3 = (float) (Math.sin(rotation1) * 18);

		// x offset
		float xOffset4 = (float)(Math.cos(rotation2) * 18);
		// y offset
		float yOffset4 = (float)(Math.sin(rotation2) * 18);

		// add trail
		GameObject carTrail1 = new CarTrail(this.getPositionX() + xOffset1, this.getPositionY() + yOffset1, 3);
		ObjectManager.addGameObject(carTrail1);

		GameObject carTrail2 = new CarTrail(this.getPositionX() + xOffset2, this.getPositionY() + yOffset2, 3);
		ObjectManager.addGameObject(carTrail2);

		GameObject carTrail3 = new CarTrail(this.getPositionX() + xOffset3, this.getPositionY() + yOffset3, 3);
		ObjectManager.addGameObject(carTrail2);

		GameObject carTrail4 = new CarTrail(this.getPositionX() + xOffset4, this.getPositionY() + yOffset4, 3);
		ObjectManager.addGameObject(carTrail2);

		///////////////////////////////// SCREEN SHAKE /////////////////////////////////////////////

		// if s key is pressed
		if (InputManager.isPressed(KeyEvent.VK_S))
		{
			// shake screen
			Graphics.setCameraPosition(Graphics.getCameraPosition().getX() + PFRandom.randomRange(-5, 5),
					                   Graphics.getCameraPosition().getY() + PFRandom.randomRange(-5, 5));
		}

		/////////////////////////////////// DROWNING /////////////////////////////////////////////////

		// get the island object
		GameObject land = ObjectManager.getGameObjectByName("Background");

		// Top right side of island
		if (checkPointLineCollision(this.getPosition(),
				                    new Vec2(0, land.getHeight() / 2 + 100),
				                    new Vec2(land.getWidth() / 2 + 100, 0),
				                    true))
		{
			drowning = true;
		}

		// Top left side of island
		if (checkPointLineCollision(this.getPosition(),
				new Vec2(0, land.getHeight() / 2 + 100),
				new Vec2(-land.getWidth() / 2 - 100, 0),
				true))
		{
			drowning = true;
		}

		// Bottom right side of island
		if (checkPointLineCollision(this.getPosition(),
				new Vec2(0, -land.getHeight() / 2 - 100),
				new Vec2(land.getWidth() / 2 + 100, 0),
				false))
		{
			drowning = true;
		}

		// Bottom left side of island
		if (checkPointLineCollision(this.getPosition(),
				new Vec2(0, -land.getHeight() / 2 - 100),
				new Vec2(-land.getWidth() / 2 - 100, 0),
				false))
		{
			drowning = true;
		}

		if (drowning)
		{
			// Fetch car facade
			GameObject carFacade = ObjectManager
					.getGameObjectByName("CarFacade");

			// Fade out car
			carFacade.setOpacity(carFacade.getOpacity() - 0.01f);

			// random splash
			GameObject splash = new Splash(new Vec2(this.getPositionX() + PFRandom.randomRange(-20, 20),
					this.getPositionY() + PFRandom.randomRange(-20, 20)));

			// Play sound once you drown
			if (hasPlayed == false)
			{
				SoundManager.playSoundEffect("WaterSplash");

				hasPlayed = true;
			}
			// reset position
			if (carFacade.getOpacity() < 0)
			{
				// reset position
				this.setPosition(0, 0);
				// reset opacity
				carFacade.setOpacity(1);
				// reset drowning
				drowning = false;
				// Sound has played
			}


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


	/**
	 * ************************************************************************
	 * This function determines whether or not two objects, one is a point, and
	 * one is a line, are colliding
	 *
	 * @param pointPosition - The position (x,y coordinate) of the point
	 * @param lPoint1       - The line's first point
	 * @param lPoint2       - The line's first point
	 * @param above         - Are we looking above, or below the line
	 *                      rectangle
	 * @return TRUE if the objects are colliding; FALSE if they are NOT
	 * colliding
	 * ************************************************************************
	 */

	boolean checkPointLineCollision(Vec2 pointPosition, Vec2 lPoint1, Vec2 lPoint2, boolean above)
	{
		// rise
		float rise = lPoint2.getY() - lPoint1.getY();

		// run
		float run = lPoint2.getX() - lPoint1.getX();

		// the slope
		float m = rise / run;

		// the x location of the point
		float x = pointPosition.getX();

		// the y-intercept
		float b = lPoint1.getY();

		// Get y from equation
		float y = (m * x) + b;

		// if checking above
		if (above)
		{
			if (y < pointPosition.getY())
			{
				return true;
			}
		}
		else
		{
			if (y > pointPosition.getY())
			{
				return true;
			}
		}


		// if no return yet, return
		return false;
	}

}
