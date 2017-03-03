package nebulous.graphics;

import nebulous.entity.Entity;
import nebulous.entity.component.Mesh;
import nebulous.entity.component.Transform;
import nebulous.graphics.shaders.Shader;

public class Renderer {
	
	private Shader shader = null;
	private Camera camera = null;
	private GameWindow window = null;

	public Renderer(Shader shader, Camera camera, GameWindow window) {
		this.shader = shader;
		this.camera = camera;
		this.window = window;
	}

	public void render(Entity entity) {
		
		shader.bind();
		
		shader.updateUniforms();

		shader.setUniform("projectionMatrix", camera.calculateProjectionMatrix(window));
		shader.setUniform("viewMatrix", camera.calculateViewMatrix(window));
		shader.setUniform("modelMatrix", camera.getModelViewMatrix(entity.getComponent(Transform.class)));
		
		((Mesh)entity.getComponent(Mesh.class)).renderMesh();
		
	    shader.unbind();
		
	}

}
