package nebulous.graphics.renderers;

import nebulous.Game;
import nebulous.entity.Entity;
import nebulous.graphics.Camera;
import nebulous.graphics.Renderer;
import nebulous.graphics.component.Mesh;
import nebulous.graphics.component.Texture;
import nebulous.graphics.component.Transform;
import nebulous.graphics.shaders.DefaultShader;

public class GUIRenderer extends Renderer {
	
	private static GUIRenderer instance = new GUIRenderer();

	public GUIRenderer() {
		super(DefaultShader.instance());
		this.camera = new Camera().setPerspective(Camera.ORTHOGRAPHIC); //TODO: move back to init?
	}
	
	@Override
	public void init(Game game) {
		this.window = game.getWindow();
	}
	
	@Override
	public void render(Entity entity) {
		
		shader.bind();
		
		shader.updateUniforms();

		shader.setUniform("projectionMatrix", camera.calculateProjectionMatrix(window));
		shader.setUniform("viewMatrix", camera.calculateViewMatrix(window));
		shader.setUniform("modelMatrix", camera.getModelViewMatrix(entity.getComponent(Transform.class)));
		
		renderMesh((Mesh)entity.getComponent(Mesh.class), (Texture)entity.getComponent(Texture.class));
		
	    shader.unbind();
	    
	}
	
	public static GUIRenderer instance() {
		return instance;
	}

	public Camera getCamera() {
		return camera;
	}

}
