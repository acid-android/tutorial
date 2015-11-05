package com.wolfgames.glbasics;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GLBasicsStarter extends ListActivity {
	String tests[] = { "GlSurfaceViewTest", "GLGameTest", "FirstTriangleTest", "ColoredTriangleTest", "TexturedTriangleTest", "IndexedTest", "BlendingTest", "BobTest", "BobOptimization", "CannonTest",
			"CannonGravityTest", "CollisionTest", "Camera2DTest", "TextureAtlasTest", "SpriteBatcherTest", "AnimationTest"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tests));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String testName = tests[position];
		try{
			Class clazz = Class.forName("com.wolfgames.glbasics." + testName);
			Intent intent = new Intent(this, clazz);
			startActivity(intent);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
