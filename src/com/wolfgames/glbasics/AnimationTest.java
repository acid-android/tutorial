package com.wolfgames.glbasics;

import com.wolfgames.framework.Screen;
import com.wolfgames.framework.Texture;
import com.wolfgames.framework.gl.Animation;
import com.wolfgames.framework.gl.Camera2D;
import com.wolfgames.framework.gl.SpriteBatcher;
import com.wolfgames.framework.impl.GLGame;
import com.wolfgames.framework.impl.GLGraphics;
import com.wolfgames.gamedev2d.DynamicGameObject;

public class AnimationTest extends GLGame{
	@Override
	public Screen getStartScreen() {
		// TODO Auto-generated method stub
		return null;
	}
	
	static final float WORLD_WIDTH = 4.8f;
	static final float WORLD_HEIGHT = 3.2f;
	
	static class Caveman extends DynamicGameObject {
		public float walkingTime = 0;
		public Caveman(float x, float y, float width, float height) {
			// TODO Auto-generated constructor stub
			super(x, y, width, height);
			this.position.set((float)Math.random() * WORLD_WIDTH, (float)Math.random() * WORLD_HEIGHT);
			this.velocity.set(Math.random() > 0.5f? - 0.5f : 0.5f, 0);
			this.walkingTime = (float)Math.random() * 10;
		}
		
		public void update(float deltaTime) {
			position.add(velocity.x * deltaTime, velocity.y * deltaTime);
			if(position.x < 0) position.x = WORLD_WIDTH;
			if(position.x > WORLD_WIDTH) position.x = 0;
			walkingTime += deltaTime;
		}
	}
	
	class AnimationScreen extends Screen {
		static final int NUM_CAVENMEN = 10;
		GLGraphics glGraphics;
		Caveman[] caveman;
		SpriteBatcher batcher;
		Camera2D camera;
		Texture texture;
		Animation walkAnim;
		
	}

}
