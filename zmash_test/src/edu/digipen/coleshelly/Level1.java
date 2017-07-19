package edu.digipen.coleshelly;

import edu.digipen.SoundManager;
import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.level.GameLevel;
import edu.digipen.math.PFRandom;
import edu.digipen.math.Vec2;
import edu.digipen.particlesystem.CircleEmitter;
import edu.digipen.particlesystem.ParticleSystem;

/**
 * Created by jake.ostrow on 7/17/2017.
 */
public class Level1 extends GameLevel
{

	@Override public void create()
	{
		//////////////////////////// TEST ////////////////////////////
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

		// Explosion particles
		// Emitter
		CircleEmitter emitter = new CircleEmitter(new Vec2(0,0), 1000, 0.2f, 0.0f, 200.0f, 0, 360);

		// Make a new particle system
		ParticleSystem ps = ParticleSystem.create("ParticleSystem.json", "ParticleProperties.json", emitter);
		ObjectManager.addGameObject(ps);

		//////////////////////////// LEVEL ////////////////////////////
		// Add car to level
		GameObject car = new Car();
		ObjectManager.addGameObject(car);

		// Add the background
		GameObject background = new GameObject("Background", 6000, 3000, "landTile.png");
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
		SoundManager.addSoundEffect("Seagulls", "Seagulls.wav");

		// Add car sounds
		SoundManager.addSoundEffect("SportsCar1Steady", "SportsCar1Steady.wav");

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

	public void addZombie()
	{
		// generate random position
		Vec2 position = new Vec2(PFRandom.randomRange(-200f, 200f), PFRandom.randomRange(-200f, 200f));

		// add zombie
		GameObject zombie = new Zombie("Zombie." + position, PFRandom.randomRange(0.5f, 1.5f));
		zombie.setPosition(position);
		ObjectManager.addGameObject(zombie);

		System.out.println("Zombie." + position);
	}

}

