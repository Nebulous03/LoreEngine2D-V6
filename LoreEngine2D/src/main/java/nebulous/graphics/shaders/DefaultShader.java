package nebulous.graphics.shaders;

import nebulous.graphics.Window;

public class DefaultShader extends Shader {
	
	private static DefaultShader instance = new DefaultShader();
	
	public DefaultShader() {
		super( Window.isLegacy() ? "/shaders/defaultShader120.vs" : "/shaders/defaultShader.vs", 
			   Window.isLegacy() ? "/shaders/defaultShader120.fs" : "/shaders/defaultShader.fs");
	}

	@Override
	public void generateUniforms() {
		
		if(!Window.isLegacy()) {
		
		// Texture Uniform
		addUniform("sampler");
		setUniform("sampler", 0);
		
		// Projection Uniform
		addUniform("projectionMatrix");
		addUniform("modelMatrix");
		addUniform("viewMatrix");
		
		}
		
	}

	@Override
	public void updateUniforms() {
		
	}

	public static Shader instance() {
		return instance;
	}

}
