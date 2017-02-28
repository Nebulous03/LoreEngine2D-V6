package nebulous.testGame;

import org.joml.Vector3f;

import nebulous.Game;
import nebulous.graphics.primatives.Mesh;
import nebulous.object.Entity2D;
import nebulous.physics.BoundingBox2D;

public class HalfBlock extends Entity2D{

	public HalfBlock(Mesh mesh, float x, float y) {
		super(mesh, x, y);
	}

	@Override
	public void init() {
		boundingBox = new BoundingBox2D(1f, 0.5f, position);
		scale = new Vector3f(1f, 0.5f, 0f);
	}

	@Override
	public void update(Game game, double delta) {
		
	}

}
