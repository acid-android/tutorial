package com.wolfgames.glbasics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.wolfgames.framework.Game;
import com.wolfgames.framework.Screen;
import com.wolfgames.framework.impl.GLGame;
import com.wolfgames.framework.impl.GLGraphics;

public class ColoredTriangleTest extends GLGame {
	@Override
	public Screen getStartScreen() {
		// TODO Auto-generated method stub
		return new ColoredTriangleScreen(this);
	}

	class ColoredTriangleScreen extends Screen {
		final int VERTEX_SIZE = (2 + 4) * 4;
		GLGraphics glGraphics;
		FloatBuffer vertices;

		public ColoredTriangleScreen(Game game) {
			// TODO Auto-generated constructor stub
			super(game);
			glGraphics = ((GLGame) game).getGLGraphics();

			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * VERTEX_SIZE);
			byteBuffer.order(ByteOrder.nativeOrder());
			vertices = byteBuffer.asFloatBuffer();
			vertices.put(new float[] { 0.0f, 0.0f, 1, 0, 0, 1, 599.0f, 0.0f, 0, 1, 0, 1, 300.0f, 1023.0f, 0, 0, 1, 1 });
		}

		@Override
		public void present(float deltaTime) {
			// TODO Auto-generated method stub
			GL10 gl = glGraphics.getGL();
			gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, 600, 0, 1024, 1, -1);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

			vertices.position(0);
			gl.glVertexPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
			vertices.position(2);
			gl.glColorPointer(4, GL10.GL_FLOAT, VERTEX_SIZE, vertices);

			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
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
