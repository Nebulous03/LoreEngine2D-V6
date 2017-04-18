package nebulous.addons.entity.simple;

import java.util.ArrayList;

import org.joml.Vector2f;

import nebulous.Game;
import nebulous.entity.Entity;
import nebulous.graphics.component.Mesh;
import nebulous.graphics.component.Render;
import nebulous.graphics.component.Texture;
import nebulous.graphics.component.Transform;
import nebulous.graphics.renderers.DefaultRenderer;
import nebulous.logic.component.Update;
import nebulous.logic.component.UpdateEvent;
import nebulous.object.Level;
import nebulous.object.TileMap;
import nebulous.physics.Collision;
import nebulous.physics.component.CollisionBox;

public abstract class EntityMovable extends Entity {
	
	protected CollisionBox box = null;
	
	protected EntityMovable() {
		this(0,0);
	}
	
	public EntityMovable(float x, float y) {
		add(new Transform(x, y));
		add(Mesh.PLANE());
		add(new Texture(Texture.UNKNOWN));
		add(new Render(DefaultRenderer.instance()));
		add(new Update(new UpdateEvent() {public void invoke(Game game, double delta) {updateMovable(game); update(game, delta);}}));
		add(new CollisionBox(1, 1));
		box = (CollisionBox)getComponent(CollisionBox.class);
	}
	
	public abstract void update(Game game, double delta);
	
	private void updateMovable(Game game) {
		((CollisionBox)getComponent(CollisionBox.class)).updateOrigin();
	}
	
	Collision collision = null;
	ArrayList<Collision> collisions = new ArrayList<Collision>(); 
	
	//TODO: Move these to a helper class
	
	public void attemptMove(Level level, float deltaX, float deltaY) {
		
		// "Ghost" move the bounding box
		box.origin.x += deltaX;
		box.origin.y += deltaY;
		
		// Check for collisions with entities
		for(Entity entity : level.getEntitySystem().getAllEntities()) {
			if(entity != this) {
				if(entity instanceof TileMap){
					collideWithTileMap((TileMap)entity);
					if(((TileMap)entity).isCollider()) {
						if(box.origin.x < 0) box.origin.x = 0;
						if(box.origin.y < 0) box.origin.y = 0;
						if(box.origin.x > ((TileMap)entity).getWidth() - 1) box.origin.x = ((TileMap)entity).getWidth() - 1;
						if(box.origin.y > ((TileMap)entity).getHeight() - 1) box.origin.y = ((TileMap)entity).getHeight() - 1;
					}
				} else if(entity.hasComponent(CollisionBox.class)){
					if(((CollisionBox)entity.getComponent(CollisionBox.class)).enabled) {
						collision = Collision.getCollision(box, ((CollisionBox)entity.getComponent(CollisionBox.class)));
						resolveCollision(collision);
					}
				}
			}
		}
		
	}
	
	public void collideWithTileMap(TileMap map) {	//TODO: move to TileMapCollider class
		
		if(map.isCollider()) {
			
			if(box.origin.x > map.getWidth() - 1) box.origin.x = map.getWidth() - 1;
			if(box.origin.y > map.getHeight() - 1) box.origin.y = map.getHeight() - 1 ;
			
			int posX = (int)(getPosition().x + 0.5f);
			int posY = (int)(getPosition().y + 0.5f);
			
			// CENTER
			if(map.get(posX, posY).textureID != -1) 
				resolveCollision(Collision.getCollision(this.box, map.get(posX, posY).box));
			
			// NORTH
			if(posY < map.getHeight() -1)
				if(map.get(posX, posY + 1).textureID != -1)
					resolveCollision(Collision.getCollision(this.box, map.get(posX, posY + 1).box));
			
			// SOUTH
			if(posY > 0)
				if(map.get(posX, posY -1).textureID != -1)
					resolveCollision(Collision.getCollision(this.box, map.get(posX, posY - 1).box));
			
			// EAST
			if(posX < map.getWidth() - 1)
				if(map.get(posX + 1, posY).textureID != -1)
					resolveCollision(Collision.getCollision(this.box, map.get(posX + 1, posY).box));
			
			// WEST
			if(posX > 0)
				if(map.get(posX - 1, posY).textureID != -1)
					resolveCollision(Collision.getCollision(this.box, map.get(posX - 1, posY).box));
		
			// NORTH EAST
			if(posX < map.getWidth() - 1)
			if(posY < map.getHeight() -1)
				if(map.get(posX + 1, posY + 1).textureID != -1)
					resolveCollision(Collision.getCollision(this.box, map.get(posX + 1, posY + 1).box));
		
			// NORTH WEST
			if(posX > 0)
			if(posY < map.getHeight() -1)
				if(map.get(posX - 1, posY + 1).textureID != -1)
					resolveCollision(Collision.getCollision(this.box, map.get(posX - 1, posY + 1).box));
		
			// SOUTH EAST
			if(posX < map.getWidth() - 1)
			if(posY - 1 > 0)
				if(map.get(posX + 1, posY - 1).textureID != -1)
					resolveCollision(Collision.getCollision(this.box, map.get(posX + 1, posY - 1).box));
	
			// SOUTH WEST
			if(posX > 0)
			if(posY - 1 > 0)
				if(map.get(posX - 1, posY - 1).textureID != -1)
					resolveCollision(Collision.getCollision(this.box, map.get(posX - 1, posY - 1).box));
		
		}
			
	}
	
