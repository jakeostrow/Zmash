package edu.digipen.coleshelly;

import edu.digipen.gameobject.GameObject;
import edu.digipen.graphics.Graphics;

/**
 * Created by cole.shelly on 7/21/2017.
 */
public class ArrowKeyPrompt extends GameObject
{

	public ArrowKeyPrompt()
	{
		super("ArrowKeyPrompt", 50, 50, "Key Prompt.png");

	}

	@Override public void update(float v)
	{
		this.setPositionY(this.getPositionY() + Graphics.getCameraPosition().getY());
		

	}

}
