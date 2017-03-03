package nebulous.graphics.renderers;

import nebulous.graphics.Camera;
import nebulous.graphics.GameWindow;
import nebulous.graphics.Renderer;
import nebulous.graphics.shaders.DefaultShader;
import nebulous.graphics.shaders.Shader;

public class DefaultRenderer extends Renderer {
	
	private static Shader shader = new DefaultShader();

	public DefaultRenderer(Camera camera, GameWindow window) { //TODO: remove camera
		super(shader, camera, window);
		
	}

	public static Renderer instance() {
		return null;
	}

}
