package edu.digipen.coleshelly;

import edu.digipen.gameobject.GameObject;
import edu.digipen.math.Vec2;

/**
 * Created by jake.ostrow on 7/19/2017.
 */
public class Splash extends GameObject
{
	public Splash(Vec2 position)
	{
		super("Splash", 40, 20, "landTile.png");

		this.setPosition(position);

	}

	@Override public void initialize()
	{

	}

	@Override public void update(float dt)
	{
		// Fade out
		this.setOpacity(this.getOpacity() - 0.01f);
		// Scale up
		this.setScale(this.getScale().getX() + 0.1f, this.getScale().getY() + 0.1f);

		// Kill when it disappears
		if (this.getOpacity() < 0)
		{
			this.kill();
		}

	}

}
