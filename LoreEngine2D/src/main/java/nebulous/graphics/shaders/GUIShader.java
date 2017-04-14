package nebulous.graphics.shaders;

public class GUIShader extends Shader {
	
	private static GUIShader instance = new GUIShader();
	
	public GUIShader() {
		super("/shaders/guiShader.vs", "/shaders/guiShader.fs");
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
