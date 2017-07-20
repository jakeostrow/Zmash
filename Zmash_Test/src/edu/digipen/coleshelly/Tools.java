package edu.digipen.coleshelly;

import edu.digipen.InputManager;
import edu.digipen.graphics.Graphics;
import edu.digipen.math.Vec2;

public class Tools
{
  static Vec2 GetVectorFromAngle(float angle, float vectorLength)
  {
    angle = (float)Math.toRadians(angle);
    return new Vec2((float)Math.cos(angle) * vectorLength, (float)Math.sin(angle) * vectorLength); 
  }
	
  static float GetAngleFromVector(Vec2 vector)
  {
    Vec2 localVec = new Vec2(vector);
    localVec.normalize();
    float angleUsed;
    float angle = (float)Math.toDegrees(Math.acos(localVec.getX() / localVec.getLength()));
    float angle2 = (float)180 + (180 - angle);

    if(vector.getY() < 0)
    {
      angleUsed = angle2;
    }
    else
    {
      angleUsed = angle;
    }

    return angleUsed;
  }

  static Vec2 GetMousePositionInWorldSpace(float screenWidth, float screenHeight)
  {
    Vec2 screenSpace = new Vec2(InputManager.getMousePosition());
    screenSpace.setX(screenSpace.getX() - screenWidth/2 + Graphics
            .getCameraPosition().getX());
    screenSpace.setY(-screenSpace.getY() + screenHeight / 2 + Graphics.getCameraPosition().getY());
    return screenSpace;
  }

  static Vec2 VectorBetweenPoints(Vec2 point1, Vec2 point2)
  {
    return new Vec2(point2.getX() - point1.getX(), point2.getY() - point1.getY());
  }
  
  static float ClampF(float number, float min, float max)
  {
	  if(number < min)
	  {
		  number = min;
	  }
	  
	  if(number > max)
	  {
		  number = max;
	  }
	  
	  return number;
  }
}
