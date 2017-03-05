package nebulous.graphics.renderers;

import nebulous.graphics.Renderer;
import nebulous.graphics.shaders.DefaultShader;
import nebulous.graphics.shaders.Shader;

public class DefaultRenderer extends Renderer {
	
	private static Shader shader = new DefaultShader();

	public DefaultRenderer() { //TODO: remove camera
		super(shader);
	}
	
}
