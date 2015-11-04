package com.wolfgames.framework.gl;

import com.wolfgames.framework.Texture;

public class TextureRegion {
	public final float u1, v1;
	public final float u2, v2;
	public final Texture texture;
	
	public TextureRegion(Texture texture, float x, float y, float width, float height) {
		// TODO Auto-generated constructor stub
		this.u1 = x / texture.getWidth();
		this.v1 = y / texture.getHeight();
		this.u2 = this.u1 + width / texture.getWidth();
		this.v2 = this.v1 + height / texture.getHeight();
		this.texture = texture;
		
	}

}
