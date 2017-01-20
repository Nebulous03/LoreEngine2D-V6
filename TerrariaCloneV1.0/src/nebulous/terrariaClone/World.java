package nebulous.terrariaClone;

import org.joml.Vector3f;

import nebulous.Game;
import nebulous.component.GuiElement;
import nebulous.component.GuiElement;
import nebulous.component.Level2D;
import nebulous.component.TileMap;
import nebulous.graphics.Camera;
import nebulous.logic.Input;
import nebulous.utils.Console;

public class World extends Level2D{
	
	private Background bg = null;
	private TileMap map = null;
	private TileMap map2 = null;
	private Player player = null;

	@Override
	public void init(Game game) {
		camera.setPosition(0, 0, 20f);
		
		bg = new Background(game.getWindow().getWidth()/2, game.getWindow().getHeight()/2, 2, 1, Textures.BG);
		map = new TileMap(null, 128, 32, 32, 32, false);
		map2 = new TileMap(null, 128, 32, 48, 32, true);
		
		for(int i = 0; i < map2.getWidth(); i++) {
			for(int j = 0; j < 12; j++) {
				map2.setTile(i, j, Textures.GRASS);
			}
		}
		
		player = new Player(12, 12);
		
		addBackgroundElement("bg", bg);
		addTileMap("layer_1", map);
		addTileMap("layer_2", map2);
		addEntity("player", player);
	}

	@Override
	public void onLoad() {
		Console.println("World", "Loading level...");
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
	public void onUnload() {
		
	}

}
