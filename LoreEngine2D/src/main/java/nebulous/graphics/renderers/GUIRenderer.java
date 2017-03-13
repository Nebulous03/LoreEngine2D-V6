package nebulous.graphics.renderers;

import java.awt.Color;

import nebulous.entity.Entity;
import nebulous.graphics.Renderer;
import nebulous.graphics.component.Mesh;
import nebulous.graphics.component.Texture;
import nebulous.graphics.component.Transform;
import nebulous.graphics.shaders.ColorShader;
import nebulous.graphics.shaders.DefaultShader;
import nebulous.graphics.shaders.GUIShader;

public class GUIRenderer extends Renderer {
	
	private static GUIRenderer instance = new GUIRenderer();

	public GUIRenderer() {
		super(GUIShader.instance());
//		ColorShader.instance().setColor(Color.MAGENTA);
	}
	
	@Override
	public void render(Entity entity) {
		
		shader.bind();
		
		shader.updateUniforms();

//		shader.setUniform("projectionMatrix", camera.calculateProjectionMatrix(window));
		shader.setUniform("viewMatrix", camera.calculateViewMatrix(window));
		shader.setUniform("modelMatrix", camera.getModelViewMatrix(entity.getComponent(Transform.class)));
		
		renderMesh((Mesh)entity.getComponent(Mesh.class), (Texture)entity.getComponent(Texture.class));
		
	    shader.unbind();
	    
	}
	
	public static GUIRenderer instance() {
		return instance;
	}

}
