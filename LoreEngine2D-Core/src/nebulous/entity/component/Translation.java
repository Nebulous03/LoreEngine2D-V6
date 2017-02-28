package nebulous.entity.component;

import org.joml.Vector2f;

public class Translation implements Component {

	protected Vector2f position  = null;
	protected Vector2f scale	 = null;
	protected float rotation     = 0;
	
	public Translation() {
		position = new Vector2f(0);
		scale = new Vector2f();
		rotation = 0;
	}

}
