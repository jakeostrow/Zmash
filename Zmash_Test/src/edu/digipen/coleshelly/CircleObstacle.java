package edu.digipen.coleshelly;

import edu.digipen.gameobject.GameObject;
import edu.digipen.gameobject.ObjectManager;
import edu.digipen.math.Vec2;

/**
 * Created by jake.ostrow on 7/17/2017.
 */
public class CircleObstacle extends GameObject
{
	// Will running into this obstacle cause the car to take damage
	boolean destructible = true;

	public CircleObstacle(int width_, int height_,
			String textureName_, boolean destructible_)
	{
		super("CircleObstacle", width_, height_, textureName_);

		// Set "destructible"
		destructible = destructible_;
	}

	@Override public void update(float dt)
	{
		// Get car
		GameObject car = ObjectManager.getGameObjectByName("Car");

		// this object's position
		Vec2 circlePos = this.getPosition();
		// this object's radius
		float circleRadius = this.getHeight() / 2;
		// car's position
		Vec2 carPos = car.getPosition();
		// car's size
		Vec2 carSize = new Vec2(car.getPositionX(), car.getPositionY());

		// check for car - obstacle collision
		if (checkCircleRectangleCollision(circlePos, circleRadius, carPos, carSize.getX() / 4, carSize.getX() / 4))
		{
			if (destructible)
			{
				// shake screen
				((Car)car).shakeScreen(0.02f);

<<<<<<< HEAD
				// destroy obstacle upon collision
				this.kill();

=======
>>>>>>> 2cb03ddc572290e953ead8a7c14acf950894be3c
			}
			else
			{

			}
		}
	}


	/***************************************************************************
	 * This function determines whether or or not a point and a circle are
	 * colliding.
	 * @param pPos		- The position (x,y coordinate) of the point
	 * @param cPos		- The position (x,y coordinate) of the circle
	 * @param cRadius	- The radius of the circle
	 * @return TRUE if the two objects are colliding; FALSE if they are NOT
	 * 		   colliding
	 **************************************************************************/
	boolean checkPointCircleCollision(Vec2 pPos, Vec2 cPos, float cRadius)
	{
		// Calculate the squared radius and store it into a new variable.
		float squaredRadius = cRadius * cRadius;

		// Subtract the x positions of the point and circle.
		float xDistance = cPos.getX() - pPos.getX();

		// Subtract the y positions of the point and circle.
		float yDistance = cPos.getY() - pPos.getY();

		// Calculate the squared distance.
		float squaredDistance = (xDistance * xDistance) + (yDistance * yDistance);

		// If the distance is less than the radius squared, we are colliding
		if (squaredDistance < squaredRadius)
		{
			// Return true
			return true;
		}
		// If we get here, then we are not colliding
		// Return false
		return false;
	}

	/***************************************************************************
	 * This function determines whether or or not a circle and a rectangle are
	 * colliding. This function utilizes the circle-point collision detection
	 * method to help find this.
	 * @param cPos			- The position (x,y coordinate) of the circle
	 * @param cRadius		- The radius of the circle
	 * @param rPos			- The position (x,y coordinate) of the rectangle
	 * @param rHalfWidth	- The half width of the rectangle
	 * @param rHalfHeight	- The half height of the rectangle
	 * @return TRUE if the two objects are colliding; FALSE if they are NOT
	 * 		   colliding
	 **************************************************************************/
	boolean checkCircleRectangleCollision(Vec2 cPos, float cRadius, Vec2 rPos, float
			rHalfWidth, float rHalfHeight)
	{
		// Make a point at the center of the circle. (NOTE: You will have to use
		// the copy constructor! Vec2 newPoint = new Vec2(cPos);
		Vec2 circlePoint = new Vec2(cPos);

		// If the point is off the rectangle to the left
		if (circlePoint.getX() < rPos.getX() - rHalfWidth)
		{
			// Reset its position x to the left side of the rectangle
			circlePoint.setX(rPos.getX() - rHalfWidth);
		}

		// Otherwise, if the point if off the rectangle to the right
		else  if (circlePoint.getX() > rPos.getX() + rHalfWidth)
		{
			// Reset its position x to the right side of the rectangle
			circlePoint.setX(rPos.getX() + rHalfWidth);
		}

		// if the point is above the rectangle
		if (circlePoint.getY() > rPos.getY() + rHalfHeight)
		{
			// Reset its position y to the top of the rectangle
			circlePoint.setY(rPos.getY() + rHalfHeight);
		}

		// Otherwise, if the point is below the rectangle
		else if (circlePoint.getY() < rPos.getY() - rHalfHeight)
		{
			// Reset its position y to the bottom of the rectangle
			circlePoint.setY(rPos.getY() - rHalfHeight);
		}

		// Return the result from the checkPointCircleCollision method.
		return checkPointCircleCollision(circlePoint, cPos, cRadius);
	}
}
