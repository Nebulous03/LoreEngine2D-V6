package nebulous.graphics.renderers;

import nebulous.graphics.Renderer;
import nebulous.graphics.shaders.DefaultShader;

public class DefaultRenderer extends Renderer {
	
	private static DefaultRenderer instance = new DefaultRenderer();

	public DefaultRenderer() { //TODO: remove camera
		super(DefaultShader.instance());
	}

	public static DefaultRenderer instance() {
		return instance;
	}
	
}
