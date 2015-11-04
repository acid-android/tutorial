package com.wolfgames.glbasics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.wolfgames.framework.Game;
import com.wolfgames.framework.Screen;
import com.wolfgames.framework.impl.GLGame;
import com.wolfgames.framework.impl.GLGraphics;

public class FirstTriangleTest extends GLGame{
	@Override
	public Screen getStartScreen() {
		return new FirstTriangleScreen(this);
	}
	
	class FirstTriangleScreen extends Screen {
		GLGraphics glGraphics;
		FloatBuffer vertices;
		
		public FirstTriangleScreen(Game game) {
			// TODO Auto-generated constructor stub
			super(game);
			glGraphics = ((GLGame)game).getGLGraphics();
			
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * 2 * 4);
			byteBuffer.order(ByteOrder.nativeOrder());
			vertices = byteBuffer.asFloatBuffer();
			vertices.put(new float[] { 0.0f, 0.0f,
									599.0f, 0.0f,
									300.0f, 1023.0f});
			vertices.flip();
		}
		@Override
		public void present(float deltaTime) {
			// TODO Auto-generated method stub
			GL10 gl10 = glGraphics.getGL();
			gl10.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
			gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl10.glMatrixMode(GL10.GL_PROJECTION);
			gl10.glLoadIdentity();
			gl10.glOrthof(0, 600, 0, 1024, 1, -1);
			gl10.glColor4f(1, 0, 0, 1);
			gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl10.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
			gl10.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		}
		
		@Override
		public void update(float deltaTime) {
			// TODO Auto-generated method stub
			game.getInput().getTouchEvents();
			game.getInput().getKeyEvents();
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
