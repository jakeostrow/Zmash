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
	float ropeOffset = PFRandom.randomRange(-30, 30);

	// The last location the car was in
	private Vec2 lastCarPos = new Vec2();

	// remaining life timer
	private float remainingLifeTimer = 1;

	// has the life timer started
	private boolean lifeTimerStarted = false;

	// has the zombie died
	private boolean hasDied = false;


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

		// make rope invisible
		rope.setOpacity(0);

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
			this.setPositionX(this.getPositionX()
					+ vector.getX() * Speed);
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

			// rope offset
			Vec2 ropePositionOffset = Tools.GetVectorFromAngle(car.getRotation(), -19);

			// add to rope position X
			rope.setPositionX(rope.getPositionX() + ropePositionOffset.getX());
			// add to rope position Y
			rope.setPositionY(rope.getPositionY() + ropePositionOffset.getY());

			// the change in car pos over the y axis
			float rise = lastCarPos.getY() - car.getPositionY();

			// the change in car pos over the x axis
			float run = lastCarPos.getX() - car.getPositionX();

			// rotation that points towards movement
			float rotation = Tools.GetAngleFromVector(((Movement)car).getMovementVelocity());
			// add rope offset to rotation
			rotation += ropeOffset;
			// let last car pos
			lastCarPos = car.getPosition();
			// slowly rotate to car's rotation
			rope.setRotation((rotation));

			// move zombie onto end of rope
			Vec2 position = Tools.GetVectorFromAngle(rotation, -100);
			this.setPosition(rope.getPositionX() + position.getX(),
					rope.getPositionY() + position.getY());

			// zombie trail
			GameObject carTrail1 = new CarTrail(this.getPositionX(), this.getPositionY(), 0.0001f);
			ObjectManager.addGameObject(carTrail1);

			// if car swerves
			if (((Car)car).turnTime > 4.2)
			{
				// throw zombie off
				zombieMode = 0;
			}
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

		if (lifeTimerStarted == true)
		{
			// decrement remaining life timer
			remainingLifeTimer -= dt;
		}

		// if remaining life timer < 0
		if (remainingLifeTimer < 0)
		{
			// set mode to 'dead'
			zombieMode = 2;

			this.setVelocity(0, 0);
			this.stop();

			// fade out
			this.setOpacity(0);

			if (hasDied == true)
			{
				// add dead facade
				GameObject deadZombie = new GameObject("DeadZombie", 244 / 15, 417 / 15, "zombieCrooked1.png");
				deadZombie.setPosition(this.getPosition());
				ObjectManager.addGameObject(deadZombie);

				// add blood pool
				GameObject bloodPool = new BloodPool(this.getPosition());
				ObjectManager.addGameObject(bloodPool);

				((Car)car).shakeScreen(0.03f);
			}

			// reset has died
			hasDied = false;
		}

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

			GameObject car = ObjectManager.getGameObjectByName("Car");

			Vec2 throwVelocity = new Vec2(((Car)car).getMovementVelocity());
			throwVelocity.scale(2.5f);
			this.setVelocity(throwVelocity);

			// Subtract damage from health
			ZombieHealth -= damage;

			if (ZombieHealth == 0)
			{
				// set remaining life timer
				remainingLifeTimer = 1;

				// start timer
				lifeTimerStarted = true;

				// set the has died variable
				hasDied = true;

				// make sure the zombie doesnt die multiple times
				ZombieHealth -= 1;

				// subtle screen shake
				((Car)car).shakeScreen(0.03f);

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
			}
		}
	}
}
