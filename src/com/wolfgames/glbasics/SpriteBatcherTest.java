package com.wolfgames.glbasics;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.wolfgames.framework.Game;
import com.wolfgames.framework.Input.TouchEvent;
import com.wolfgames.framework.Screen;
import com.wolfgames.framework.Texture;
import com.wolfgames.framework.gl.Camera2D;
import com.wolfgames.framework.gl.SpatialHashGrid;
import com.wolfgames.framework.gl.SpriteBatcher;
import com.wolfgames.framework.gl.TextureRegion;
import com.wolfgames.framework.impl.GLGame;
import com.wolfgames.framework.impl.GLGraphics;
import com.wolfgames.framework.math.OverlapTester;
import com.wolfgames.framework.math.Vector2;
import com.wolfgames.gamedev2d.Cannon;
import com.wolfgames.gamedev2d.DynamicGameObject;
import com.wolfgames.gamedev2d.GameObject;

public class SpriteBatcherTest extends GLGame{
	@Override
	public Screen getStartScreen() {
		// TODO Auto-generated method stub
		return new SpriteBatcherScreen(this);
	}
	
	class SpriteBatcherScreen extends Screen {
		Camera2D camera;
		final int NUM_TARGETS = 20;
		final float WORLD_WIDTH = 820.0f;
		final float WORLD_HEIGHT = 460.0f;
		GLGraphics glGraphics;
		GL10 gl;
		Cannon cannon;
		DynamicGameObject ball;
		List<GameObject> targets;
		SpatialHashGrid grid;
		Vector2 center;
		
		Texture texture;
		
		TextureRegion cannonRegion;
		TextureRegion ballRegion;
		TextureRegion bobRegion;
		SpriteBatcher batcher;
		
		Vector2 touchPos = new Vector2();
		Vector2 gravity = new Vector2(0, -600);
		
		public SpriteBatcherScreen(Game game) {
			super(game);
			glGraphics = ((GLGame)game).getGLGraphics();
			gl = glGraphics.getGL();
			
			camera = new Camera2D(glGraphics, WORLD_WIDTH, WORLD_HEIGHT);
			center = new Vector2(WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
			cannon = new Cannon(0, 0, 128, 64.0f);
			ball = new DynamicGameObject(0, 0, 16.0f, 16.0f);
			targets = new ArrayList<GameObject>(NUM_TARGETS);
			grid = new SpatialHashGrid(WORLD_WIDTH, WORLD_HEIGHT, 64);
			for(int i = 0; i < NUM_TARGETS; i++) {
				GameObject target = new GameObject((float)Math.random() * WORLD_WIDTH,
												   (float)Math.random() * WORLD_HEIGHT,
												   50.0f, 50.0f);
				grid.insertStaticObjects(target);
				targets.add(target);
			}
			
			batcher = new SpriteBatcher(glGraphics, 100);	
			
		}
		
		@Override
		public void update(float deltaTime) {
			List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
			game.getInput().getKeyEvents();
			
			int len = touchEvents.size();
			for(int i = 0; i < len; i++) {
				TouchEvent event = touchEvents.get(i);
				
				camera.touchToWorld(touchPos.set(event.x, event.y));
				
				cannon.angle = touchPos.sub(cannon.position).angle();
				
				if(event.type == TouchEvent.TOUCH_UP) {
					float radians = cannon.angle * Vector2.TO_RADIANS;
					float ballSpeed = touchPos.len() * 2;
					ball.position.set(cannon.position);
					
					ball.velocity.x = (float)Math.cos(radians) * ballSpeed;
					ball.velocity.y = (float)Math.sin(radians) * ballSpeed;
					
					ball.bounds.lowerLeft.set(ball.position.x - 8.0f, ball.position.y - 8.0f);
				}
			}
			

			
			ball.velocity.add(gravity.x * deltaTime, gravity.y * deltaTime);
			ball.position.add(ball.velocity.x * deltaTime, ball.velocity.y * deltaTime);
			ball.bounds.lowerLeft.add(ball.velocity.x * deltaTime, ball.velocity.y * deltaTime);
			
			List<GameObject> colliders = grid.getPotentialColliders(ball);
			len = colliders.size();
			
			for(int i = 0; i < len; i++) {
				GameObject collider = colliders.get(i);
				if(OverlapTester.overlapRectangles(ball.bounds, collider.bounds)) {
					grid.removeObject(collider);
					targets.remove(collider);
				}
			}
			
			if(ball.position.y > 0) {
				camera.position.set(center);
				camera.zoom = 1 + ball.position.y / WORLD_HEIGHT;
			}
			else {
				camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
				camera.zoom = 1;
			}
		}
		
		@Override
		public void present(float deltaTime) {
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			camera.setViewportAndMatrices();
			
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			
			                               // End Text Rendering        
			
			batcher.beginBatch(texture);
			
			int len = targets.size();
			for(int i = 0; i < len; i++) {
				GameObject target = targets.get(i);
				batcher.drawSprite(target.position.x, target.position.y, 50.0f, 50.0f, bobRegion);
			}

			batcher.drawSprite(ball.position.x, ball.position.y, 16.0f, 16.0f, ballRegion);
			batcher.drawSprite(cannon.position.x, cannon.position.y, 128, 64.0f, cannon.angle, cannonRegion);
			batcher.endBatch();
		}

		@Override
		public void pause() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void resume() {
			texture = new Texture(((GLGame)game), "atlas.png");
			cannonRegion = new TextureRegion(texture, 0, 0, 64, 32);
			ballRegion = new TextureRegion(texture, 0, 32, 16, 16);
			bobRegion = new TextureRegion(texture, 32, 32, 32, 32);
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}
	}

}
