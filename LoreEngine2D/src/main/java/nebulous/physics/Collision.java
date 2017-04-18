package nebulous.physics;

import nebulous.physics.component.CollisionBox;

public class Collision {
	
	public static final int SIDE_NONE  = 0;
	public static final int SIDE_NORTH = 1;
	public static final int SIDE_SOUTH = 2;
	public static final int SIDE_EAST  = 3;
	public static final int SIDE_WEST  = 4;
	
	private float distanceX = 0;
	private float distanceY = 0;
	
	private int side = 0;
	
	public Collision(float distanceX, float distanceY, int side) {
		this.distanceX = distanceX;
		this.distanceY = distanceY;
		this.side = side;
	}
	
	static boolean collidingNorth = false;
	static boolean collidingSouth = false;
	static boolean collidingEast = false;
	static boolean collidingWest = false;
	
	public static Collision getCollision(CollisionBox box, CollisionBox collisionBox) {
		
//		System.out.println("--------------------\nFirst:\n\t\t" + box + "\nSecond\n\t\t" + collisionBox);
		
		float distanceX = 0;
		float distanceY = 0;
		int   side = 0;
		
		if(box == null || collisionBox == null) return new Collision(0, 0, 0);
		if(box.origin == null || collisionBox.origin == null) return new Collision(0, 0, 0);
		
		collidingNorth = ((box.origin.y + box.halfHeight) >= (collisionBox.origin.y - collisionBox.halfHeight))		// TOP1    > BOTTOM2
				      && ((box.origin.y - box.halfHeight) <= (collisionBox.origin.y - collisionBox.halfHeight));	// BOTTOM1 < BOTTOM2
		
		collidingSouth = ((box.origin.y - box.halfHeight) <= (collisionBox.origin.y + collisionBox.halfHeight))		// BOTTOM1 < TOP2
					  && ((box.origin.y + box.halfHeight) >= (collisionBox.origin.y + collisionBox.halfHeight));	// TOP1    > TOP2
		
		collidingEast =  ((box.origin.x + box.halfWidth)  >= (collisionBox.origin.x - collisionBox.halfWidth))		// EAST1   > WEST2
					  && ((box.origin.x + box.halfWidth)  <= (collisionBox.origin.x + collisionBox.halfWidth));		// WEST1   < WEST2
		
		collidingWest =  ((box.origin.x - box.halfWidth)  <= (collisionBox.origin.x + collisionBox.halfWidth))		// WEST1   < EAST2
					  && ((box.origin.x + box.halfWidth)  >= (collisionBox.origin.x + collisionBox.halfWidth));		// EAST1   > EAST2
		
		if(collidingNorth && (box.origin.x > collisionBox.origin.x - collisionBox.width)) {
			if(box.origin.x < collisionBox.origin.x + collisionBox.width) {
				distanceY = ((collisionBox.origin.y - collisionBox.halfHeight) - (box.origin.y + box.halfHeight));
				side = 1;
			}
		}
		
		if(collidingSouth && (box.origin.x > collisionBox.origin.x - collisionBox.width)) {
			if(box.origin.x < collisionBox.origin.x + collisionBox.width) {
				distanceY = ((box.origin.y - box.halfHeight) - (collisionBox.origin.y + collisionBox.halfHeight));
				side = 2;
			}
		}
		
		if(collidingEast && (box.origin.y > collisionBox.origin.y - collisionBox.height)) {
			if(box.origin.y < collisionBox.origin.y + collisionBox.height) {
				distanceX = ((box.origin.x + box.halfWidth) - (collisionBox.origin.x - collisionBox.halfWidth));
				if(Math.abs(distanceX) < Math.abs(distanceY)) side = 3;
			}
		}
		
		if(collidingWest && (box.origin.y > collisionBox.origin.y - collisionBox.height)) {
			if(box.origin.y < collisionBox.origin.y + collisionBox.height) {
				distanceX = ((box.origin.x - box.halfWidth) - (collisionBox.origin.x + collisionBox.halfWidth));
				if(Math.abs(distanceX) < Math.abs(distanceY)) side = 4;
			}
		}
		
		return new Collision(Math.abs(distanceX), Math.abs(distanceY), side);
	}

	public float getDistanceX() {
		return distanceX;
	}

	public float getDistanceY() {
		return distanceY;
	}

	public int getSide() {
		return side;
	}
	
	@Override
	public String toString() {
		String sideName = "";
		
		switch(side) {
		case 0: sideName = "NONE"; break;
		case 1: sideName = "North"; break;
		case 2: sideName = "South"; break;
		case 3: sideName = "East"; break;
		case 4: sideName = "West"; break;
		}
		
		return "Collision " + sideName + "(" + side + ")" + " by (" + distanceX + ", " + distanceY + ")";
	}
	
}
