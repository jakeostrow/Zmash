package edu.digipen.coleshelly;

import edu.digipen.SoundManager;
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

		// Add the ocean
		GameObject ocean = new GameObject("Ocean", 12000, 6000, "oceanTile.png");
		ocean.setZOrder(-3);
		ObjectManager.addGameObject(ocean);

		////////////////////////////// SOUNDS ///////////////////////////////

		// Add water splash
		SoundManager.addSoundEffect("WaterSplash", "WaterSplash.wav");

		// Add seagulls
		SoundManager.addBackgroundSound("Seagulls", "Seagulls.wav", true);

		// Add car sounds
		SoundManager.addBackgroundSound("SportsCar1Steady", "SportsCar1Steady.wav", true);

		// Add bob landing sounds
		SoundManager.addBackgroundSound("Dirt3", "Dirt3.wav", true);

		// Gameplay music
		SoundManager.addBackgroundSound("Intro", "Intro.wav", true);

		// Car explosion
		SoundManager.addSoundEffect("Explosion7", "Explosion7.wav");

		// Wind
		SoundManager.addBackgroundSound("Wind2", "Wind2.wav", true);

		// Skid car sound
		SoundManager.addBackgroundSound("Skid2", "Skid2.wav", true);

		// Blood Sound
		SoundManager.addSoundEffect("Gravel3", "Gravel3.wav");

	}

	@Override public void initialize()
	{
		// Add car to level
		GameObject car = new Car();
		ObjectManager.addGameObject(car);


		// Add test zombie to level
		for (int i = 0; i < 20; i++)
		{
			// Add a zombie
			addZombie();

			// Add rock
			// scale
			int scale = PFRandom.randomRange(30, 60);
			GameObject rock = new CircleObstacle(scale, scale, "rock.png", true);
			rock.setPosition(PFRandom.randomRange(-200, 200),PFRandom.randomRange(-200, 200));
			ObjectManager.addGameObject(rock);


			/////////MORE SOUND/////////

			// Play intro sound
			SoundManager.playBackgroundSound("Intro");

			// Play seagull sound
			SoundManager.playBackgroundSound("Seagulls");

			// Play wind sound
			SoundManager.playBackgroundSound("Wind2");


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
		// Generate random position
		Vec2 position = new Vec2(PFRandom.randomRange(-800f, -400f), PFRandom.randomRange(0f, 0f));

		// Add zombie
		GameObject zombie = new Zombie("Zombie", PFRandom.randomRange(1.5f, 2.5f));
		zombie.setPosition(position);
		ObjectManager.addGameObject(zombie);

		System.out.println("Zombie." + position);
	}

}

