package edu.digipen.coleshelly;

import edu.digipen.InputManager;
import edu.digipen.SoundManager;
import edu.digipen.gameobject.GameObject;
import edu.digipen.math.Vec2;

import java.awt.event.KeyEvent;

/**
 * Created by Chad on 6/23/2015.
 */
public class Movement extends GameObject
{

	// Direction in which ship is facing (starts out facing down positive x axis)
	private Vec2 Acceleration;
	private Vec2 Velocity;
	private float Speed = 200.0f;

	public float turnSpeed = 0;
	public float maxTurnSpeed = 1.5f;
	public float turnTime = 0;

	boolean engineIsPlaying = false;

	public Movement(String name_, int width_, int height_, String textureName_)
	{
		super(name_, width_, height_, textureName_);
		Acceleration = new Vec2(1, 0);
		Velocity = new Vec2(0, 0);
		Acceleration.scale(Speed);
	}

	public Vec2 getAcceleration()
	{
		return Acceleration;
	}

	public Vec2 getMovementVelocity()
	{
		return Velocity;
	}

	public float getSpeed()
	{
		return Speed;
	}

	public void setSpeed(float newSpeed)
	{
		Speed = newSpeed;
	}

	public void checkInput(float dt)
	{
		boolean moving = false;

		// Update dir at start
		updateDir(0.0f);

		boolean forward = InputManager.isPressed(KeyEvent.VK_UP);
		boolean backward = InputManager.isPressed(KeyEvent.VK_DOWN);
		boolean left = InputManager.isPressed(KeyEvent.VK_LEFT);
		boolean right = InputManager.isPressed(KeyEvent.VK_RIGHT);

		// Are we supposed to move
		if (forward || backward)
		{
			// Play sound
			if (engineIsPlaying == false)
			{
				SoundManager.playBackgroundSound("SportsCar1Steady");

				engineIsPlaying = true;
			}

			// Go Forward
			if (forward)
			{
				Velocity.setX(Acceleration.getX() * dt + Velocity.getX());
				Velocity.setY(Acceleration.getY() * dt + Velocity.getY());

				Velocity.scale(0.99f);
			}

			// Go Backward
			if (backward)
			{
				//				Velocity.setX(-Acceleration.getX() * dt + Velocity.getX());
				//				Velocity.setY(-Acceleration.getY() * dt + Velocity.getY());
				//
				//				Velocity.scale(0.99f);
			}

			// Rotate Left
			if (left || right)
			{

				if (left)
				{
					if (turnSpeed < maxTurnSpeed)
					{
						turnSpeed += 0.1f;

					}
				}
			}
			if (right)
			{
				if (turnSpeed > -maxTurnSpeed)
				{
					turnSpeed -= 0.1f;
				}
			}

			// Turn time
			turnTime += dt;
		}

		// Apply friction
		else
		{
			Velocity.setX(Velocity.getX() * 0.96f);
			Velocity.setY(Velocity.getY() * 0.96f);

			// Stop sound
			SoundManager.stopBackgroundSound("SportsCar1Steady");

			engineIsPlaying = false;


		}

		// Decelerate car's rotation
		if (turnSpeed != 0)
		{
			turnSpeed *= 0.96f;
		}

		// Turn based on speed
		updateDir(turnSpeed);

		setPositionX(getPositionX() + Velocity.getX() * dt);
		setPositionY(getPositionY() + Velocity.getY() * dt);

		// Reset turn time if left or right keys are released
		if(InputManager.isReleased(KeyEvent.VK_LEFT)||InputManager.isReleased(KeyEvent.VK_RIGHT))

		{
			// Reset turn time
			turnTime = 0;
		}
	}

	private void updateDir(float angleToChangBy)
	{
		// We want to get Rotation so we can update it
		float rot = getRotation();
		rot += angleToChangBy;
		setRotation(rot);

		// Update the direction of ship
		// Need to change to Radians for cos and sin
		// + 90, because the image is rotated 90 degrees from expected angle. it is an offset.
		rot = (float)Math.toRadians(rot);

		// Getting the unit length vector using angle (a vector of length one in the dir in which ship is facing)
		float x = (float)Math.cos(rot);
		float y = (float)Math.sin(rot);

		Acceleration.set(x, y);
		Acceleration.scale(Speed);
	}
}