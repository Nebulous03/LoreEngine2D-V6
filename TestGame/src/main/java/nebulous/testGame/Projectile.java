package nebulous.testGame;

import org.joml.Vector2f;

import nebulous.Game;
import nebulous.utils.Time;

public class Projectile extends BlockEntity {
	
	private double time = 0;
	private double lifetime = 10000;
	
	private float angle = 0f;
	private float speed = 8f;

	public Projectile(float x, float y, float angle) {
		super(x, y);
		setScale(new Vector2f(0.2f, 0.2f));
		time = Time.getTime();
	}
	
	@Override
	public void update(Game game, double delta) {
		super.update(game, delta);
		if(Time.getTime() > time + lifetime) game.getActiveLevel().remove(this);
		move(-(float)Math.cos(Math.toRadians(angle - 90)) * speed * (float)delta, -(float)Math.sin(Math.toRadians(angle - 90)) * speed * (float)delta);
	}

}
