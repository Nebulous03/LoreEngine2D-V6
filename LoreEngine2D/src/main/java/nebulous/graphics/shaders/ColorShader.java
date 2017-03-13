package nebulous.graphics.shaders;

import java.awt.Color;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;

import nebulous.graphics.shaders.Shader;

public class ColorShader extends Shader {
	
	private Color color = null;
	private Vector4f vColor = null;
	
	private static ColorShader instance = new ColorShader(Color.WHITE);
	
	public ColorShader(Color color) {
		super("/shaders/colorShader.vs", "/shaders/colorShader.fs");
		this.color = color;
		this.vColor = new Vector4f();
		vColor.x = (float) color.getRed() / 255.0f;
		vColor.y = (float) color.getGreen() / 255.0f;
		vColor.z = (float) color.getBlue() / 255.0f;
		vColor.w = (float) color.getAlpha() / 255.0f;
	}

	@Override
	public void generateUniforms() {
		
		// Color Uniform
		addUniform("color");
		addUniform("projectionMatrix");
		addUniform("modelMatrix");
		addUniform("viewMatrix");
	}
	
	@Override
	public void updateUniforms() {
		setUniform("color", vColor);
	}
	
	public void setUniform(String name, Vector4f value) {
		GL20.glUniform4f(getUniform(name), value.x, value.y, value.z, value.w);
	}
	
	public void setUniform(String name, Color value) {
		GL20.glUniform4f(getUniform(name), value.getRed() / 255f, value.getGreen() / 255f, value.getBlue() / 255f, value.getAlpha() / 255f);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		vColor.x = (float) color.getRed() / 255.0f;
		vColor.y = (float) color.getGreen() / 255.0f;
		vColor.z = (float) color.getBlue() / 255.0f;
		vColor.w = (float) color.getAlpha() / 255.0f;
	}

	public static ColorShader instance() {
		return instance;
	}

}
