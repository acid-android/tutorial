package com.wolfgames.gamedev2d;

import com.wolfgames.framework.math.Rectangle;
import com.wolfgames.framework.math.Vector2;

public class GameObject {
	public final Vector2 position;
	public final Rectangle bounds;
	
	public GameObject(float x, float y, float width, float height) {
		// TODO Auto-generated constructor stub
		this.position = new Vector2(x, y);
		this.bounds = new Rectangle(x - width/2, y - height/2, width, height);
	}

}
