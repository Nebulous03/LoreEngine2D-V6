package nebulous.testGame;

import org.joml.Vector3f;

import nebulous.game.Game;
import nebulous.game.GameObject;
import nebulous.game.TileMap;
import nebulous.graphics.Camera;
import nebulous.graphics.GameWindow;
import nebulous.graphics.Mesh;
import nebulous.graphics.RenderEngine;
import nebulous.graphics.Texture;
import nebulous.logic.Input;

public class TestGame extends Game{

	public GameObject player = null;
	public TileMap map = null;
	public TileMap map2 = null;
	
	@Override
	public void preInit() {
		window.setDisplayMode(GameWindow.DISPLAY_MODE_WINDOWED);
		window.enableVSync(true);
//		window.setSize(1920, 1080);
		window.setSize(640, 480);
	}
	
	@Override
	public void init() {
		window.printGLStats();
		camera.setPerspective(Camera.PERSPECTIVE);
		camera.setPosition(new Vector3f(0,0,10f));
		
		Texture STONE = new Texture("/textures/stone.png");
		Texture GRASS = new Texture("/textures/grass_side.png");
		Texture TORCH = new Texture("/textures/torch_on.png");
		
		map = new TileMap(STONE, 16, 32);
		map2 = new TileMap(16, 32);
		
		for(int i = 0; i < map.getWidth(); i++) {
			map.setTile(i, 0, GRASS);
		}
		
		map.setTile(4, 3, GRASS);
		map2.setTile(4, 4, TORCH);
		
		player = new GameObject(Mesh.PLANE(Texture.UNKNOWN2));
	}

	float scrollVelocity = 0;
	
	@Override
	public void update(double delta) {
		
		if(Input.isKeyHeld(Input.KEY_G)) camera.setPosition(camera.getPosition().add(new Vector3f(0,0,0.1f)));
		if(Input.isKeyHeld(Input.KEY_H)) camera.setPosition(camera.getPosition().add(new Vector3f(0,0,-0.1f)));
		if(Input.isKeyHeld(Input.KEY_E)) camera.setRotation(camera.getRotation().add(new Vector3f(0,0.1f,0)));
		if(Input.isKeyHeld(Input.KEY_R)) camera.setRotation(camera.getRotation().add(new Vector3f(0,-0.1f,0)));
		
		if(Input.isKeyHeld(Input.KEY_P)) camera.setRotation(0,0,0);
		
		if(Input.isKeyHeld(Input.KEY_A)) camera.setPosition(camera.getPosition().add(new Vector3f(-0.1f,0,0)));
		if(Input.isKeyHeld(Input.KEY_D)) camera.setPosition(camera.getPosition().add(new Vector3f(0.1f,0,0)));
		if(Input.isKeyHeld(Input.KEY_W)) camera.setPosition(camera.getPosition().add(new Vector3f(0,0.1f,0)));
		if(Input.isKeyHeld(Input.KEY_S)) camera.setPosition(camera.getPosition().add(new Vector3f(0,-0.1f,0)));
		
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
	public void render(RenderEngine renderer) {
		player.setPosition(camera.getPosition().x, camera.getPosition().y, 0);
		
		renderer.render(map);
		renderer.render(map2);
		renderer.render(player);
	}

	public static void main(String[] args) {
		new TestGame();
	}
	
	
}
