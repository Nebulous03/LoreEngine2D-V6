package nebulous.testGame;

import org.joml.Vector3f;

import nebulous.Game;
import nebulous.entity.component.Texture;
import nebulous.graphics.Camera;
import nebulous.logic.Input;
import nebulous.object.Level;
import nebulous.object.TileMap;

public class TestLevel2 extends Level {
	
	private Player player = null;
	private TileMap map   = null;

	@Override
	public void init(Game game) {
		
		camera.setPerspective(Camera.PERSPECTIVE);
		camera.setPosition(new Vector3f(0,0,10f));
		
		player = (Player) new Player(0, 0).setTexture(Texture.UNKNOWN2);
		map = new TileMap(12, 12).populate(Texture.UNKNOWN);
		
		add(map);
		add(player);
	}

	@Override
	public void update(Game game, double delta) {
		controlCamera();
	}
	
	float scrollVelocity = 0;
	
	public void controlCamera() {
		
		if(Input.isKeyHeld(Input.KEY_G)) camera.setPosition(camera.getPosition().add(new Vector3f(0,0,0.1f)));
		if(Input.isKeyHeld(Input.KEY_H)) camera.setPosition(camera.getPosition().add(new Vector3f(0,0,-0.1f)));
		if(Input.isKeyHeld(Input.KEY_E)) camera.setRotation(camera.getRotation().add(new Vector3f(0,0.1f,0)));
		if(Input.isKeyHeld(Input.KEY_R)) camera.setRotation(camera.getRotation().add(new Vector3f(0,-0.1f,0)));
		
		if(Input.isKeyHeld(Input.KEY_P)) camera.setRotation(0,0,0);
		
		if(Input.isMouseScrollIncrease()){ 
			scrollVelocity = -60;
		}
		
		if(Input.isMouseScrollDecrease()){
			scrollVelocity = 60;
		}
		
		if(scrollVelocity > 0) {
			camera.setPosition(camera.getPosition().add(new Vector3f(0, 0, 0.005f * scrollVelocity)));
			scrollVelocity -= 2;
		}
		
		if(scrollVelocity < 0) {
			camera.setPosition(camera.getPosition().add(new Vector3f(0, 0, 0.005f * scrollVelocity)));
			scrollVelocity += 2;
		}
		
	}
	
	@Override
	public void onLoad() {
		
	}

	@Override
	public void onUnload() {
		
	}
}
