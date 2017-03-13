package nebulous.graphics.shaders;

public class DefaultShader extends Shader {
	
	private static DefaultShader instance = new DefaultShader();
	
	public DefaultShader() {
		super("/shaders/defaultShader.vs", "/shaders/defaultShader.fs");
	}

	@Override
	public void generateUniforms() {
		
		// Texture Uniform
		addUniform("sampler");
		setUniform("sampler", 0);
		
		// Projection Uniform
		addUniform("projectionMatrix");
		addUniform("modelMatrix");
		addUniform("viewMatrix");
		
	}

	@Override
	public void updateUniforms() {
		
	}

	public static Shader instance() {
		return instance;
	}

}
