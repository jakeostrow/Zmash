package edu.digipen.coleshelly;

import edu.digipen.SoundManager;
import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.graphics.Graphics;
import edu.digipen.level.GameLevelManager;
import edu.digipen.math.PFRandom;
import edu.digipen.math.Vec2;

/**
 * Created by jake.ostrow on 7/17/2017.
 */
public class Car extends Movement
{
	// Maximum Health
	public int MaxHealth = 10;

	// Current Health
	public int Health = 0;

	// Is the car drowning
	private boolean drowning = false;

	// Screen shake timer
	public float screenShakeTimer = 0;

	// Has the splash sound played?
	private boolean hasPlayed = false;

	// Has the explosion sound played?
	private boolean explosionIsPlayed = false;


	public Car()
	{
		super("Car", 20, 20, "");

		// Add facade to front
		GameObject carFacade = new CarFacade();
		ObjectManager.addGameObject(carFacade);
	}

	@Override
	public void update(float dt)
	{
		// Move car through movement class
		checkInput(dt);

		// Camera follow
		Graphics.setCameraPosition(this.getPosition());

		/////////////////////////////////// CAR TRAIL ///////////////////////////////////////////

		// Rotation
		float rotation1 = (float)Math.toRadians(this.getRotation() - 15);
		// Rotation
		float rotation2 = (float)Math.toRadians(this.getRotation() + 15);

		// X offset
		float xOffset1 = (float) (Math.cos(rotation1) * -15);
		// Y offset
		float yOffset1 = (float) (Math.sin(rotation1) * -15);

		// X offset
		float xOffset2 = (float)(Math.cos(rotation2) * -15);
		// Y offset
		float yOffset2 = (float)(Math.sin(rotation2) * -15);
		// X offset

		// X offset
		float xOffset3 = (float) (Math.cos(rotation1) * 18);
		// Y offset
		float yOffset3 = (float) (Math.sin(rotation1) * 18);

		// X offset
		float xOffset4 = (float)(Math.cos(rotation2) * 18);
		// Y offset
		float yOffset4 = (float)(Math.sin(rotation2) * 18);
		
		// Add trail
		GameObject carTrail1 = new CarTrail(this.getPositionX() + xOffset1, this.getPositionY() + yOffset1, 3);
		ObjectManager.addGameObject(carTrail1);

		GameObject carTrail2 = new CarTrail(this.getPositionX() + xOffset2, this.getPositionY() + yOffset2, 3);
		ObjectManager.addGameObject(carTrail2);

		GameObject carTrail3 = new CarTrail(this.getPositionX() + xOffset3, this.getPositionY() + yOffset3, 3);
		ObjectManager.addGameObject(carTrail2);

		GameObject carTrail4 = new CarTrail(this.getPositionX() + xOffset4, this.getPositionY() + yOffset4, 3);
		ObjectManager.addGameObject(carTrail2);

		///////////////////////////////// SCREEN SHAKE /////////////////////////////////////////////

		// If screen shake timer still has time on it
		if (screenShakeTimer > 0)
		{
			// Shake screen
			Graphics.setCameraPosition(Graphics.getCameraPosition().getX() + PFRandom.randomRange(-5, 5),
					                   Graphics.getCameraPosition().getY() + PFRandom.randomRange(-5, 5));
		}

		// Deduct from screen shake timer
		screenShakeTimer -= dt;


		/////////////////////////////////// DROWNING /////////////////////////////////////////////////

		// Get the island object
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

			// Random splash
			GameObject splash = new Splash(new Vec2(this.getPositionX() + PFRandom.randomRange(-20, 20),
					this.getPositionY() + PFRandom.randomRange(-20, 20)));

			// Play sound once you drown
			if (hasPlayed == false)
			{
				SoundManager.playSoundEffect("WaterSplash");

				hasPlayed = true;
			}
			// Reset position
			if (carFacade.getOpacity() < 0)
			{
				// Reset position
				this.setPosition(0, 0);
				// Reset opacity
				carFacade.setOpacity(1);
				// Reset drowning
				drowning = false;
				// Sound has played
			}
		}
	}

	public void applyDamage(int damage)
	{
		// Check that damage is positive
		if (damage > 0)
		{
			// Subtract damage from health
			Health -= damage;
		}
		if (Health < 0)
		{
//			EndGameDropDown endGameDropDown = new EndGameDropDown(false);
//			endGameDropDown.bringDown();

			if (explosionIsPlayed == false)
			{
				// Play sound
				SoundManager.playSoundEffect("Explosion7");

				explosionIsPlayed = true;
			}
			else
			{
				// Stop sound
				SoundManager.stopBackgroundSound("Explosion7");

				explosionIsPlayed = false;
			}

			// Restart level
			GameLevelManager.restartLevel();


		}
	}

	public void heal(int amount)
	{
		// Check that amount is positive
		if (amount > 0)
		{
			// Add health by amount
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
		// Rise
		float rise = lPoint2.getY() - lPoint1.getY();

		// Run
		float run = lPoint2.getX() - lPoint1.getX();

		// The slope
		float m = rise / run;

		// The x location of the point
		float x = pointPosition.getX();

		// The y-intercept
		float b = lPoint1.getY();

		// Get y from equation
		float y = (m * x) + b;

		// If checking above
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


		// If no return yet, return
		return false;
	}

}
