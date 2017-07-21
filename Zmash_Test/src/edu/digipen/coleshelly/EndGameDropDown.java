package edu.digipen.coleshelly;

import edu.digipen.InputManager;
import edu.digipen.gameobject.GameObject;
import edu.digipen.graphics.Graphics;
import edu.digipen.level.GameLevelManager;
import edu.digipen.math.Vec2;

import java.awt.event.KeyEvent;

/**
 * Created by cole.shelly on 7/19/2017.
 */
public class EndGameDropDown extends GameObject
{
	// Is the dropdown coming down
	boolean dropDown = false;

	public Vec2 position = new Vec2(0.0f, Graphics.getWindowHeight() / 2 + this.getHeight());

	public EndGameDropDown(boolean victory)
	{
		super("EndGameDropdownVictory" + victory, 946 / 3, 1222 / 3, "endGameDropdown" + victory + ".png");

		// Put it in front
		this.setZOrder(10);

		// Position
		this.setPosition(position);

	}

	public void bringDown()
	{
		dropDown = true;
	}

	@Override public void update(float v)
	{
		if (dropDown)
		{
			position.setY((0 + position.getY()) * 0.90f);
		}

		// If it has descended completely, pause game
		if (this.getPositionY() < 0 + Graphics.getCameraPosition().getY())
		{

		}

		Vec2 placement = new Vec2();
		placement.setX(position.getX() + Graphics.getCameraPosition().getX());
		placement.setY(position.getY() + Graphics.getCameraPosition().getY());
		this.setPosition(placement);

		// If space key is pressed
		if (InputManager.isPressed(KeyEvent.VK_SPACE))
		{
			// Reset level
			GameLevelManager.restartLevel();

		}

	}
}
