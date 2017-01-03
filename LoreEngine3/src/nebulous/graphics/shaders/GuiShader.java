package nebulous.graphics.shaders;

public class GuiShader extends Shader{
	
	public GuiShader() {
		super("/shaders/guiShader.vs", "/shaders/guiShader.fs");
	}

	@Override
	public void generateUniforms() {
		
		// Texture Uniform
		addUniform("sampler");
		setUniform("sampler", 0);
		
		addUniform("transform");
		addUniform("view");
		
	}

	@Override
	public void updateUniforms() {
		
	}

}
