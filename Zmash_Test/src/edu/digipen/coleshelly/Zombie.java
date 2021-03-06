package edu.digipen.coleshelly;

import edu.digipen.InputManager;
import edu.digipen.SoundManager;
import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.math.PFRandom;
import edu.digipen.math.Vec2;

import java.awt.event.KeyEvent;

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

	// Blood sounds
	boolean bloodIsPlaying = false;

	boolean blood2IsPlaying = false;

	// Time when zombie lands
	public float landingTimer = 0;

	// Zombie mode; 0 = normal 'point and chase,' 1 = grabbed onto car, 2 = dead, 3 = stagnant;
	public int zombieMode = 3;

	// Car rope
	public GameObject rope = new GameObject("Rope", 200, 3, "rope.png");

	// On-car Offset
	float ropeOffset = PFRandom.randomRange(-30, 30);

	// The last location the car was in
	private Vec2 lastCarPos = new Vec2();

	// Remaining life timer
	private float remainingLifeTimer = 1;

	// Has the life timer started
	private boolean lifeTimerStarted = false;

	// Has the zombie died
	private boolean hasDied = false;

	// Velocity
	private Vec2 velocity = new Vec2(0, 0);

	// throw timer
	private float throwTimer = 2;

	// is the zombie dead
	public boolean isZombieDead = false;

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
		GameObject car = ObjectManager.getGameObjectByName("Car");

		// Make rope invisible
		rope.setOpacity(0);

		((Car)car).shakeOffTextActive = false;

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
			// shake off text true
			((Car)car).shakeOffTextActive = true;

			// Make rope visible
			rope.setOpacity(1);
			// Move rope to proper location
			rope.setPosition(car.getPosition());
			// Rope zOrder
			rope.setZOrder(0);

			// Rope offset
			Vec2 ropePositionOffset = Tools
					.GetVectorFromAngle(car.getRotation(), -19);

			// Add to rope position X
			rope.setPositionX(rope.getPositionX() + ropePositionOffset.getX());

			// Add to rope position Y
			rope.setPositionY(rope.getPositionY() + ropePositionOffset.getY());

			// The change in car pos over the y axis
			float rise = lastCarPos.getY() - car.getPositionY();

			// The change in car pos over the x axis
			float run = lastCarPos.getX() - car.getPositionX();

			// Rotation that points towards movement
			float rotation = Tools
					.GetAngleFromVector(((Movement) car).getMovementVelocity());

			// Add rope offset to rotation
			rotation += ropeOffset;

			// Let last car pos
			lastCarPos = car.getPosition();

			// Slowly rotate to car's rotation
			rope.setRotation((rotation));

			// Move zombie onto end of rope
			Vec2 position = Tools.GetVectorFromAngle(rotation, -100);
			this.setPosition(rope.getPositionX() + position.getX(),
					rope.getPositionY() + position.getY());

			// Zombie trail
			GameObject carTrail1 = new CarTrail(this.getPositionX(), this.getPositionY(), 0.0001f);
			ObjectManager.addGameObject(carTrail1);

			// If car swerves
			if (((Car) car).turnTime > 4.2)
			{
				// Throw zombie off
				zombieMode = 0;

				// Turning right
				if (((Car) car).turnSpeed > 0)
				{
					// Set velocity
					velocity.set(Tools.GetVectorFromAngle(rope.getRotation() - 90,
									300));
				} else
				{
					// Set velocity
					velocity.set(Tools.GetVectorFromAngle(rope.getRotation() + 90,
									300));

				}
			}

			// if throw timer is up
			if (throwTimer < 0)
			{
				// throw
				GameObject throwingRock = new ThrowingRock(this.getPosition());
				// add
				ObjectManager.addGameObject(throwingRock);

				// apply damage to car
				((Car) car).applyDamage(1);
				// screen shake
				((Car) car).shakeScreen(0.2f);

				// reset throw timer
				throwTimer = 2;
			}

			// decrement throw timer
			throwTimer -= dt;
		}

		////////////////////////////////// ZOMBIE-CAR COLLISION /////////////////////////////////////////

		// If the zombie collides with the car
		if (checkCircleCircleCollision(this.getPosition(), this.getWidth() / 2,
				car.getPosition(), car.getWidth() / 2))
		{
			// TAKE HEALTH FROM THE CAR
			((Car) car).applyDamage(1);
			if (zombieMode == 0)
			{
				// Attach to the car
				zombieMode = 1;
			}
		}

		///////////////////////////////// CAR-ZOMBIE COLLISION ///////////////////////////////////////

		// Car rotation
		float carRotationRadians = (float) Math.toRadians(car.getRotation());

		// Collision circle location
		Vec2 collisionCirclePosition = new Vec2((float) (Math.cos(carRotationRadians) * 30),
				(float) (Math.sin(carRotationRadians) * 30));

		collisionCirclePosition
				.setX(collisionCirclePosition.getX() + car.getPositionX());
		collisionCirclePosition
				.setY(collisionCirclePosition.getY() + car.getPositionY());

		// If the zombie collides with the car
		if (checkCircleCircleCollision(this.getPosition(), this.getWidth() / 2,
				collisionCirclePosition, car.getWidth()))
		{
			// Car speed
			Vec2 velocityVector = (((Car) car).getMovementVelocity());

			// Speed
			float speed = velocityVector.getX() / velocityVector.getY();

			// Absolute value
			speed = Math.abs(speed);

			// Make sure car is moving, and make sure zombie mode is correct
			if (speed > 0.8f)
			{
				// Take health from zombie
				this.applyDamage(1);
			}
		}

		///////////////////////////////////////////// BOB ////////////////////////////////////////////////////

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
		} else
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
			// Decrement remaining life timer
			remainingLifeTimer -= dt;
		}

		// If remaining life timer < 0
		if (remainingLifeTimer < 0)
		{
			// Set mode to 'dead'
			zombieMode = 2;

			this.setVelocity(0, 0);
			this.stop();

			// Fade out
			this.setOpacity(0);

			// kill
			isZombieDead = true;

			if (hasDied == true)
			{
				// Add dead facade
				GameObject deadZombie = new GameObject("DeadZombie", 244 / 15,
						417 / 15, "zombieCrooked1.png");
				deadZombie.setPosition(this.getPosition());
				deadZombie.setRotation(90); // Rotate on it's side
				ObjectManager.addGameObject(deadZombie);

				// Add blood pool
				GameObject bloodPool = new BloodPool(this.getPosition());
				ObjectManager.addGameObject(bloodPool);

				((Car) car).shakeScreen(0.03f);
			}

			// Reset has died
			hasDied = false;
		}

		if (landingTimer > 0)
		{
			landingTimer -= dt;

			SoundManager.playBackgroundSound("Dirt3");
		}

		// Decay rate
		float decayRate = 2f;

		// X POSITIVE
		if (velocity.getX() > 0)
		{
			// Decay velocity
			velocity.setX(velocity.getX() - decayRate);
		}
		// X NEGATIVE
		if (velocity.getX() < 0)
		{
			// Decay velocity
			velocity.setX(velocity.getX() + decayRate);
		}
		// Y POSITIVE
		if (velocity.getY() > 0)
		{
			// Decay velocity
			velocity.setY(velocity.getY() - decayRate);
		}
		// Y NEGATIVE
		if (velocity.getY() < 0)
		{
			// Decay velocity
			velocity.setY(velocity.getY() + decayRate);
		}

		// Set velocity on zombie
		this.setVelocity(velocity);

		//////////////////////////////// START /////////////////////////////////////////
		// if arrow keys pressed
		if (InputManager.isTriggered(KeyEvent.VK_UP))
		{
			if (zombieMode == 3)
			{
				// make zombies move
				zombieMode = 0;
			}
		}

	}

	/**
	 * ************************************************************************
	 * This function determines whether or not two objects with circle colliders
	 * are colliding.
	 *
	 * @param c1Position The position (x, y coordinate) of the first circle
	 * @param c1Radius   The radius of the first circle
	 * @param c2Position The position (x, y coordinate) of the second circle
	 * @param c2Radius   The radius of the second circle
	 * @return TRUE if the two circles are colliding; FALSE if they are NOT
	 * colliding
	 * ************************************************************************
	 */
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
		} else
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

			Vec2 throwVelocity = new Vec2(((Car) car).getMovementVelocity());
			throwVelocity.scale(5f);
			velocity = throwVelocity;

			// Subtract damage from health
			ZombieHealth -= damage;

			if (ZombieHealth == 0)
			{
				// set remaining life timer
				remainingLifeTimer = 3;

				// Start timer
				lifeTimerStarted = true;

				// Set the has died variable
				hasDied = true;

				// Make sure the zombie doesn't die multiple times
				ZombieHealth -= 1;

				// Subtle screen shake
				((Car) car).shakeScreen(0.04f);

				if (bloodIsPlaying == false)
				{
					// Play sound
					SoundManager.playSoundEffect("Gravel3");

					bloodIsPlaying = true;
				}
				else
				{
					// Stop sound
					SoundManager.stopSoundEffect("Gravel3");

					bloodIsPlaying = false;
				}

				if (blood2IsPlaying == false)
				{
					// Play sound
					SoundManager.playSoundEffect("Grass4");

					blood2IsPlaying = true;
				}
				else
				{
					// Stop sound
					SoundManager.stopSoundEffect("Grass4");

					blood2IsPlaying = false;
				}
			}
		}
	}
}
