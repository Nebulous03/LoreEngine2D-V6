package nebulous.entity.component;

import org.joml.Vector2f;

public class Transform extends Component {

	public Vector2f position = null;
	public Vector2f scale	 = null;
	public float    rotation = 0;
	
	public Transform() {
		position = new Vector2f(0f);
		scale = new Vector2f(1f);
		rotation = 0;
	}
	
	public Transform(float x, float y) {
		position = new Vector2f(x,y);
		scale = new Vector2f(1f);
		rotation = 0;
	}
	
	@Override
	public String toString() {
		return "Position = " + "(" + position.x + ", " + position.y + ")"
				+ "\n\t\tRotation = " + rotation
				+ "\n\t\tScale = " + "(" + scale.x + ", " + scale.y + ")";
	}

}
