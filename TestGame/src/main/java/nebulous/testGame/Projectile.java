package nebulous.testGame;

import org.joml.Vector2f;

import nebulous.Game;
import nebulous.addons.entity.simple.EntityMovable;
import nebulous.addons.util.VectorUtils;
import nebulous.graphics.Camera;
import nebulous.graphics.Window;
import nebulous.physics.Collision;
import nebulous.utils.Time;

public class Projectile extends EntityMovable {
	
	private double time = 0;
	private double lifetime = 2000;
	
	private float angle = 0f;
	private float speed = 8f;
	
	public Projectile(float x, float y, float angle) {
		super(x, y);
		this.setScale(new Vector2f(0.2f, 0.2f));
		this.time = Time.getTime();
		this.angle = angle;
		this.box.setSize(0.2f, 0.2f);
	}
	
	Window window = null;
	Camera camera = null;
	
	Vector2f edge1 = null;
	Vector2f edge2 = null;
	
	Collision[] collisions = null;
	
	@Override
	public void update(Game game, double delta) {
		
		window = game.getWindow();
		camera = game.getActiveLevel().getCamera();
		
		edge1 = VectorUtils.toWorldSpace2D(window, camera, 0, 0).mul(camera.getPosition().z);
		edge2 = VectorUtils.toWorldSpace2D(window, camera, window.getWidth(), window.getHeight()).mul(camera.getPosition().z);
		
		if(Time.getTime() > time + lifetime) game.getActiveLevel().remove(this);
		move(-(float)Math.cos(Math.toRadians(angle - 180)) * speed * (float)delta, -(float)Math.sin(Math.toRadians(angle - 180)) * speed * (float)delta);
		if(getX() > edge2.x || getX() < edge1.x) angle = -angle - 180;
		if(getY() > edge1.y || getY() < edge2.y) angle = -angle;
		
	}

}
