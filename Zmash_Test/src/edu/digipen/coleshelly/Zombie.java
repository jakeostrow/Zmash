package edu.digipen.coleshelly;

import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.math.PFRandom;
import edu.digipen.math.Vec2;

/**
 * Created by cole.shelly on 7/17/2017.
 */

public class Zombie extends GameObject
{
	// Speed of zombie's movement
	public float Speed = 4;

	// Maximum health
	public int MaxHealth = 10;

	// Current health
	public int ZombieHealth = 1;

	// Damage
	public int MaxDamage = 15;

	// Current Damage
	public int Damage = 2;

	// bob time
	public float bobTime = 0.2f;

	// Time between bobs
	public float bobTimer = bobTime;

	// Whether or not zombie is bobbing up, if not zombie is bobbing down
	boolean bobUp = true;


	public Zombie(String name, float speed_)
	{
		// Call the base constructor
		super(name, 190 / 15, 408 / 15, "zombie3d.png");

		// set speed
		Speed = speed_;
	}

	@Override public void initialize()
	{
		// Health
		ZombieHealth = MaxHealth;

		// Damage
		Damage = MaxDamage;
	}

	@Override public void update(float dt)
	{
		GameObject zombie = ObjectManager.getGameObjectByName("Car");

		// Compute the vector from the enemy to the paddle
		// THIS IS P - E
		Vec2 vector = new Vec2();
		vector.setX(zombie.getPositionX() - this.getPositionX());
		vector.setY(zombie.getPositionY() - this.getPositionY());
		// Get the unit vector!
		vector.normalize();

		// Use the computed vector to move the enemy towards the player
		this.setPositionX(this.getPositionX() + vector.getX() * Speed);
		this.setPositionY(this.getPositionY() + vector.getY() * Speed);


		// Get the car from the object manager
		GameObject car = ObjectManager
				.getGameObjectByName("Car");

		// If the result from checkRectangleRectangleCollision is true
		if (checkRectangleRectangleCollision(this.getPosition(), this.getWidth() / 2, this.getHeight() / 2,
				car.getPosition(), car.getWidth() / 2, car.getHeight() / 2))
		{
			// Reset the position of the horizontal rectangle to (0,0)
			car.setPosition(PFRandom.randomRange(-400, 400),
					PFRandom.randomRange(-400, 400));
		}

		// if timer reaches zero
		if (bobTimer < 0)
		{
			// toggle bob
			bobUp = !bobUp;

			// reset bob timer
			bobTimer = bobTime;
		}

		// If bobup is true, bob up. else, bob down
		if (bobUp)
		{
			this.setPositionY(this.getPositionY() + 0.4f);
		}
		else
		{
			this.setPositionY(this.getPositionY() - 0.4f);
		}

		//  decrement timer by time
		bobTimer -= dt;
	}

	/**
	 * ************************************************************************
	 * This function determines whether or not two objects with rectangular
	 * colliders are colliding.
	 *
	 * @param r1Position   - The position (x,y coordinate) of the first
	 *                     rectangle
	 * @param r1HalfWidth  - The half width of the first rectangle
	 * @param r1HalfHeight - The half height of the first rectangle
	 * @param r2Position   - The position (x,y coordinate) of the second
	 *                     rectangle
	 * @param r2HalfWidth  - The half width of the second rectangle
	 * @param r2HalfHeight - The half height of the second rectangle
	 * @return TRUE if the two rectangles are colliding; FALSE if they are NOT
	 * colliding
	 * ************************************************************************
	 */

	boolean checkRectangleRectangleCollision(Vec2 r1Position, float r1HalfWidth,
			float r1HalfHeight, Vec2 r2Position, float r2HalfWidth, float r2HalfHeight)
	{
		float r1LeftCoordinate = r1Position.getX() + r1HalfWidth;
		//Left: Check if r1's right side is further left than r2's left side
		//If it is, there can be no collision, so return false.
		float r2RightCoordinate = r2Position.getX() - r2HalfWidth;
		//Right: Check if r1's left side is further right than r2's right side
		//If it is, there can be no collision, so return false.
		if (r1LeftCoordinate < r2RightCoordinate)
		{
			return false;
		}

		float r1RightCoordinate = r1Position.getX() - r1HalfWidth;

		float r2LeftCoordinate = r2Position.getX() + r2HalfWidth;

		if (r1RightCoordinate > r2LeftCoordinate)
		{
			return false;
		}
		//Bottom: Check if r1's top side is below r2's bottom side
		//If it is, there can be no collision, so return false.
		float r1BottomCoordinate = r1Position.getY() + r1HalfHeight;

		float r2TopCoordinate = r2Position.getY() - r2HalfHeight;

		if (r1BottomCoordinate < r2TopCoordinate)
		{
			return false;
		}
		//Top: Check if r1's bottom side is above r2's top side
		//If it is, there can be no collision, so return false.
		float r1TopCoordinate = r1Position.getY() - r1HalfHeight;

		float r2BottomCoordinate = r2Position.getY() + r2HalfHeight;

		if (r1TopCoordinate > r2BottomCoordinate)
		{
			return false;
		}
		//If none of those conditions are true, return true, as anything left is a collision.

		// DELETE THIS LINE OF CODE WHEN YOU ARE READY!! This is just a place
		// holder so that your program will compile!!
		return true;
	}

	public void applyDamge()
	{
		// Check that damage is positive
		if (Damage > 0)
		{

			// Subtract damage from health
			ZombieHealth -= Damage;

			if (ZombieHealth < 0)
			{
				this.kill();
			}
		}
	}
	public void endGameDropDown()
	{

	}
}

