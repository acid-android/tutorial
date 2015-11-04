package com.wolfgames.framework.impl;

import com.wolfgames.framework.Audio;
import com.wolfgames.framework.FileIO;
import com.wolfgames.framework.Game;
import com.wolfgames.framework.Graphics;
import com.wolfgames.framework.Input;
import com.wolfgames.framework.Screen;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

public abstract class AndroidGame extends Activity implements Game {
	AndroidFastRenderView renderView;
	Graphics graphics;
	Audio audio;
	Input input;
	FileIO fileIO;
	Screen screen;
	WakeLock wakeLock;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
		int frameBufferWidth = isLandscape ? 480 : 320;
		int frameBufferHeight = isLandscape ? 320 : 480;
		
		Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);
		@SuppressWarnings("deprecation")
		float scaleX = (float) frameBufferWidth / getWindowManager().getDefaultDisplay().getWidth();
		@SuppressWarnings("deprecation")
		float scaleY = (float) frameBufferHeight / getWindowManager().getDefaultDisplay().getHeight();
		
		renderView = new AndroidFastRenderView(this, frameBuffer);
		graphics = new AndroidGraphics(getAssets(), frameBuffer);
		fileIO = new AndroidFileIO(getAssets());
		audio = new AndroidAudio(this);
		input = new AndroidInput(this, renderView, scaleX, scaleY);
		screen = getStartScreen();
		setContentView(renderView);
		
		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		wakeLock.acquire();
		screen.resume();
		renderView.resume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		wakeLock.release();
		renderView.pause();
		screen.pause();
		
		if (isFinishing())
			screen.dispose();
	}
	
	@Override
	public Input getInput() {
		// TODO Auto-generated method stub
		return input;
	}
	
	@Override
	public FileIO getFileIO() {
		// TODO Auto-generated method stub
		return fileIO;
	}
	
	@Override
	public Graphics getGraphics() {
		// TODO Auto-generated method stub
		return graphics;
	}
	
	@Override
	public Audio getAudio() {
		// TODO Auto-generated method stub
		return audio;
	}
	
	@Override
	public void setScreen(Screen screen) {
		// TODO Auto-generated method stub
		if (screen == null) 
			throw new IllegalArgumentException("Screen must not be null");
		
		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update(0);
		this.screen = screen;
	}
	
	public Screen getCurrentScreen() {
		return screen;
	}
	

}
