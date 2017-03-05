package nebulous.terrariaClone;

import nebulous.Game;
import nebulous.entity.Entity;
import nebulous.entity.component.CollisionBox;
import nebulous.entity.component.Transform;
import nebulous.entity.simple.EntityMovable;
import nebulous.logic.Input;
import nebulous.object.Level;
import nebulous.object.TileMap;
import nebulous.physics.Collision;
import nebulous.utils.Console;

public class Player extends EntityMovable {
	
	private float walkSpeed = 0.1f;
	
	protected CollisionBox box = null;
	
	public Player(float x, float y) {
		super(x, y);
	}
	
	@Override
	public void init(Game game) {
		super.init(game);
		box = (CollisionBox)getComponent(CollisionBox.class);
		Console.println("Player", "Player initialized...");
	}

	@Override
	public void update(Game game, double delta) {
		
		float deltaX = 0;
		float deltaY = 0;
		
		// SPACE
		if(Input.isKeyHeld(Input.KEY_SPACE)){
			
		}
		
		// W(UP)
		if(Input.isKeyHeld(Input.KEY_W)){
			deltaY += walkSpeed;					//TODO: FIX and multiply with delta
		}
		
		// A(LEFT)
		if(Input.isKeyHeld(Input.KEY_A)){
			deltaX += -(walkSpeed);
		}
		
		// S(DOWN)
		if(Input.isKeyHeld(Input.KEY_S)){
			deltaY += -(walkSpeed);
		}
		
		// D(RIGHT)
		if(Input.isKeyHeld(Input.KEY_D)){
			deltaX += walkSpeed;
		}
		
		attemptMove(game.getActiveLevel(), deltaX, deltaY);
		game.getActiveLevel().getCamera().setPosition(((Transform)getComponent(Transform.class)).position);
		
	}
	
	Collision collision = null;
	
	//TODO: Move these to a helper class
	
	public void attemptMove(Level level, float deltaX, float deltaY){
		
		// "Ghost" move the bounding box
		box.origin.x += deltaX;
		box.origin.y += deltaY;
		
		if(box.origin.x < 0) box.origin.x = 0;
		if(box.origin.y < 0) box.origin.y = 0;
		
		// Check for collisions with entities
		for(Entity entity : level.getEntitySystem().getAllEntities()) {
			if(entity != this) {
				if(entity instanceof TileMap){
					collideWithTileMap((TileMap)entity);
				} else if(!entity.hasComponent(CollisionBox.class)){
					collision = Collision.getCollision(box, ((CollisionBox)entity.getComponent(CollisionBox.class)));
					resolveCollision(collision);
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
			if(map.get(posX, posY).textureID != -1) resolveCollision(Collision.getCollision(this.box, map.get(posX, posY).box));
			
			// NORTH
			if(posY < map.getHeight() -1)
				if(map.get(posX, posY + 1).textureID != -1) resolveCollision(Collision.getCollision(this.box, map.get(posX, posY + 1).box));
			
			// SOUTH
			if(posY > 0)
				if(map.get(posX, posY -1).textureID != -1) resolveCollision(Collision.getCollision(this.box, map.get(posX, posY - 1).box));
			
			// EAST
			if(posX < map.getWidth() - 1)
				if(map.get(posX + 1, posY).textureID != -1) resolveCollision(Collision.getCollision(this.box, map.get(posX + 1, posY).box));
			
			// WEST
			if(posX > 0)
				if(map.get(posX - 1, posY).textureID != -1) resolveCollision(Collision.getCollision(this.box, map.get(posX - 1, posY).box));
		
			// NORTH EAST
			if(posX < map.getWidth() - 1)
			if(posY < map.getHeight() -1)
				if(map.get(posX + 1, posY + 1).textureID != -1) resolveCollision(Collision.getCollision(this.box, map.get(posX + 1, posY + 1).box));
		
			// NORTH WEST
			if(posX > 0)
			if(posY < map.getHeight() -1)
				if(map.get(posX - 1, posY + 1).textureID != -1) resolveCollision(Collision.getCollision(this.box, map.get(posX - 1, posY + 1).box));
		
			// SOUTH EAST
			if(posX < map.getWidth() - 1)
			if(posY - 1 > 0)
				if(map.get(posX + 1, posY - 1).textureID != -1) resolveCollision(Collision.getCollision(this.box, map.get(posX + 1, posY - 1).box));
	
			// SOUTH WEST
			if(posX > 0)
			if(posY - 1 > 0)
				if(map.get(posX - 1, posY - 1).textureID != -1) resolveCollision(Collision.getCollision(this.box, map.get(posX - 1, posY - 1).box));
		
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

}
