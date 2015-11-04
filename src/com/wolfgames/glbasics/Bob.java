package com.wolfgames.glbasics;

import java.util.Random;

public class Bob {
	static final Random rand = new Random();
	public float x, y;
	float dirX, dirY;
	
	public Bob() {
		x = rand.nextFloat() * 600;
		y = rand.nextFloat() * 1024;
		
		dirX = 50;
		dirY = 50;
	}
	
	public void update(float deltaTime) {
		x = x + dirX * deltaTime;
		y = y + dirY * deltaTime;
		
		if(x < 0) { 
			dirX = -dirX;
			x = 0;
		}
		
		if(x > 600) {
			dirX = -dirX;
			x = 600;
		}
		
		if(y < 0) {
			dirY = -dirY;
			y = 0;
		}
		
		if (y > 1024) {
			dirY = -dirY;
			y = 1024;
		}
	}

}
