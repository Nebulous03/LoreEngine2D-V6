package nebulous.physics;

import org.joml.Vector2f;

public class BoundingBox2D {

	public float width      = 0;
	public float height     = 0;
	public float halfWidth  = 0;
	public float halfHeight = 0;
	
	public Vector2f origin = null;
	
	public BoundingBox2D(float width, float height, Vector2f origin) {
		this.width = width;
		this.height = height;
		this.halfWidth = width / 2;
		this.halfHeight = height / 2;
		this.origin = origin;
	}
	
}
