package nebulous.testGame;

import nebulous.Game;
import nebulous.graphics.primatives.Mesh;
import nebulous.logic.Input;
import nebulous.object.Entity2D;
import nebulous.object.Level2D;
import nebulous.object.TileMap;
import nebulous.physics.BoundingBox2D;
import nebulous.physics.Collision2D;

public class Player extends Entity2D{
	
	public boolean gravity = true;
	public float gravityForce = -0.3f;

	public Player(Mesh mesh, int x, int y) {
		super(mesh, x, y);
	}

	@Override
	public void init() {
		boundingBox = new BoundingBox2D(1f, 1f, position);
		System.out.println("Player initialized");
	}

	float speed = 0.1f;
	
	@Override
	public void update(Game game, double delta) {
		
		float deltaX = 0;
		float deltaY = 0;
		
		// SPACE
		if(Input.isKeyHeld(Input.KEY_SPACE)){
			
		}
		
		// W(UP)
		if(Input.isKeyHeld(Input.KEY_W)){
			deltaY += speed;
		}
		
		// A(LEFT)
		if(Input.isKeyHeld(Input.KEY_A)){
			deltaX += -speed;
		}
		
		// S(DOWN)
		if(Input.isKeyHeld(Input.KEY_S)){
			deltaY += -speed;
		}
		
		// D(RIGHT)
		if(Input.isKeyHeld(Input.KEY_D)){
			deltaX += speed;
		}
		
		attemptMove(game.getActiveLevel(), deltaX, deltaY);
		
		game.getActiveLevel().getCamera().setPosition(position.x, position.y, game.getActiveLevel().getCamera().getPosition().z); //TODO: Simplify
		
	}
	
	Collision2D collision = null;
	
	public void attemptMove(Level2D level, float deltaX, float deltaY){
		
		// "Ghost" move the bounding box
		boundingBox.origin.x += deltaX;
		boundingBox.origin.y += deltaY;
		
		if(boundingBox.origin.x < 0) boundingBox.origin.x = 0;
		if(boundingBox.origin.y < 0) boundingBox.origin.y = 0;
		
		// Check for collisions with entities
		for(Entity2D entity : level.getEntities()) {
			if(entity != this) {
				if(entity.boundingBox != null){
					collision = Collision2D.getCollision(boundingBox, entity.boundingBox);
					resolveCollision(collision);
				}
			}
		}
		
		// Check collision with TileMap
		for(TileMap map : level.getTileMaps()) {
			if(map.collisionLayer) {
				
				if(boundingBox.origin.x > map.getWidth() - 1) boundingBox.origin.x = map.getWidth() - 1;
				if(boundingBox.origin.y > map.getHeight() - 1) boundingBox.origin.y = map.getHeight() - 1 ;
				
				int posX = (int)(position.x + 0.5f);
				int posY = (int)(position.y + 0.5f);
				
				// CENTER
				resolveCollision(Collision2D.getCollision(this.boundingBox, map.getTile(posX, posY).boundingBox));
				
				// NORTH
				if(posY < map.getHeight() -1)
				resolveCollision(Collision2D.getCollision(this.boundingBox, map.getTile(posX, posY + 1).boundingBox));
				
				// SOUTH
				if(posY > 0)
				resolveCollision(Collision2D.getCollision(this.boundingBox, map.getTile(posX, posY - 1).boundingBox));
				
				// EAST
				if(posX < map.getWidth() - 1)
				resolveCollision(Collision2D.getCollision(this.boundingBox, map.getTile(posX + 1, posY).boundingBox));
				
				// WEST
				if(posX > 0)
				resolveCollision(Collision2D.getCollision(this.boundingBox, map.getTile(posX - 1, posY).boundingBox));
			
				// NORTH EAST
				if(posX < map.getWidth() - 1)
				if(posY < map.getHeight() -1)
				resolveCollision(Collision2D.getCollision(this.boundingBox, map.getTile(posX + 1, posY + 1).boundingBox));
			
				// NORTH WEST
				if(posX > 0)
				if(posY < map.getHeight() -1)
				resolveCollision(Collision2D.getCollision(this.boundingBox, map.getTile(posX - 1, posY + 1).boundingBox));
			
				// SOUTH EAST
				if(posX < map.getWidth() - 1)
				if(posY - 1 > 0)
				resolveCollision(Collision2D.getCollision(this.boundingBox, map.getTile(posX + 1, posY - 1).boundingBox));
			
				// SOUTH WEST
				if(posX > 0)
				if(posY - 1 > 0)
				resolveCollision(Collision2D.getCollision(this.boundingBox, map.getTile(posX - 1, posY - 1).boundingBox));
			
			}
			
		}
		
	}
	
	public void resolveCollision(Collision2D collision) {
		
		if(collision.getSide() != Collision2D.SIDE_NONE){
			
			if(collision.getSide() == Collision2D.SIDE_NORTH) {
				boundingBox.origin.sub(0, collision.getDistanceY());
			}
			
			if(collision.getSide() == Collision2D.SIDE_SOUTH) {
				boundingBox.origin.sub(0, -collision.getDistanceY());
			}
			
			if(collision.getSide() == Collision2D.SIDE_EAST) {
				boundingBox.origin.sub(collision.getDistanceX(), 0);
			}
			
			if(collision.getSide() == Collision2D.SIDE_WEST) {
				boundingBox.origin.sub(-collision.getDistanceX(), 0);
			}
			
		}
	}

}
