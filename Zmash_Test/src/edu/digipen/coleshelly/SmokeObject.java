package edu.digipen.coleshelly;

import edu.digipen.gameobject.GameObject;
import edu.digipen.math.PFRandom;
import edu.digipen.math.Vec2;

/**
 * Created by jake.ostrow on 7/21/2017.
 */
public class SmokeObject extends GameObject
{

	// Life timer
	float lifeTimer = 0;

	public SmokeObject(Vec2 position)
	{
		super("SmokeParticle", 619 / 40, 669 / 40, "smokeParticle.png");

		// zOrder
		this.setZOrder(3);

		// position
		this.setPosition(position);

		// random offset
		this.setPositionX(this.getPositionX() + PFRandom.randomRange(-10, 10));
		this.setPositionY(this.getPositionY() + PFRandom.randomRange(-10, 10));
	}

	@Override public void update(float dt)
	{
		// update life timer
		lifeTimer -= dt;

		// Scale up
		this.setScale(this.getScale().getX() + 0.0001f, this.getScale().getY() + 0.0001f);

		// move up
		this.setPositionY(this.getPositionY() + 1f);

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
