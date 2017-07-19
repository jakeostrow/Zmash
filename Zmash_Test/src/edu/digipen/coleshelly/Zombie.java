package edu.digipen.coleshelly;

import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.math.Vec2;

/**
 * Created by cole.shelly on 7/17/2017.
 */

public class Zombie extends GameObject
{
	// Speed of zombie's movement
	public float Speed = 4;

	// Maximum health
	public int MaxHealth = 1;

	// Current health
	public int ZombieHealth = 1;

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
	}

	@Override public void update(float dt)
	{
		///////////////////////////////////// ZOMBIE PATHFINDING ///////////////////////////////////////////

		// Get the car from the object manager
		GameObject car = ObjectManager
				.getGameObjectByName("Car");

		// Compute the vector from the enemy to the paddle
		// THIS IS P - E
		Vec2 vector = new Vec2();
		vector.setX(car.getPositionX() - this.getPositionX());
		vector.setY(car.getPositionY() - this.getPositionY());
		// Get the unit vector!
		vector.normalize();

		// Use the computed vector to move the enemy towards the player
		this.setPositionX(this.getPositionX() + vector.getX() * Speed);
		this.setPositionY(this.getPositionY() + vector.getY() * Speed);

		////////////////////////////////// ZOMBIE-CAR COLLISION /////////////////////////////////////////////

		// If the zombie collides with the car
		if (checkCircleCircleCollision(this.getPosition(), this.getWidth() / 2, car.getPosition(), car.getWidth() / 2))
		{
			// TAKE HEALTH FROM THE CAR
			((Car)car).applyDamage(10);
		}

		///////////////////////////////// CAR-ZOMBIE COLLISION //////////////////////////////////////////////\

		// car rotation
		float carRotationRadians = (float) Math.toRadians(car.getRotation());

		// Collision circle location
		Vec2 collisionCirclePosition = new Vec2((float) (Math.cos(carRotationRadians) * 30), (float) (Math.sin(carRotationRadians) * 30));

		collisionCirclePosition.setX(collisionCirclePosition.getX() + car.getPositionX());
		collisionCirclePosition.setY(collisionCirclePosition.getY() + car.getPositionY());

		// If the zombie collides with the car
		if (checkCircleCircleCollision(this.getPosition(), this.getWidth() / 2, collisionCirclePosition, car.getWidth() / 1.5f))
		{
			// Take health from zombie
			this.applyDamage(1);
		}

		//////////////////////////////////////////// BOB ////////////////////////////////////////////////////

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


	/***************************************************************************
	 * This function determines whether or not two objects with circle colliders
	 * are colliding.
	 * @param c1Position The position (x, y coordinate) of the first circle
	 * @param c1Radius	 The radius of the first circle
	 * @param c2Position The position (x, y coordinate) of the second circle
	 * @param c2Radius   The radius of the second circle
	 * @return TRUE if the two circles are colliding; FALSE if they are NOT
	 * 		   colliding
	 **************************************************************************/
	boolean checkCircleCircleCollision(Vec2 c1Position, float c1Radius, Vec2 c2Position,
			float c2Radius)
	{
		// Calculate the squared sum of radius1 and radius2
		float squaredSum = c1Radius * c1Radius + c2Radius * c2Radius;

		// Calculate the change in x and change in y
		float changeX = c2Position.getX() - c1Position.getX();
		float changeY = c2Position.getY() - c1Position.getY();

		// Calculate the distance squared using pythagroean therom x^2 + y^2 = c^2
		float distanceSquared = changeX * changeX + changeY * changeY;

		// If the distance squared is <= radii squared
		if (distanceSquared <= squaredSum)
		{
			// Return true
			return true;
		}
		else
		{
			// Otherwise, return false;
			return false;
		}
	}

	public void applyDamage(int damage)
	{
		// Check that damage is positive
		if (damage > 0)
		{

			// Subtract damage from health
			ZombieHealth -= damage;

			if (ZombieHealth < 0)
			{
				this.kill();
			}
		}
	}
}
