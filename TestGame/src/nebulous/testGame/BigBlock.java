package nebulous.testGame;

import org.joml.Vector3f;

import nebulous.Game;
import nebulous.graphics.primatives.Mesh;
import nebulous.object.Entity2D;
import nebulous.physics.BoundingBox2D;

public class BigBlock extends Entity2D{

	public BigBlock(Mesh mesh, float x, float y) {
		super(mesh, x, y);
	}

	@Override
	public void init() {
		boundingBox = new BoundingBox2D(2, 2, position);
		scale = new Vector3f(2);
	}

	@Override
	public void update(Game game, double delta) {
		
	}

}
