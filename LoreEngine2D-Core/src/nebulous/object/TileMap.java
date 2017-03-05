package nebulous.object;

import org.joml.Vector2f;

import nebulous.Game;
import nebulous.entity.Entity;
import nebulous.entity.component.CollisionBox;
import nebulous.entity.component.Mesh;
import nebulous.entity.component.Render;
import nebulous.entity.component.Texture;
import nebulous.graphics.renderers.TileMapRenderer;

public class TileMap extends Entity {
	
	private Tile[] tiles = null;
	private int width	 = 16;
	private int height   = 16;
	private int cullX	 = 16;
	private int cullY	 = 16;
	private boolean cull = false;
	private boolean collisions = false;
	
	public TileMap(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width * height];
		add(new Render(new TileMapRenderer(this)));
		for(int i = 0; i < tiles.length; i++) tiles[i] = new Tile(); //TODO: Move to init?
	}
	
	@Override
	public void init(Game game) {

		for(int i = 0; i < tiles.length; i++) {
			tiles[i].mesh = Mesh.PLANE();
			tiles[i].mesh.init(game);
			tiles[i].setPos(i,i); //TODO: fin
		}
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				tiles[x + (y * width)].setPos(x,y);
				tiles[x + (y * width)].box = new CollisionBox(1, 1);
				tiles[x + (y * width)].box.origin = new Vector2f(x,y);	 //TODO: I dont like this
			}
		}
	}
	
	public TileMap populate(Texture texture) {
		for(int i = 0; i < tiles.length; i++) tiles[i].setTexture(texture.getTextureID());
		return this;
	}
	
	public TileMap populate(int texture) {
		for(int i = 0; i < tiles.length; i++) tiles[i].setTexture(texture);
		return this;
	}
	
	public void fill(int x, int y, int width, int height) {
		//TODO: do
	}
	
	public Tile get(int x, int y) {
		return tiles[x + (y * width)];
	}

	public void set(int x, int y, Texture texture) {
		if(texture != null) get(x, y).setTexture(texture.getTextureID());
		else get(x, y).setTexture(-1);
	}
	
	public void set(int x, int y, int texture) {
		get(x, y).setTexture(texture);
	}

	public TileMap setCull(int cullX, int cullY) {
		this.cullX = cullX;
		this.cullY = cullY;
		return this;
	}
	
	public TileMap enableCulling() {
		cull = true;
		return this;
	}
	
	public TileMap disableCulling() {
		cull = false;
		return this;
	}
	
	public boolean isCulling() {
		return cull;
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

	public int getCullY() {
		return cullY;
	}

	public boolean isCollider() {
		return collisions;
	}

	public TileMap enableCollision() {
		collisions = true;
		return this;
	}
	
	public TileMap disableCollision() {
		collisions = false;
		return this;
	}
	
}
