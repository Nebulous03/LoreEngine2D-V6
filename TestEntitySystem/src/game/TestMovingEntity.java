package game;

import nebulous.Game;
import nebulous.entity.simple.EntityMovable;

public class TestMovingEntity extends EntityMovable {
	
	float speed = 0.1f;
	
	public TestMovingEntity(float x, float y) {
		super(x, y);
	}
	
	@Override
	public void update(Game game, double delta) {
		
		move((float)(speed), 0);
		
		if(getX() > 6){
			setPosition(6, getY());
			speed *= -1;
		}
		
		if(getX() < -6){
			setPosition(-6, getY());
			speed *= -1;
		}
		
	}

	public TestMovingEntity reverse() {
		speed *= -1;
		return this;
	}

}
