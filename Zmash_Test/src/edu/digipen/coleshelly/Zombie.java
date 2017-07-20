package edu.digipen.coleshelly;

import edu.digipen.SoundManager;
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
	public int MaxHealth = 6;

	// Current health
	public int ZombieHealth = 1;

	// Bob time
	public float bobTime = 0.2f;

	// Time between bobs
	public float bobTimer = bobTime;

	// Whether or not zombie is bobbing up, if not zombie is bobbing down
	boolean bobUp = true;

	// Sound for when zombie lands when bobbing
	boolean landingIsPlaying = false;

	boolean bloodIsPlaying = false;

	// Time when zombie lands
	public float landingTimer = 0;

	// Zombie mode; 0 = normal 'point and chase,' 1 = grabbed onto car, 2 = dead;
	public int zombieMode = 0;

	// Car rope
	public GameObject rope = new GameObject("Rope", 200, 3, "rope.png");

	// On-car Offset
	private Vec2 onCarOffset = new Vec2(PFRandom.randomRange(-20, 20), PFRandom.randomRange(-20, 20));


	public Zombie(String name, float speed_)
	{
		// Call the base constructor
		super(name, 190 / 15, 408 / 15, "zombie3d.png");

		// Set speed
		Speed = speed_;

		// Add rope
//		ObjectManager.addGameObject(rope);
		// Opacity
		rope.setOpacity(0);
	}

	@Override public void initialize()
	{
		// Health
		ZombieHealth = MaxHealth;
	}

	@Override public void update(float dt)
	{
		/////////////////////////////////////// ESSENTIALS ///////////////////////////////////////////

		// Get the car from the object manager
		GameObject car = ObjectManager
				.getGameObjectByName("Car");

		///////////////////////////// ZOMBIE PATH-FINDING ( MODE 0 ) ////////////////////////////////

		// CHECK THAT ZOMBIE IS IN CORRECT MODE
		if (zombieMode == 0)
		{
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
		}

		///////////////////////////////// ATTACH TO CAR ( MODE 1 ) /////////////////////////////////////

		// CHECK THAT ZOMBIE IS IN CORRECT MODE
		if (zombieMode == 1)
		{
			// Make rope visible
			rope.setOpacity(1);
			// Move rope to proper location
			rope.setPosition(car.getPosition());
			// Rope zOrder
			rope.setZOrder(0);

			// Slowly rotate to car's rotation
			rope.setRotation((car.getRotation() + rope.getRotation()) * 0.1f);

		}

		////////////////////////////////// ZOMBIE-CAR COLLISION /////////////////////////////////////////

		// If the zombie collides with the car
		if (checkCircleCircleCollision(this.getPosition(), this.getWidth() / 2, car.getPosition(), car.getWidth() / 2))
		{
			// TAKE HEALTH FROM THE CAR
//			((Car)car).applyDamage(1);

			// Attach to the car
			zombieMode = 1;
		}

		///////////////////////////////// CAR-ZOMBIE COLLISION //////////////////////////////////////////////

		// Car rotation
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

		// If timer reaches zero
		if (bobTimer < 0)
		{
			// Toggle bob
			bobUp = !bobUp;

			// Reset bob timer
			bobTimer = bobTime;
		}

		// If bob up is true, bob up. else, bob down
		if (bobUp)
		{
			this.setPositionY(this.getPositionY() + 0.4f);

			// Sound play
			if (landingIsPlaying == false)
			{
				SoundManager.playBackgroundSound("Dirt3");

				landingIsPlaying = true;
			}
		}
		else
		{
			this.setPositionY(this.getPositionY() - 0.4f);

			// Sound stop
			SoundManager.stopBackgroundSound("Dirt3");

			landingIsPlaying = false;
		}

		// Decrement timer by time
		bobTimer -= dt;

		 if (landingTimer > 0)
		 {
			 landingTimer -= dt;

			 SoundManager.playBackgroundSound("Dirt3");
		 }

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
				if (bloodIsPlaying == false)
				{
					// Play sound
					SoundManager.playSoundEffect("Gravel3");

					bloodIsPlaying = true;
				}
				else
				{
					// Stop sound
					SoundManager.stopBackgroundSound("Gravel3");

					bloodIsPlaying = false;
				}

				this.kill();
			}
		}
	}
}
