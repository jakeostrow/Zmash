package edu.digipen.coleshelly;

import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.level.GameLevel;
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
		GameObject zombie = new Zombie();
		ObjectManager.addGameObject(zombie);

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
		GameObject background = new GameObject("Background", 3000, 1500, "backgroundDiagonalSquare.png");
		background.setZOrder(-1);
		ObjectManager.addGameObject(background);

		// Add rock
		GameObject rock = new CircleObstacle(30, 30, "rock.png", true);
		rock.setPosition(80, 80);
		ObjectManager.addGameObject(rock);

		// I'm making a change

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

