package edu.digipen.coleshelly;

import edu.digipen.gameobject.GameObject;
import edu.digipen.math.Vec2;

/**
 * Created by r.frazier on 6/30/2017.
 */
public class Particle extends GameObject
{
	// This is the speed of the particle.
	private float speed = 0.3f;

	// This is the direction the particle is traveling
	private Vec2 direction = new Vec2();

	// This is how long the particle stays alive before it gets deleted
	private float lifeTimer = 0.0f;

	/***************************************************************************
	 * Constructor - Constructs a new Particle object
	 * @param 	speed_		- The speed at which the particle travels
	 * @param 	direction_	- The direction at which the particle travels
	 * @param 	lifeTimer_	- How long the particle should stay alive before it
	 *                        gets destroyed
	 **************************************************************************/
	public Particle(float speed_, Vec2 direction_, float lifeTimer_)
	{
		// Name: "Particle", Width: 20, Height: 20, Texture file: "particle.png"
		super("Particle", 20, 20, "carTrailParticle.png");

		// Assign the member variables with the variables passed in to the function
		// set speed
		speed = speed_;
		// set direction
		direction = direction_;
		// set lifeTimer
		lifeTimer = lifeTimer_;
	}

	@Override
	public void update(float dt)
	{
		// Make the particle move!
		// Set the x position using the relationship x_comp = x_comp + direction_x_comp * speed
		this.setPositionX(this.getPositionX() + direction.getX() * speed);
		// Set the y position using the relationship y_comp = y_comp _ direction_y_comp * speed
		this.setPositionY(this.getPositionY() + direction.getY() * speed);

		// Make sure to check if the particle has died
		if (!this.isDead())
		{






















			// Decrement lifeTimer by dt (subtract dt from lifeTimer)
			lifeTimer -= dt;
		}

		//
		// If the lifeTimer falls below 0
		if (lifeTimer <= 0)
		{
			// Kill the object
			this.kill();
		}


	}
}
