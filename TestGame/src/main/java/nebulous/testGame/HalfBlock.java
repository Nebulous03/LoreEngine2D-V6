package nebulous.testGame;

import org.joml.Vector2f;

import nebulous.Game;
import nebulous.addons.entity.simple.EntityMovable;

public class HalfBlock extends EntityMovable {

	public HalfBlock(float x, float y) {
		super(x, y);
	}

	@Override
	public void init(Game game) {
		setCollisionBox(1f, 0.5f);
		setScale(new Vector2f(1f, 0.5f));
	}

	@Override
	public void update(Game game, double delta) {
		
	}

}
