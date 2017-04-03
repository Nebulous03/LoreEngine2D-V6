package nebulous.testGame;

import org.joml.Vector2f;

import nebulous.Game;
import nebulous.addons.entity.simple.EntityMovable;

public class BigBlock extends EntityMovable {

	public BigBlock(float x, float y) {
		super(x, y);
	}

	@Override
	public void init(Game game) {
		setCollisionBox(2, 2);
		setScale(new Vector2f(2));
	}

	@Override
	public void update(Game game, double delta) {
		
	}

}
