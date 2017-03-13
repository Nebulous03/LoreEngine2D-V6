package nebulous.graphics.renderers;

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

import nebulous.entity.Entity;
import nebulous.graphics.Renderer;
import nebulous.graphics.component.Texture;
import nebulous.graphics.shaders.DefaultShader;
import nebulous.object.Tile;
import nebulous.object.TileMap;

public class TileMapRenderer extends Renderer {
	
	private TileMap map;

	public TileMapRenderer(TileMap map) {
		super(new DefaultShader());
		this.map = map;
		//TODO: DONT USE NEW!!!
	}
	
	@Override
	public void render(Entity entity) {
		
		float cameraX = (int)(camera.getPosition().x + 0.5f);
		float cameraY = (int)(camera.getPosition().y + 0.5f);
		
		int cullX = map.getCullX();
		int cullY = map.getCullY();
		
		int height = map.getHeight();
		int width = map.getWidth();
		
		for(int id = 1; id <= Texture.getIndexSize(); id++) {
			
			shader.bind();
			
			shader.setUniform("projectionMatrix", camera.calculateProjectionMatrix(window));
			shader.setUniform("viewMatrix", camera.calculateViewMatrix(window));
			
			shader.updateUniforms();
			
			if(map.isCulling()) {
			
				int posXStart = (int) (cameraX - (cullX / 2));
				int posYStart = (int) (cameraY - (cullY / 2));
				
				if(posYStart + cullY > height) posYStart = height - cullY;
				if(posXStart + cullX > width) posXStart = width - cullX;
				
				if(posYStart < 0) posYStart = 0;
				if(posXStart < 0) posXStart = 0;
				
				for(int y = posYStart; y < posYStart + cullY; y++) {
					for(int x = posXStart; x < posXStart + cullX; x++) {
						if(map.get(x, y).getTextureID() <= 0) continue;
						if(map.get(x, y).getTextureID() == id)
							renderTile(map.get(x, y));
					}
				}
			
			} else {
			
				for(int y = 0; y < height; y++){
					for(int x = 0; x < width; x++){
						if(map.get(x, y).getTextureID() <= 0) continue;
						if(map.get(x, y).getTextureID() == id)
							renderTile(map.get(x, y));
					}
				}
				
			}
			
			shader.unbind();
		}
	}
	
	public void renderTile(Tile tile) {
		
		 // Bind VAO Data
        glBindVertexArray(tile.mesh.vao);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        
        shader.setUniform("modelMatrix", camera.getModelViewMatrix(tile.x, tile.y, 1, 1, 0));
        
        // Bind Texture Data
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, tile.getTextureID());
        
        // Draw Mesh
        glDrawElements(GL_TRIANGLES, tile.mesh.vCount, GL_UNSIGNED_INT, 0);

        // Restore state
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
	}

}
