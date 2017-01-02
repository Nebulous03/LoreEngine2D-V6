package nebulous.physics;

public class Collision2D {
	
	public static final int SIDE_NONE  = 0;
	public static final int SIDE_NORTH = 1;
	public static final int SIDE_SOUTH = 2;
	public static final int SIDE_EAST  = 3;
	public static final int SIDE_WEST  = 4;
	
	private float distanceX = 0;
	private float distanceY = 0;
	
	private int side = 0;
	
	public Collision2D(float distanceX, float distanceY, int side) {
		this.distanceX = distanceX;
		this.distanceY = distanceY;
		this.side = side;
	}
	
	static boolean collidingNorth = false;
	static boolean collidingSouth = false;
	static boolean collidingEast = false;
	static boolean collidingWest = false;
	
	public static Collision2D getCollision(BoundingBox2D first, BoundingBox2D second) {
		
		float distanceX = 0;
		float distanceY = 0;
		int   side = 0;
		
		if(first == null || second == null) return new Collision2D(0, 0, 0);
		
		collidingNorth = ((first.origin.y + first.halfHeight) >= (second.origin.y - second.halfHeight))		// TOP1    > BOTTOM2
				      && ((first.origin.y - first.halfHeight) <= (second.origin.y - second.halfHeight));	// BOTTOM1 < BOTTOM2
		
		collidingSouth = ((first.origin.y - first.halfHeight) <= (second.origin.y + second.halfHeight))		// BOTTOM1 < TOP2
					  && ((first.origin.y + first.halfHeight) >= (second.origin.y + second.halfHeight));	// TOP1    > TOP2
		
		collidingEast =  ((first.origin.x + first.halfWidth)  >= (second.origin.x - second.halfWidth))		// EAST1   > WEST2
					  && ((first.origin.x + first.halfWidth)  <= (second.origin.x + second.halfWidth));		// WEST1   < WEST2
		
		collidingWest =  ((first.origin.x - first.halfWidth)  <= (second.origin.x + second.halfWidth))		// WEST1   < EAST2
					  && ((first.origin.x + first.halfWidth)  >= (second.origin.x + second.halfWidth));		// EAST1   > EAST2
		
		if(collidingNorth && (first.origin.x > second.origin.x - second.width)) {
			if(first.origin.x < second.origin.x + second.width) {
				distanceY = ((second.origin.y - second.halfHeight) - (first.origin.y + first.halfHeight));
				side = 1;
			}
		}
		
		if(collidingSouth && (first.origin.x > second.origin.x - second.width)) {
			if(first.origin.x < second.origin.x + second.width) {
				distanceY = ((first.origin.y - first.halfHeight) - (second.origin.y + second.halfHeight));
				side = 2;
			}
		}
		
		if(collidingEast && (first.origin.y > second.origin.y - second.height)) {
			if(first.origin.y < second.origin.y + second.height) {
				distanceX = ((first.origin.x + first.halfWidth) - (second.origin.x - second.halfWidth));
				if(Math.abs(distanceX) < Math.abs(distanceY)) side = 3;
			}
		}
		
		if(collidingWest && (first.origin.y > second.origin.y - second.height)) {
			if(first.origin.y < second.origin.y + second.height) {
				distanceX = ((first.origin.x - first.halfWidth) - (second.origin.x + second.halfWidth));
				if(Math.abs(distanceX) < Math.abs(distanceY)) side = 4;
			}
		}
		
		return new Collision2D(Math.abs(distanceX), Math.abs(distanceY), side);
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
