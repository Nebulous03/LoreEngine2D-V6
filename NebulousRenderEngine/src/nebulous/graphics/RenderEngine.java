package nebulous.graphics;

import nebulous.game.GameObject;
import nebulous.graphics.shaders.DefaultShader;
import nebulous.graphics.shaders.Shader;

public class RenderEngine {
	
	private static Shader DEFALUT_SHADER = null;
	private GameWindow    window 		 = null;
	private Camera		  camera		 = null;
	private Shader		  shader 		 = null;
	
	public void init(GameWindow window, Camera camera) {
		this.window = window;
		this.camera = camera;
		DEFALUT_SHADER = new DefaultShader();
	}
	
	public void render(GameObject object) {
		
		if(object.getShader() != null) shader = object.getShader();
		else shader = DEFALUT_SHADER;
		
		object.render(window, camera, shader);
		
	}

}
