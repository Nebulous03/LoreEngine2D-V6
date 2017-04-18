package nebulous.testGame;

import nebulous.Game;
import nebulous.addons.entity.simple.EntityMovable;
import nebulous.physics.component.CollisionBox;

public class BlockEntity extends EntityMovable {

	public BlockEntity(float x, float y) {
		super(x,y);
	}

	@Override
	public void update(Game game, double delta) {
		
	}

	public BlockEntity collideable(boolean collidable) {
		((CollisionBox)getComponent(CollisionBox.class)).enabled = collidable;
		return this;
	}

}
