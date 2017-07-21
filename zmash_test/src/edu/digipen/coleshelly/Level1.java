package edu.digipen.coleshelly;

import edu.digipen.InputManager;
import edu.digipen.SoundManager;
import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.level.GameLevel;
import edu.digipen.level.GameLevelManager;
import edu.digipen.math.PFRandom;
import edu.digipen.math.Vec2;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by jake.ostrow on 7/17/2017.
 */
public class Level1 extends GameLevel
{

	// Game start timer
	float gameStartTimer = 1;

	// game over?
	boolean gameOver = false;

	// is paused
	boolean isPaused = false;

	// time till pause
	float timeTillPause = 1;

	@Override public void create()
	{

		////////////////////////////// SOUNDS ///////////////////////////////

		// Add water splash
		SoundManager.addSoundEffect("WaterSplash", "WaterSplash.wav");

		// Add seagulls
		SoundManager.addBackgroundSound("Seagulls", "Seagulls.wav", true);

		// Add car sounds
		SoundManager
				.addBackgroundSound("SportsCar1Steady", "SportsCar1Steady.wav",
						true);

		// Add bob landing sounds
		SoundManager.addBackgroundSound("Dirt3", "Dirt3.wav", true);

		// Gameplay music
		SoundManager.addBackgroundSound("Intro", "Intro.wav", true);

		// Car explosion
		SoundManager.addSoundEffect("Explosion7", "Explosion7.wav");

		// Wind
		SoundManager.addBackgroundSound("Wind2", "Wind2.wav", true);

		// Blood Sound
		SoundManager.addSoundEffect("Gravel3", "Gravel3.wav");

	}

	@Override public void initialize()
	{
		//////////////////////////// LEVEL ////////////////////////////

		// Add the background
		GameObject background = new GameObject("Background", 3000, 1500,
				"landTile.png");
		background.setZOrder(-2);
		ObjectManager.addGameObject(background);

		// Add the ocean
		GameObject ocean = new GameObject("Ocean", 12000, 6000, "oceanTile.png");
		ocean.setZOrder(-3);
		ObjectManager.addGameObject(ocean);

		// Add car to level
		GameObject car = new Car();
		ObjectManager.addGameObject(car);

		// Add test zombie to level
		for (int i = 0; i < 20; i++)
		{
			// Add a zombie
			addZombie();

			// Add rock
			// Scale
			int scale = PFRandom.randomRange(50, 80);
			GameObject rock = new CircleObstacle(scale, scale, "rock.png", true);
			rock.setPosition(PFRandom.randomRange(-1000, 1000), PFRandom.randomRange(-500, 500));
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

	@Override public void update(float dt)
	{
		// If space key is pressed
		if (InputManager.isPressed(KeyEvent.VK_ESCAPE))
		{
			// swich to home screen
			GameLevelManager.goToLevel(new HomeScreen());
		}


		// decrement timer
		if (gameStartTimer < 0)
		{
			if (gameOver == false)
			{
				// check for zombies
				ArrayList<GameObject> zombie = ObjectManager
						.getGameObjectsByName("Zombie");

				// number of live zombie
				float numLiveZombies = 0;

				for (GameObject z : zombie)
				{
					if (!((Zombie) z).isZombieDead)
						// increment num of zombies
						numLiveZombies += 1;
				}

				System.out.println("game over");

				if (numLiveZombies == 0)
				{
					EndGameDropDown victoryDropdown = new EndGameDropDown(true);
					victoryDropdown.bringDown();
					ObjectManager.addGameObject(victoryDropdown);

					// game over = true
					gameOver = true;
				}

				////////////////////////////// FAILURE //////////////////////////////////
				// check for car
				GameObject car = ObjectManager.getGameObjectByName("Car");

				if (((Car) car).gameOver)
				{
					// failure
					EndGameDropDown victoryDropdown = new EndGameDropDown(false);
					victoryDropdown.bringDown();
					ObjectManager.addGameObject(victoryDropdown);

					// game over = true
					gameOver = true;
				}
			}
		}

		if (gameOver)
		{
			timeTillPause -= dt;
		}

		if (timeTillPause < 0)
		{
			// pause
			isPaused = true;
		}

		// pause
		if (isPaused)
		{
			// pause game objects
			ObjectManager.pauseAllObjects();

			// If space key is pressed
			if (InputManager.isPressed(KeyEvent.VK_SPACE))
			{
				ObjectManager.unpauseAllObjects();

				isPaused = false;

				gameOver = false;

				timeTillPause = 1;

				// Reset level
				GameLevelManager.restartLevel();

			}
		}
		else
		{
			// un-pause
			ObjectManager.unpauseAllObjects();
		}

		// game start timer
		gameStartTimer -= dt;
	}


	@Override public void uninitialize()
	{
		// Remove unused things
		ObjectManager.removeAllObjects();
	}

	public void addZombie()
	{
		// Generate random position
		Vec2 position = new Vec2(PFRandom.randomRange(-800f, -400f), PFRandom.randomRange(-400f, 400f));

		// Add zombie
		GameObject zombie = new Zombie("Zombie", PFRandom.randomRange(1.0f, 2.0f));
		zombie.setPosition(position);
		ObjectManager.addGameObject(zombie);

		System.out.println("Zombie." + position);
	}

}

