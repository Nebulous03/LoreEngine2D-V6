package game;

import nebulous.Game;
import nebulous.entity.simple.EntityMovable;

public class TestMovingEntity extends EntityMovable {
	
	float speedX = 0.05f;
	float speedY = 0.05f;
	
	public TestMovingEntity(float x, float y) {
		super(x, y);
	}
	
	@Override
	public void update(Game game, double delta) {
		
		move(speedX, speedY);
		
		if(getX() > 6){
			setPosition(6, getY());
			speedX *= -1;
		}
		
		if(getX() < -6){
			setPosition(-6, getY());
			speedX *= -1;
		}
		
		if(getY() > 5){
			setPosition(getX(), 5);
			speedY *= -1;
		}
		
		if(getY() < -5){
			setPosition(getX(), -5);
			speedY *= -1;
		}
		
	}

	public TestMovingEntity reverseX() {
		speedX *= -1;
		return this;
	}
	
	public TestMovingEntity reverseY() {
		speedY *= -1;
		return this;
	}

}
