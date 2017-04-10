package nebulous.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import nebulous.Game;
import nebulous.entity.Entity;
import nebulous.graphics.component.Mesh;
import nebulous.graphics.component.Texture;
import nebulous.graphics.component.Transform;
import nebulous.graphics.shaders.Shader;

public class Renderer {
	
	protected Shader shader = null;
	protected Camera camera = null;
	protected Window window = null;

	public Renderer(Shader shader) {
		this.shader = shader;
	}
	
	public void init(Game game) {
		this.window = game.getWindow();
		this.camera = game.getActiveLevel().getCamera(); //TODO: needs to update
	}

	public void render(Entity entity) {
		
		shader.bind();
		
		if(!Window.isLegacy()) {
		
			shader.updateUniforms();
	
			shader.setUniform("projectionMatrix", camera.calculateProjectionMatrix(window));
			shader.setUniform("viewMatrix", camera.calculateViewMatrix(window));
			shader.setUniform("modelMatrix", camera.getModelViewMatrix(entity.getComponent(Transform.class)));
			
		}
		
		renderMesh((Mesh)entity.getComponent(Mesh.class), (Texture)entity.getComponent(Texture.class));
		
	    shader.unbind();
		
	}

	public void renderMesh(Mesh mesh, int texID) {
		
		if(!Window.isLegacy()) {
		
	        // Bind VAO Data
	        glBindVertexArray(mesh.vao);
	        glEnableVertexAttribArray(0);
	        glEnableVertexAttribArray(1);
	        
	        // Bind Texture Data
	        glActiveTexture(GL_TEXTURE0);
	        glBindTexture(GL_TEXTURE_2D, texID);
	        
	        // Draw Mesh
	        glDrawElements(GL_TRIANGLES, mesh.vCount, GL_UNSIGNED_INT, 0);
	        
	        // Restore state
	        glDisableVertexAttribArray(0);
	        glDisableVertexAttribArray(1);
	        glBindVertexArray(0);
	        
		} else {
			
			glActiveTexture(GL_TEXTURE0);
		    glBindTexture(GL_TEXTURE_2D, texID);
			
			glBegin(GL_QUADS);
			
			glTexCoord2d(0.0,0.0);
			glVertex2f(-0.5f, -0.5f); // BL
			
			glTexCoord2d(-1.0,-1.0);
			glVertex2f(-0.5f, 0.5f);  // TL
			
			glTexCoord2d(1.0,-1.0);
			glVertex2f(0.5f, 0.5f);   // TR
			
			glTexCoord2d(1.0,1.0);
			glVertex2f(0.5f, -0.5f);  // BR
			
			glEnd();
			
			glFlush();
			
		}
        
	}
	
	public void renderMesh(Mesh mesh, Texture texture) {
		renderMesh(mesh, texture.getTextureID());
	}
	
}
