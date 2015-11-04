package com.wolfgames.glbasics;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import com.wolfgames.framework.Game;
import com.wolfgames.framework.Screen;
import com.wolfgames.framework.impl.GLGame;
import com.wolfgames.framework.impl.GLGraphics;

public class GLGameTest extends GLGame{
	@Override
	public Screen getStartScreen() {
		// TODO Auto-generated method stub
		return new TestScreen(this);
	}
	
	class TestScreen extends Screen {
		GLGraphics glGraphics;
		Random rand = new Random();
		
		public TestScreen(Game game) {
			// TODO Auto-generated constructor stub
			super(game);
			glGraphics = ((GLGame)game).getGLGraphics();
			
		}
		
		@Override
		public void present(float deltaTime) {
			// TODO Auto-generated method stub
			GL10 gl = glGraphics.getGL();
			gl.glClearColor(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		}
		
		@Override
		public void update(float deltaTime) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void pause() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void resume() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}
	}

}
