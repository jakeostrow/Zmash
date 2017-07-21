package edu.digipen.coleshelly;

import edu.digipen.gameobject.GameObject;

/**
 * Created by daniel.lelivelt on 7/17/2017.
 */
public class Timer extends GameObject
{
	public float timer;
	public float maxTime = 0;
	public boolean isReady = false;
	public Timer(float delay)
	{
		super("Timer", 0, 0, "");
		timer = delay;
		maxTime = delay;
	}
	@Override public void update(float dt)
	{
		timer-= dt;
		if(timer <= 0)
		{
			isReady = true;
		}

	}
	void reset()
	{
		timer = maxTime;
		isReady = false;
	}
}
