package edu.digipen.coleshelly;

import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.math.PFRandom;
import edu.digipen.math.Vec2;

/**
 * Created by Rachel on 6/29/2017.
 */
public class ParticleSystem extends GameObject
{

	// This is the number of particles we would like to have in our particle system
	int numberOfParticles = 0;

	// This is the radius of the particle emitter.
	private float radius = 0;

	// This is the minimum speed particles could begin at
	float minSpeed = 0;

	// This is the maximum speed particles could begin at
	float maxSpeed = 0;

	// This is the min angle (direction) particles could begin traveling at
	float minAngle = 0;

	// This is the max angle (direction) particles could begin traveling at
	float maxAngle = 0;

	// This is the min possible time the particle may stay 'alive'
	float minLifeTimer = 0;

	// This is the max possible time the particle may stay 'alive'
	float maxLifeTimer = 0;

	/***************************************************************************
	 * Constructor - constructs a particle system game object
	 * @param 	numParticles 	- the number of particles in the particle
	 *                         system
	 * @param	newRadius	 	- the radius of the particle emitter
	 * @param	newMinSpeed	 	- the min possible speed for a particle to start at
	 * @param	newMaxSpeed	 	- the max possible speed for a particle to start at
	 * @param	newMinAngle	 	- the min possible angle for a particle to start at
	 * @param	newMaxAngle	 	- the max possible angle for a particle to start at
	 * @param 	newMinLifeTimer - the min possible time for the particle to stay
	 *                            alive
	 * @param 	newMaxLifeTimer	- the max possible time for the particle to stay
	 *                            alive
	 **************************************************************************/
	public ParticleSystem(int numParticles, float newRadius, float newMinSpeed,
			float newMaxSpeed, float newMinAngle, float newMaxAngle,
			float newMinLifeTimer, float newMaxLifeTimer, String name)
	{
		// Call the base class constructor with the following parameters:
		// Name: "ParticleSystem", Width: 1, Height: 1, Texture file: ""
		super(name, 1, 1, "");

		// Assign the member variables of the class using the variables
		// passed in to the function
		// number of particles
		numberOfParticles = numParticles;
		// the emission circle's radius
		radius = newRadius;
		// minimum speed of particle
		minSpeed = newMinSpeed;
		// maximum speed of particle
		maxSpeed = newMaxSpeed;
		// minimum angle of particle
		minAngle = newMinAngle;
		// maximum angle of particle
		maxAngle = newMaxAngle;
		// minimum life timer
		minLifeTimer = newMinLifeTimer;
		// maximum life timer
		maxLifeTimer = newMaxLifeTimer;

	}

	/**************************************************************************
	 * This function runs after the Constructor is called. In this function,
	 * we'll be generating new particles and adding it to the system
	 *
	 **************************************************************************/
	@Override
	public void initialize()
	{
		// a loop that runs for the numberOfParticles
		for (int i = 0; i <= numberOfParticles; i++)
		{
			// Generate a random value between minSpeed and maxSpeed and assign
			// that value to a new float variable named 'speed'
			// (HINT: Use the PFRandom class)
			float speed = PFRandom.randomRange(minSpeed, maxSpeed);

			// Generate a random value between minAngle and maxAngle and assign
			// that value to a new float variable named 'angle'
			float angle = PFRandom.randomRange(minAngle, maxAngle);

			// Generate a random value between minLifeTimer and maxLifeTimer
			// and assign that value to a new float variable named 'lifeTime'
			float lifeTime = PFRandom.randomRange(minLifeTimer, maxLifeTimer);

			// Need to find the direction we want to have our particle moving, so
			// Convert our angle to radians (HINT: There's a handy function in the Math class to do this)
			// HINT: Assign the radians value back to angle
			// Also don't forget to cast the result back to a float
			angle = (float)Math.toRadians(angle);

			// Now that our angle is in radians, we can find the x and y component of the direction
			// vector.
			// Make a new variable x and assign (float)Math.cos(angle) to it
			float directionX = (float)Math.cos(angle);
			// Make a new variable y and assign (float)Math.sin(angle) to it
			float directionY =(float)Math.sin(angle);

			// Make a new 2D Vector object called 'direction' and construct it with the x, y values
			// we just calculated
			Vec2 direction = new Vec2(directionX, directionY);

			// Create a new Particle object with the speed, direction and lifetime as its parameters
			GameObject particle = new Particle(speed, direction, lifeTime);

			// Set its position within the circle by calling getPositionInCircle()
			particle.setPosition(getPositionInCircle());

			// Add the particle object to the object manager
			ObjectManager.addGameObject(particle);
		}

	}

	/***************************************************************************
	 * This function finds a random position within the radius of the particle
	 * emitter for a particle to spawn at.
	 * @param //none
	 * @return A position (x, y) within the circle for the particle to start at
	 **************************************************************************/
	public Vec2 getPositionInCircle()
	{
		// We need to find a position in the circle given an angle... Remember
		// your trig!!
		// Find a random angle from 0 to 360 degrees (HINT: Remember the PFRandom class!)
		float randAngle = PFRandom.randomRange(0, 360);

		// Convert angle to radians (HINT: There's a handy function in the Math class to do this)
		// Also don't forget to cast the result back to a float!
		randAngle = (float)Math.toRadians(randAngle);

		// Use (float)Math.cos(angle) to find X, and (float)Math.sin(angle) to find Y
		float xPos = (float)Math.cos(randAngle);
		float yPos = (float)Math.sin(randAngle);

		// Get a random value between 0 and the radius value
		// Assign this value to a new float called 'distance'
		// This is so the particle spawns at a random position within the
		// particle emitter
		float distance = PFRandom.randomRange(0, radius);

		// Return a new Vec2 with the values for X and Y we found above}
		// Don't forget to account for distance!
		// HINT: Remember when we had a direction vector and we had a speed?
		// What did we do to our movement code with those two variables?
		// (Add, subtract, multiply, divide?)
		return new Vec2(xPos * distance, yPos * distance);
	}
}
