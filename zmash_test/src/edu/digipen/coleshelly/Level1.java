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
		for (int i = 0; i < 1000; ++i)
		{
			// Add car to level
			GameObject car = new Car();
			ObjectManager.addGameObject(car);

			// Add test zombie to level
			GameObject zombie = new Zombie();
			ObjectManager.addGameObject(zombie);
		}
	}
}
