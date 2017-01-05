package nebulous.component;

import org.joml.Vector2f;
import org.joml.Vector3f;

import nebulous.graphics.Camera;
import nebulous.graphics.GameWindow;
import nebulous.graphics.primatives.Texture;
import nebulous.graphics.shaders.Shader;
import nebulous.physics.BoundingBox2D;

public class TileMap extends GameObject2D{
	
	private Tile[]  tiles   = null;
	private int	    height  = 0;
	private int	    width   = 0;
	private int	    cullX   = 0;
	private int	    cullY   = 0;
	private boolean culling = true;
	
	public boolean collisionLayer = false;
	
	public TileMap(int width, int height, boolean collisionLayer) {
		this(null, width, height, collisionLayer);
	}
	
	public TileMap(int width, int height, int cullX, int cullY, boolean collisionLayer) {
		this(null, width, height, cullX, cullY, collisionLayer);
	}
	
	public TileMap(Texture texture, int width, int height, boolean collisionLayer) {
		this(texture, width, height, -1, -1, collisionLayer);
	}
	
	public TileMap(Texture texture, int width, int height, int cullX, int cullY, boolean collisionLayer) {
		
		this.width = width;
		this.height = height;
		this.cullX = cullX;
		this.cullY = cullY;
		this.position = new Vector2f(0);
		this.rotation = new Vector3f(0);
		this.scale = new Vector3f(1);
		this.collisionLayer = collisionLayer;
		tiles = new Tile[width * height];
		
		if(cullX == -1 || cullY == -1) {
			culling = false;
		}
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				tiles[x + y * width] = new Tile(texture);
				tiles[x + y * width].setPosition(x, y);
			}
		}
		
	}
	
	int cameraX = 0;
	int cameraY = 0;
	
	int posXStart = 0;
	int posYStart = 0;
	
	@Override
	public void render(GameWindow window, Camera camera, Shader shader) {
		
		cameraX = (int)(camera.getPosition().x + 0.5f);
		cameraY = (int)(camera.getPosition().y + 0.5f);
		
		for(int id = 1; id <= Texture.maxTextureID; id++) {
			
			shader.bind();
			
			shader.setUniform("projectionMatrix", camera.calculateProjectionMatrix(window));
			shader.setUniform("viewMatrix", camera.calculateViewMatrix(window));
			
			shader.updateUniforms();
			
			if(culling) {
			
				int posXStart = cameraX - (cullX / 2);
				int posYStart = cameraY - (cullY / 2);
				
				if(posYStart + cullY > height) posYStart = height - cullY;
				if(posXStart + cullX > width) posXStart = width - cullX;
				
				if(posYStart < 0) posYStart = 0;
				if(posXStart < 0) posXStart = 0;
				
				for(int y = posYStart; y < posYStart + cullY; y++) {
					for(int x = posXStart; x < posXStart + cullX; x++) {
						if(tiles[x + y * width].getTexure() == null) continue;
						if(tiles[x + y * width].getTexure().textureID == id)
							tiles[x + y * width].render(window, camera, shader);
					}
				}
			
			} else {
			
				for(int y = 0; y < height; y++){
					for(int x = 0; x < width; x++){
						if(tiles[x + y * width].getTexure() == null) continue;
						if(tiles[x + y * width].getTexure().textureID == id)
							tiles[x + y * width].render(window, camera, shader);
					}
				}
				
			}
			
			shader.unbind();
			
		}
		
	}
	
	public void setTile(int x, int y, Texture texture) {
		if(y < height && x < width) {
			tiles[x + y * width].setTexture(texture);
			if(texture == null) {
				tiles[x + y * width].boundingBox = null;
			} else {
				tiles[x + y * width].boundingBox = new BoundingBox2D(1, 1, new Vector2f(x,y));
			}
		}
	}
	
	public Tile getTile(int x, int y) {
		if((x + y * width) < 0) return null;
		if((x + y * width) > tiles.length - 1) return null;
		if(x > width)return null;
		if(y > height) return null;
		return tiles[x + y * width];
	}

	public Tile[] getTiles() {
		return tiles;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getCullX() {
		return cullX;
	}

	public void setCullX(int cullX) {
		this.cullX = cullX;
	}

	public int getCullY() {
		return cullY;
	}

	public void setCullY(int cullY) {
		this.cullY = cullY;
	}

	public boolean isCulling() {
		return culling;
	}

	public void enableCulling(boolean culling) {
		this.culling = culling;
	}

}
