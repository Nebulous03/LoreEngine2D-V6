package nebulous.testGame;

import org.joml.Vector3f;

import nebulous.Game;
import nebulous.component.Level2D;
import nebulous.component.TileMap;
import nebulous.graphics.Camera;
import nebulous.graphics.primatives.Mesh;
import nebulous.graphics.primatives.Texture;
import nebulous.logic.Input;
import nebulous.physics.BoundingBox2D;

public class TestLevel extends Level2D {

	public TileMap map       = null;
	public TileMap map2      = null;
	public Player player     = null;
	public BlockEntity block = null;
	public BlockEntity block2 = null;
	public BlockEntity block3 = null;
	public BlockEntity block4 = null;
	
	@Override
	public void init() {
		
		camera.setPerspective(Camera.PERSPECTIVE);
		camera.setPosition(new Vector3f(0,0,10f));
		
		Texture STONE = new Texture("/textures/stone.png");
		Texture GRASS = new Texture("/textures/grass_side.png");
		Texture TORCH = new Texture("/textures/torch_on.png");
		
		map = new TileMap(STONE, 32, 32, 24, 14, false);
		map2 = new TileMap(32, 32, 24, 14, true);
		
		for(int i = 0; i < map2.getWidth(); i++) {
			map2.setTile(i, 0, GRASS);
			map2.getTile(i, 0).boundingBox = new BoundingBox2D(1, 1, map2.getTile(i, 0).getPosition());
		}
		
		for(int i = 4; i < 16; i++) {
			map2.setTile(i, 3, GRASS);
			map2.getTile(i, 3).boundingBox = new BoundingBox2D(1, 1, map2.getTile(i, 3).getPosition());
		}
		
		for(int i = 10; i < 16; i++) {
			map2.setTile(i, 5, GRASS);
			map2.getTile(i, 5).boundingBox = new BoundingBox2D(1, 1, map2.getTile(i, 5).getPosition());
		}
		
		for(int i = 17; i < 21; i++) {
			map2.setTile(i, 3, GRASS);
			map2.getTile(i, 3).boundingBox = new BoundingBox2D(1, 1, map2.getTile(i, 3).getPosition());
		}
		
		for(int i = 3; i < 7; i++) {
			map2.setTile(21, i, GRASS);
			map2.getTile(21, i).boundingBox = new BoundingBox2D(1, 1, map2.getTile(21, i).getPosition());
		}
		
		map2.setTile(4, 4, TORCH);
		map2.setTile(18, 5, GRASS);
		map2.setTile(19, 5, GRASS);
		map2.setTile(19, 6, GRASS);
		
		player = new Player(Mesh.PLANE(Texture.UNKNOWN2), 4, 4);
		block = new BlockEntity(Mesh.PLANE(Texture.UNKNOWN), 8, 8);
		block2 = new BlockEntity(Mesh.PLANE(Texture.UNKNOWN), 9, 8);
		block3 = new BlockEntity(Mesh.PLANE(Texture.UNKNOWN), 10, 8);
		block4 = new BlockEntity(Mesh.PLANE(Texture.UNKNOWN), 11, 8);
		
		addTileMap("map1", map);
		addTileMap("map2", map2);
		addEntity("player", player);
		addEntity("block", block);
		addEntity("block2", block2);
		addEntity("block3", block3);
		addEntity("block4", block4);
		
	}

	float scrollVelocity = 0;
	
	@Override
	public void update(Game game, double delta) {
	
		controlCamera();
		
	}
	
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

}
