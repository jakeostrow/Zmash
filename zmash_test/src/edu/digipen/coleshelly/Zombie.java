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
	// Maximum Health
	public int MaxHealth = 10;

	// Current Health
	public int ZombieHealth = 1;



	public Zombie()
	{
		// Call the base constructor
		super("Rectangle", 40, 40, "GreenRectangle.jpg");
	}

	@Override public void initialize()
	{
		ZombieHealth = MaxHealth;

		setPosition(0, 0);
	}

	@Override
	public void update(float dt)
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
		this.setPositionX(this.getPositionX() + vector.getX());
		this.setPositionY(this.getPositionY() + vector.getY());
	}

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

	public void applydamage(int damage)
	{
		if (damage > 0)
		{
			ZombieHealth -= damage;
		}
	}

	@Override
	public void collisionReaction(GameObject collidedWith)
	{
		float x = PFRandom.randomRange(-3, 3);
		float y = PFRandom.randomRange(-3, 3);

		this.setPositionX(this.getPositionX() + x);
		this.setPositionY(this.getPositionY() + y);
	}
}


