package nebulous.graphics.renderers;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import nebulous.entity.Entity;
import nebulous.graphics.Renderer;
import nebulous.graphics.font.FontMap;
import nebulous.graphics.font.TextData;
import nebulous.graphics.shaders.DefaultShader;

public class TextRenderer extends Renderer {
	
	private TextData text;

	public TextRenderer(TextData text) { //TODO: remove camera
		super(new DefaultShader());
		this.text = text;
	}

	FontMap map = null;
	
	@Override
	public void render(Entity entity) {
		
		map = text.getFont();
		
		shader.bind();
		
		shader.setUniform("projectionMatrix", camera.calculateProjectionMatrix(window));
		shader.setUniform("viewMatrix", camera.calculateViewMatrix(window));
		
		shader.updateUniforms();
		
		float offset = 0;
		
		for(char c : text.getText().toCharArray()) {
			renderChar(c, offset);
			offset += 1f;
		}
		
		shader.unbind();
			
	}
	
	public void renderChar(char id, float offset) {
		
		 // Bind VAO Data
       glBindVertexArray(map.getMeshHash().get(id).vao);
       glEnableVertexAttribArray(0);
       glEnableVertexAttribArray(1);
       
       shader.setUniform("modelMatrix", camera.getModelViewMatrix(offset, 0, text.getSize(), text.getSize(), 0));
       
       // Bind Texture Data
       glActiveTexture(GL_TEXTURE0);
       glBindTexture(GL_TEXTURE_2D, map.getFullTexture().getTextureID());
       
       // Draw Mesh
       glDrawElements(GL_TRIANGLES, map.getMeshHash().get(id).vCount, GL_UNSIGNED_INT, 0);

       // Restore state
       glDisableVertexAttribArray(0);
       glDisableVertexAttribArray(1);
       glBindVertexArray(0);
	}
	
}
