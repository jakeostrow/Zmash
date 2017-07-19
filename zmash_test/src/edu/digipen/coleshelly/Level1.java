package edu.digipen.coleshelly;

import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.level.GameLevel;
import edu.digipen.math.PFRandom;
import edu.digipen.math.Vec2;

/**
 * Created by jake.ostrow on 7/17/2017.
 */
public class Level1 extends GameLevel
{

	@Override public void create()
	{
		//////////////////////////// LEVEL ////////////////////////////

		// Add the background
		GameObject background = new GameObject("Background", 3000, 1500, "landTile.png");
		background.setZOrder(-2);
		ObjectManager.addGameObject(background);

		// add the ocean
		GameObject ocean = new GameObject("Ocean", 12000, 6000, "oceanTile.png");
		ocean.setZOrder(-3);
		ObjectManager.addGameObject(ocean);


	}

	@Override public void initialize()
	{
		// Add car to level
		GameObject car = new Car();
		ObjectManager.addGameObject(car);

		// Add test zombie to level
		for (int i = 0; i < 20; i++)
		{
			// add a zombie
			addZombie();

			// Add rock
			GameObject rock = new CircleObstacle(30, 30, "rock.png", true);
			rock.setPosition(PFRandom.randomRange(-200, 200),PFRandom.randomRange(-200, 200));
			ObjectManager.addGameObject(rock);
		}
	}

	@Override public void update(float v)
	{

	}

	@Override public void uninitialize()
	{
		// Remove unused things
		ObjectManager.removeAllObjectsByName("Zombie");
		ObjectManager.removeAllObjectsByName("CircleObstacle");
		ObjectManager.removeAllObjectsByName("Car");
		ObjectManager.removeAllObjectsByName("CarFacade");
	}

	public void addZombie()
	{
		// generate random position
		Vec2 position = new Vec2(PFRandom.randomRange(-800f, -400f), PFRandom.randomRange(0f, 0f));

		// add zombie
		GameObject zombie = new Zombie("Zombie", PFRandom.randomRange(0.5f, 1.5f));
		zombie.setPosition(position);
		ObjectManager.addGameObject(zombie);

		System.out.println("Zombie." + position);
	}

}

