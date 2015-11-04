package com.wolfgames.glbasics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.wolfgames.framework.Game;
import com.wolfgames.framework.Screen;
import com.wolfgames.framework.Texture;
import com.wolfgames.framework.impl.GLGame;
import com.wolfgames.framework.impl.GLGraphics;

public class IndexedTest extends GLGame{
	@Override
	public Screen getStartScreen() {
		// TODO Auto-generated method stub
		return new IndexedScreen(this);
	}
	class IndexedScreen extends Screen {
		final int VERTEX_SIZE = (2 + 4) * 4;
		GLGraphics glGraphics;
		FloatBuffer vertices;
		ShortBuffer indices;
		Texture texture;
		
		public IndexedScreen(Game game) {
			// TODO Auto-generated constructor stub
			super(game);
			glGraphics = ((GLGame)game).getGLGraphics();
			
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * VERTEX_SIZE);
			byteBuffer.order(ByteOrder.nativeOrder());
			vertices = byteBuffer.asFloatBuffer();
			vertices.put(new float[] {
					100.0f, 100.0f, 0.0f, 1.0f,
					228.0f, 100.0f, 1.0f, 1.0f,
					228,0f, 228.0f, 1.0f, 0.0f,
					100.0f, 228.0f, 0.0f, 0.0f
			});
			
			vertices.flip();
			
			byteBuffer = ByteBuffer.allocateDirect(6 * 2);
			byteBuffer.order(ByteOrder.nativeOrder());
			indices = byteBuffer.asShortBuffer();
			indices.put(new short[] {
					0, 1, 2,
					2, 3, 0
			});
			indices.flip();
			
			texture = new Texture((GLGame)game, "bobrgb888.png");
		}
		
		@Override
		public void present(float deltaTime) {
			// TODO Auto-generated method stub
			GL10 gl = glGraphics.getGL();
			gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, 320, 0, 480, 1, -1);
			
			gl.glEnable(GL10.GL_TEXTURE_2D);
			texture.bind();
			
			
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			
			vertices.position(0);
			gl.glVertexPointer(2,  GL10.GL_FLOAT, VERTEX_SIZE, vertices);
			vertices.position(2);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glTexCoordPointer(2,  GL10.GL_FLOAT, VERTEX_SIZE, vertices);
			gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, indices);
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
