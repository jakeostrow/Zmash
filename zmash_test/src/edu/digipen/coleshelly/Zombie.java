package edu.digipen.coleshelly;

import edu.digipen.SoundManager;
import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.math.Vec2;

/**
 * Created by cole.shelly on 7/17/2017.
 */

public class Zombie extends GameObject
{
	public Zombie()
	{
		// Call the base constructor
		super("Ball", 40, 40, "Ball.png");

		SoundManager.addSoundEffect("I Want Brains-SoundBible.com-814171004.mp3", ".wav");
	}

	@Override public void initialize()
	{
		SoundManager.playBackgroundSound("BG");
	}
	@Override
	public void update(float dt)
	{
		GameObject paddle = ObjectManager.getGameObjectByName("Car");

		// Compute the vector from the enemy to the paddle
		// THIS IS P - E
		Vec2 vector = new Vec2();
		vector.setX(paddle.getPositionX() - this.getPositionX());
		vector.setY(paddle.getPositionY() - this.getPositionY());
		// Get the unit vector!
		vector.normalize();

		// Use the computed vector to move the enemy towards the player
		this.setPositionX(this.getPositionX() + vector.getX());
		this.setPositionY(this.getPositionY() + vector.getY());
	}

}
