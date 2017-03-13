package nebulous.physics.component;

import org.joml.Vector2f;

import nebulous.entity.Component;
import nebulous.graphics.component.Transform;

public class CollisionBox extends Component {
	
	public float width      = 0;
	public float height     = 0;
	public float halfWidth  = 0;
	public float halfHeight = 0;
	
	public boolean enabled = true;
	
	public Vector2f origin = null;
	
	public CollisionBox() {
		origin = new Vector2f(0);
	}
	
	public CollisionBox(float width, float height) {
		this.width = width;
		this.height = height;
		this.halfWidth = width / 2;
		this.halfHeight = height / 2;
	}
	
	public void updateOrigin() {
		this.origin = ((Transform)parent.getComponent(Transform.class)).position;
	}
	
	public CollisionBox set(float width, float height, Vector2f origin) {
		this.width = width;
		this.height = height;
		this.origin = origin;
		return this;
	}
	
	public CollisionBox set(float width, float height) {
		set(width, height, origin);
		return this;
	}
	
	public void enable() {
		enabled = true;
	}
	
	public void disable() {
		enabled = false;
	}
	
	@Override
	public String toString() {
		return "Enabled = " + enabled
				+ "\n\t\tOrigin = " + origin
				+ "\n\t\tWidth = " + width
				+ "\n\t\tHeight = " + height;
	}

}
