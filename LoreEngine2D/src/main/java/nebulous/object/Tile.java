package nebulous.object;

import nebulous.graphics.component.Mesh;
import nebulous.graphics.component.Texture;
import nebulous.physics.component.CollisionBox;

public class Tile {
	
	public int x = 0;
	public int y = 0;
	
	public int textureID = -1;
	public Mesh mesh;
	public CollisionBox box;
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getTextureID() {
		return textureID;
	}

	public Tile setTexture(int textureID) {
		this.textureID = textureID;
		return this;
	}
	
	public Tile setTexture(Texture texture) {
		this.textureID = texture == null ? -1 : texture.getTextureID();
		return this;
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
}
