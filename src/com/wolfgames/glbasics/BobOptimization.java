package com.wolfgames.glbasics;

import javax.microedition.khronos.opengles.GL10;

import com.wolfgames.framework.FPSCounter;
import com.wolfgames.framework.Game;
import com.wolfgames.framework.Screen;
import com.wolfgames.framework.Texture;
import com.wolfgames.framework.impl.GLGame;
import com.wolfgames.framework.impl.GLGraphics;
import com.wolfgames.framework.impl.Vertices;

public class BobOptimization extends GLGame{
	@Override
	public Screen getStartScreen() {
		// TODO Auto-generated method stub
		return new BobScreen(this);
	}
	
	class BobScreen extends Screen{
		static final int NUB_BOBS = 500;
		GLGraphics glGraphics;
		Texture bobTexture;
		Vertices bobModel;
		Bob[] bobs;
		FPSCounter fpsCounter;
		
		
		public BobScreen(Game game) {
			// TODO Auto-generated constructor stub
			super(game);
			glGraphics = ((GLGame)game).getGLGraphics();
			bobTexture = new Texture((GLGame)game, "minion-mini.png");
			
			bobModel = new Vertices(glGraphics, 4, 12, false, true);
			bobModel.setVertices(new float[] {
					-16, -16, 0, 1,
					16, -16, 1, 1,
					16, 16, 1, 0,
					-16, 16, 0, 0
			}, 0, 16);
			bobModel.setIndices(new short[] {0, 1, 2, 2, 3, 0}, 0, 6);
			
			bobs = new Bob[NUB_BOBS];
			for (int i = 0; i < NUB_BOBS; i++) {
				bobs[i] = new Bob();
			}
			
			fpsCounter = new FPSCounter();
		}
		
		@Override
		public void update(float deltaTime) {
			// TODO Auto-generated method stub
			game.getInput().getTouchEvents();
			game.getInput().getKeyEvents();
			
			for (int i = 0; i < NUB_BOBS; i++) {
				bobs[i].update(deltaTime);
			}
		}
		
		@Override
		public void present(float deltaTime) {
			// TODO Auto-generated method stub
			GL10 gl = glGraphics.getGL();
			
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			bobModel.bind();
			for (int i = 0; i < NUB_BOBS; i++) {
				gl.glLoadIdentity();
				gl.glTranslatef(bobs[i].x, bobs[i].y, 0);
				bobModel.draw(GL10.GL_TRIANGLES, 0, 6);
			}
			bobModel.unbind();
			fpsCounter.logFrame();
			
		}

		@Override
		public void pause() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void resume() {
			// TODO Auto-generated method stub
			GL10 gl = glGraphics.getGL();
			gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
			gl.glClearColor(1, 1, 1, 1);
			
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, 600, 0, 1024, 1, -1);
			
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			
			gl.glEnable(GL10.GL_TEXTURE_2D);
			bobTexture.bind();
			gl.glMatrixMode(GL10.GL_MODELVIEW);
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}
	}

}
