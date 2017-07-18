package edu.digipen.coleshelly;

import edu.digipen.gameobject.GameObject;

/**
 * Created by jake.ostrow on 7/18/2017.
 */
public class CarTrail extends GameObject
{
	// Life timer
	float lifeTimer = 0;

	public CarTrail(float x, float y, float lifetime)
	{
		// Super
		super("carTrail", 7, 7, "carTrailParticleGray.png");

		// set position
		this.setPosition(x, y);
		// set zorder
		this.setZOrder(-1);

		// set life timer
		lifeTimer = lifetime;

	}

	@Override public void update(float dt)
	{
		// update life timer
		lifeTimer -= dt;

		// check that timer ran out
		if (lifeTimer < 0)
		{
			// fade out
			this.setOpacity(this.getOpacity() - 0.01f);

			// if the particle has faded out entirely
			if (this.getOpacity() <= 0)
			{
				// kill the particle
				this.kill();
			}
		}
	}

}