	public void resolveCollision(Collision collision) {
		
		if(collision.getSide() != Collision.SIDE_NONE){
			
			if(collision.getSide() == Collision.SIDE_NORTH) {
				box.origin.sub(0, collision.getDistanceY());
			}
			
			if(collision.getSide() == Collision.SIDE_SOUTH) {
				box.origin.sub(0, -collision.getDistanceY());
			}
			
			if(collision.getSide() == Collision.SIDE_EAST) {
				box.origin.sub(collision.getDistanceX(), 0);
			}
			
			if(collision.getSide() == Collision.SIDE_WEST) {
				box.origin.sub(-collision.getDistanceX(), 0);
			}
			
		}
	}
	
	public EntityMovable move(float deltaX, float deltaY) {
		Vector2f pos = ((Transform)getComponent(Transform.class)).position; // TODO: Move these to own instance?
		pos.x += deltaX;
		pos.y += deltaY;
		return this;
	}
	
	public EntityMovable rotate(float degrees) {
		((Transform)getComponent(Transform.class)).rotation += degrees;
		return this;
	}
	
	public EntityMovable setPosition(Vector2f pos) {
		((Transform)getComponent(Transform.class)).position = pos;
		return this;
	}
	
	public EntityMovable setPosition(float x, float y) {
		Vector2f pos = ((Transform)getComponent(Transform.class)).position;
		pos.x = x;
		pos.y = y;
		return this;
	}
	
	public EntityMovable setRotation(float degrees) {
		((Transform)getComponent(Transform.class)).rotation = degrees;
		return this;
	}
	
	public float getX() {
		return ((Transform)getComponent(Transform.class)).position.x;
	}
	
	public float getY() {
		return ((Transform)getComponent(Transform.class)).position.y;
	}
	
	public Vector2f getPosition() {
		return ((Transform)getComponent(Transform.class)).position;
	}
	
	public float getRotation() {
		return ((Transform)getComponent(Transform.class)).rotation;
	}
	
	public Vector2f getScale() {
		return ((Transform)getComponent(Transform.class)).scale;
	}
	
	public EntityMovable setMesh(Mesh mesh) {
		((Mesh)getComponent(Mesh.class)).set(mesh);
		return this;
	}
	
	public EntityMovable setScale(Vector2f scale) {
		((Transform)getComponent(Transform.class)).scale = scale;
		return this;
	}
	
	public EntityMovable setTexture(Texture texture) {
		((Texture)getComponent(Texture.class)).set(texture);
		return this;
	}
	
	public EntityMovable setTexture(int texture) {
		((Texture)getComponent(Texture.class)).set(texture);
		return this;
	}
	
	public EntityMovable setCollisionBox(float width, float height, Vector2f origin) {
		((CollisionBox)getComponent(CollisionBox.class)).setSize(width, height, origin);
		return this;
	}
	
	public EntityMovable setCollisionBox(float width, float height) {
		((CollisionBox)getComponent(CollisionBox.class)).setSize(width, height);
		return this;
	}
	
	public EntityMovable setCollidable(boolean collidable) {
		((CollisionBox)getComponent(CollisionBox.class)).enabled = collidable;
		return this;
	}
}
