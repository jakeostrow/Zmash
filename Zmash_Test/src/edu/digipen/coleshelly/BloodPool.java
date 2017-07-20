package edu.digipen.coleshelly;

/**
 * Created by jake.ostrow on 7/20/2017.
 */

import edu.digipen.gameobject.GameObject;
import edu.digipen.math.Vec2;

/**
 * Created by jake.ostrow on 7/19/2017.
 */
public class BloodPool extends GameObject
{
	public BloodPool(Vec2 position)
	{
		super("bloodPool", 10, 5, "bloodPool.png");

		// position
		this.setPosition(position);
		// zOrder
		this.setZOrder(-1);

	}

	@Override public void initialize()
	{

	}

	@Override public void update(float dt)
	{
		// Fade out
		// Scale up
		this.setScale((this.getScale().getX() + 0.1f) * 0.99f, (this.getScale().getY() + 0.1f) * 0.99f);


	}

}
