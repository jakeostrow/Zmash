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
		super("CarFacade", 48 * 2, 48 * 2, "carSpritesheetSmall.png", 22, 1, 22, 1);

		// zOrder
		this.setZOrder(1);

		// Set as animation
		this.animationData.numberOfColumns = 22;
		this.animationData.numberOfRows = 1;
		this.play();
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
		int newFrameNum = ((int)((-carRotation) / 8.18));

		// if frame num is less than 0, add 48
		if (newFrameNum < 0)
		{
			newFrameNum += 44;
		}

		// go to frame and stop
		this.animationData.goToAndStop(newFrameNum);

	}
}
