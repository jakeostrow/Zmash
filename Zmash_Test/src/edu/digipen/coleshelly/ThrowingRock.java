package edu.digipen.coleshelly;

import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.math.Vec2;

/**
 * Created by jake.ostrow on 7/21/2017.
 */
public class ThrowingRock extends GameObject
{

	public ThrowingRock(Vec2 originPos)
	{
		super("ThrowingRock", 619 / 40, 669 / 40, "smokeParticle.png");

		// set position
		this.setPosition(originPos);
	}

	@Override public void update(float dt)
	{
		// get car
		GameObject car = ObjectManager.getGameObjectByName("Car");

		// vector from zombie to car
		Vec2 vector = Tools.VectorBetweenPoints(this.getPosition(), car.getPosition());

		vector.normalize();
		vector.scale(5);

		// move to car
		this.setPositionX(this.getPositionX() + vector.getX());
		this.setPositionY(this.getPositionY() + vector.getY());
	}
}
