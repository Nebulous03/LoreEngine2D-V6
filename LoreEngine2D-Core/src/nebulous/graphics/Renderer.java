package nebulous.graphics;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import nebulous.Game;
import nebulous.entity.Entity;
import nebulous.entity.component.Mesh;
import nebulous.entity.component.Texture;
import nebulous.entity.component.Transform;
import nebulous.graphics.shaders.Shader;

public class Renderer {
	
	protected Shader shader = null;
	protected Camera camera = null;
	protected GameWindow window = null;

	public Renderer(Shader shader) {
		this.shader = shader;
	}
	
	public void init(Game game) {
		this.window = game.getWindow();
		this.camera = game.getActiveLevel().getCamera(); //TODO: needs to update
	}

	public void render(Entity entity) {
		
		shader.bind();
		
		shader.updateUniforms();

		shader.setUniform("projectionMatrix", camera.calculateProjectionMatrix(window));
		shader.setUniform("viewMatrix", camera.calculateViewMatrix(window));
		shader.setUniform("modelMatrix", camera.getModelViewMatrix(entity.getComponent(Transform.class)));
		
		renderMesh((Mesh)entity.getComponent(Mesh.class), (Texture)entity.getComponent(Texture.class));
		
	    shader.unbind();
		
	}

	public void renderMesh(Mesh mesh, Texture texture) {
		
        // Bind VAO Data
        glBindVertexArray(mesh.vao);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        
        // Bind Texture Data
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
        
        // Draw Mesh
        glDrawElements(GL_TRIANGLES, mesh.vCount, GL_UNSIGNED_INT, 0);

        // Restore state
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        
	}
	
}
