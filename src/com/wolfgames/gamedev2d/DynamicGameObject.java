package com.wolfgames.gamedev2d;

import com.wolfgames.framework.math.Vector2;

public class DynamicGameObject extends GameObject{
	public final Vector2 velocity;
	public final Vector2 accel;
	
	public DynamicGameObject(float x, float y, float width, float height) {
		// TODO Auto-generated constructor stub
		super(x, y, width, height);
		velocity = new Vector2();
		accel = new Vector2();
	}

}
