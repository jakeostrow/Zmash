package edu.digipen.coleshelly;

import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;

/**
 * Created by jake.ostrow on 7/18/2017.
 */
public class CarFacade extends GameObject
{
	public CarFacade()
	{
		// super duper
		super("CarFacade", 48 * 2, 48 * 2, "carSpritesheetSmall.png", 24, 1, 24, 1);

		// Set as animation
		this.animationData.numberOfColumns = 24;
		this.animationData.numberOfRows = 1;
	}

	@Override public void update(float dt)
	{
		// Fetch car
		GameObject car = ObjectManager.getGameObjectByName("Car");

		// set carFacade's location based on car location
		this.setPosition(car.getPosition());

		// update rotation
		updateRotation(car.getRotation());
	}

	public void updateRotation(float carRotation)
	{
		// angle of car divided by 7.5 (degrees between each frame) equals the frame that is needed
		// newFr4meNum
		int newFrameNum = Math.abs((int)((-carRotation) / 7.5));

		// go to frame and stop
		this.animationData.goToAndStop(newFrameNum);

		// print car rot
		System.out.println(newFrameNum);

	}
}
