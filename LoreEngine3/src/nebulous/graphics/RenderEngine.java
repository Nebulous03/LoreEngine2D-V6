package nebulous.graphics;

import nebulous.component.GameObject2D;
import nebulous.graphics.shaders.DefaultShader;
import nebulous.graphics.shaders.Shader;

public class RenderEngine {
	
	private static Shader DEFALUT_SHADER = null;
	private GameWindow    window 		 = null;
	private Shader		  shader 		 = null;
	
	public void init(GameWindow window) {
		this.window = window;
		DEFALUT_SHADER = new DefaultShader();
	}
	
	public void render(Camera camera, GameObject2D object) {
		
		if(object.getShader() != null)
			shader = object.getShader();
		else{
			shader = DEFALUT_SHADER;
		}
		
		object.render(window, camera, shader);
		
	}

}
