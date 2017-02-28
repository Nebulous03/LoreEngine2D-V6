package nebulous.testGame;

import nebulous.Game;
import nebulous.graphics.primatives.Mesh;
import nebulous.object.Entity2D;
import nebulous.physics.BoundingBox2D;

public class BlockEntity extends Entity2D{

	public BlockEntity(Mesh mesh, float x, float y) {
		super(mesh, x, y);
	}

	@Override
	public void init() {
		boundingBox = new BoundingBox2D(1, 1, position);
	}

	@Override
	public void update(Game game, double delta) {
		
	}

}
