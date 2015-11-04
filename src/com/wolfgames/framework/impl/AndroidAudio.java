package com.wolfgames.framework.impl;

import java.io.IOException;


import com.wolfgames.framework.Audio;
import com.wolfgames.framework.Music;
import com.wolfgames.framework.Sound;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

public class AndroidAudio implements Audio {
	AssetManager assets;
	SoundPool soundPool;
	
	public AndroidAudio(Activity activity) {
		// TODO Auto-generated constructor stub
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
	}
	
	@Override
	public Music newMusic(String fileName) {
		// TODO Auto-generated method stub
		try {
			AssetFileDescriptor assetFileDescriptor = assets.openFd(fileName);
			return new AndroidMusic(assetFileDescriptor);
		} catch (IOException e) {
			// TODO: handle exception
			throw new RuntimeException("Невозможно загрузить музыку '" + fileName + "'");
		}
	}
	
	@Override
	public Sound newSound(String fileName) {
		// TODO Auto-generated method stub
		try {
			AssetFileDescriptor assetFileDescriptor = assets.openFd(fileName);
			int soundId = soundPool.load(assetFileDescriptor, 0);
			return new AndroidSound(soundPool, soundId);
		} catch (IOException e) {
			// TODO: handle exception
			throw new RuntimeException("Невозможно загрузить звук '" + fileName + "'");
		}
	}

}