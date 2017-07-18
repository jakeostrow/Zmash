package edu.digipen.coleshelly;

/**
 * Created by jake.ostrow on 7/17/2017.
 */
public class Car extends Movement
{
	public Car()
	{
		super("Car", 24 * 2, 48 * 2, "car.png");
	}

	@Override
	public void update(float dt)
	{
		// move car through movement class
		checkInput(dt);

		// camera follow
//		this.setPosition(Graphics.getCameraPosition());
	}

}
