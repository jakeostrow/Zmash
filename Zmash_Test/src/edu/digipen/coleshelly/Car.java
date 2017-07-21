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

	// For the resetTimer
	public float normalSpeed = 0;

	// Maximum Health
	public float MaxHealth = 70;

	// Current Health
	public float Health = 70;

	// Is the car drowning
	private boolean drowning = false;

	// Screen shake timer
	public float screenShakeTimer = 0;

	// Has the splash sound played?
	private boolean hasPlayed = false;

	// Has the explosion sound played?
	private boolean explosionIsPlayed = false;

	// Is the game over?
	boolean gameOver = false;

	// timer
	public float Timer = 0;

	// timer that runs when a car hits a rock
	public Timer slowDownTimer;

	// smoke active
	private boolean smokeActive = false;

	// health bar
	GameObject healthBar = new GameObject("HealthBar", 10, 800, "healthBar.png");

	// health bar's original height
	float healthBarOriginalHeight = healthBar.getHeight();

	// shake off text active
	boolean shakeOffTextActive = false;


	public Car()
	{
		super("Car", 20, 20, "");

		// Add facade to front
		GameObject carFacade = new CarFacade();
		ObjectManager.addGameObject(carFacade);

		// add health bar
		ObjectManager.addGameObject(healthBar);
		healthBar.setZOrder(5);

		// arrow key prompt
		GameObject arrowKeys = new ArrowKeyPrompt();
		ObjectManager.addGameObject(arrowKeys);

		// shake off text

		setRectangleCollider(20, 20);
		slowDownTimer = new Timer(0.5f);
		normalSpeed = super.getSpeed();
	}

	@Override
	public void update(float dt)
	{
		if(slowDownTimer.isReady)
		{
			setSpeed(normalSpeed);
		}

		this.Timer -= dt;

		if (Timer < -3)
		{
			setSpeed(getSpeed() * 0.5f);

		}

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

		if (InputManager.isPressed(KeyEvent.VK_S))
		{
			screenShakeTimer = 2;
		}


		/////////////////////////////////// DROWNING /////////////////////////////////////////////////

		// Get the island object
		GameObject land = ObjectManager.getGameObjectByName("Background");

		if (land != null)
		{
			// Top right side of island
			if (checkPointLineCollision(this.getPosition(), new Vec2(0, land.getHeight() / 2 + 50),
					new Vec2(land.getWidth() / 2 + 50, 0), true))
			{
				drowning = true;
			}

			// Top left side of island
			if (checkPointLineCollision(this.getPosition(), new Vec2(0, land.getHeight() / 2 + 50),
					new Vec2(-land.getWidth() / 2 - 50, 0), true))
			{
				drowning = true;
			}

			// Bottom right side of island
			if (checkPointLineCollision(this.getPosition(), new Vec2(0, -land.getHeight() / 2 - 50),
					new Vec2(land.getWidth() / 2 + 50, 0), false))
			{
				drowning = true;
			}

			// Bottom left side of island
			if (checkPointLineCollision(this.getPosition(), new Vec2(0, -land.getHeight() / 2 - 50),
					new Vec2(-land.getWidth() / 2 - 50, 0), false))
			{
				drowning = true;
			}
		}

		if (drowning)
		{
			// Fetch car facade
			GameObject carFacade = ObjectManager
					.getGameObjectByName("CarFacade");

			if (carFacade != null)
			{

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
					// Kill
					killCar();
				}
			}
		}

		///////////////////////////// SMOKE //////////////////////////////////////////
		// if health is critical
		if (Health < 10) {
			// activate smoke
			smokeActive = true;
		} else {
			// deactivate smoke
			smokeActive = false;
		}

		// if smoke is activated
		if (smokeActive)
		{
			// position
			Vec2 position = new Vec2(this.getPositionX() + Tools.GetVectorFromAngle(this.getRotation(), 20).getX(),
								     this.getPositionY() + Tools.GetVectorFromAngle(this.getRotation(), 20).getX());

			// smoke object
			GameObject smokeObject = new SmokeObject(this.getPosition());
			// add
			ObjectManager.addGameObject(smokeObject);
		}

		///////////////////////////////////////// HEALTH BAR /////////////////////////////////////////
		// health bar position
		healthBar.setPositionX(Graphics.getCameraPosition().getX() - (Graphics.getWindowWidth() / 2) + 50);
		healthBar.setPositionY(Graphics.getCameraPosition().getY() - healthBar.getHeight() / 4);

		// size based on health
		float scale = (Health / MaxHealth);

		// if scale is greater than zero
		if (scale > 0)
		{
			healthBar.setScaleY(scale);
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
			killCar();

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


		}
	}

	public void killCar()
	{
		if (gameOver == false)
		{
			// set game over
			gameOver = true;
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

	public void shakeScreen(float duration)
	{
		screenShakeTimer = duration;
	}

	@Override public void collisionReaction(GameObject collidedWith)
	{
		if (collidedWith instanceof CircleObstacle)
		{
//			getPosition().subtract(
//					Vec2.scale(super.getMovementVelocity(), super.dt));
			setSpeed(normalSpeed / 2);
			slowDownTimer.reset();


		}

	}
}
