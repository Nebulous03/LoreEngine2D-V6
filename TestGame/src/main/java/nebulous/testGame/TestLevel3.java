package nebulous.testGame;

import org.joml.Vector2f;

import nebulous.Game;
import nebulous.addons.util.VectorUtils;
import nebulous.logic.Input;
import nebulous.object.Level;

public class TestLevel3 extends Level {
	
	public BlockEntity player;

	@Override
	public void init(Game game) {
		camera.setPosition(0,0,10);
		player = new BlockEntity(0, 0);
	}
	
	float speed = 6f;

	Vector2f distance = new Vector2f(0);
	Vector2f mousePos = new Vector2f(0);
	
	@Override
	public void update(Game game, double delta) {
		
		mousePos.x = VectorUtils.toWorldSpace3D(game.getWindow(), camera, (float)Input.mousePosX, (float)Input.mousePosY, camera.getPosition().z).x;
		mousePos.y = VectorUtils.toWorldSpace3D(game.getWindow(), camera, (float)Input.mousePosX, (float)Input.mousePosY, camera.getPosition().z).y;
		
		float angle = (float) Math.toDegrees(Math.atan2(mousePos.y - player.getPosition().y, mousePos.x - player.getPosition().x));
		
		float xDistance = mousePos.x - player.getPosition().x;
		float yDistance = mousePos.y - player.getPosition().y;
		
		float distance = (float)Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));

		player.setRotation(-angle);
		
		if(Input.isKeyHeld(Input.KEY_W) & (distance > 0.1f))
			player.move((float)Math.cos(Math.toRadians(angle)) * speed * (float)delta, (float)Math.sin(Math.toRadians(angle)) * speed * (float)delta);
		if(Input.isKeyHeld(Input.KEY_S))
			player.move(-(float)Math.cos(Math.toRadians(angle)) * speed * (float)delta, -(float)Math.sin(Math.toRadians(angle)) * speed * (float)delta);
		
		if(Input.isKeyHeld(Input.KEY_A))
			player.move(-(float)Math.cos(Math.toRadians(angle + 90)) * speed * (float)delta, -(float)Math.sin(Math.toRadians(angle + 90)) * speed * (float)delta);
		if(Input.isKeyHeld(Input.KEY_D))
			player.move(-(float)Math.cos(Math.toRadians(angle - 90)) * speed * (float)delta, -(float)Math.sin(Math.toRadians(angle - 90)) * speed * (float)delta);
		
		if(Input.isMouseButtonClicked(0))
			shoot(-angle);
			
		
//		System.out.println(distance);
		
		
		// Camera
		
		if(Input.isKeyHeld(Input.KEY_R)) camera.rotate(0, 8f * (float)delta, 0);
		if(Input.isKeyHeld(Input.KEY_E)) camera.rotate(0, -8f * (float)delta, 0);
		
	}
	
	public void shoot(float angle) {
		add(new Projectile(player.getPosition().x, player.getPosition().y, 45));
	}

	@Override
	public void onLoad() {
		add(player);
	}

	@Override
	public void onUnload() {
		remove(player);
	}

}
