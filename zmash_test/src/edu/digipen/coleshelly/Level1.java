package edu.digipen.coleshelly;

import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.level.GameLevel;

/**
 * Created by jake.ostrow on 7/17/2017.
 */
public class Level1 extends GameLevel
{

	@Override public void create()
	{
		// Add car to level
		GameObject car = new Car();
		ObjectManager.addGameObject(car);

		GameObject zombie = new Zombie();
		ObjectManager.addGameObject(zombie);
	}

	@Override public void initialize()
	{

	}

	@Override public void update(float v)
	{

	}

	@Override public void uninitialize()
	{

	}
}
