package game;

import java.util.ArrayList;
import java.util.Random;

import org.joml.Vector3f;

import nebulous.Game;
import nebulous.entity.Entity;
import nebulous.entity.component.Texture;
import nebulous.graphics.Camera;
import nebulous.logic.Input;
import nebulous.object.Level;
import nebulous.object.TileMap;

public class TestLevel extends Level {
	
	public static final int ENTITY_COUNT = 2600;
	public ArrayList<Entity> temp_entities;
	
//	private Entity e0;
	private TileMap map;

	@Override
	public void init(Game game) {
		
		camera.setPerspective(Camera.PERSPECTIVE);
		camera.setPosition(0f, 0f, 10f);
		
		temp_entities = new ArrayList<Entity>();
		
		// TESTING ENTITY LOAD
		
//		for(int i = 0; i < ENTITY_COUNT; i++){
//			Entity e = new Entity()
////					.add(new Transform(i, i))
//					.add(new Transform(0, 0))
//					.add(Texture.UNKNOWN2)
//					.add(Mesh.PLANE())
//					.add(new Render(new Renderer(new DefaultShader(), camera, game.getWindow()))) //TODO: fix
//					.add(new Update() {
//						public void update(Game game, double delta) {
//						}
//					});
//			temp_entities.add(e);
//		}
		
		// TESTING MOVING ENTITY
		
//		e0 = new TestMovingEntity(0,0);
		
		Random rand = new Random();
		
		for(int i = 0; i < ENTITY_COUNT; i++) {
			TestMovingEntity e = new TestMovingEntity((6f + 6f) * rand.nextFloat() - 6f, (5f + 5f) * rand.nextFloat() - 5f);
			if(rand.nextBoolean()) e.reverseX();
			if(rand.nextBoolean()) e.reverseY();
			temp_entities.add(e);
		}
		
		map = new TileMap(32, 32).enableCulling().populate(Texture.UNKNOWN);
	}

	float scrollVelocity = 0f;
	
	@Override
	public void update(Game game, double delta) {
		
		if(Input.isKeyHeld(Input.KEY_Q)) camera.rotate(0, -0.1f, 0);
		if(Input.isKeyHeld(Input.KEY_E)) camera.rotate(0, 0.1f, 0);
		if(Input.isKeyHeld(Input.KEY_G)) camera.move(0, 0, 0.1f);
		if(Input.isKeyHeld(Input.KEY_H)) camera.move(0, 0, -0.1f);
		
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
//		Random rand = new Random();
//		for(Entity e : temp_entities){
//			add(e);
//			if(rand.nextBoolean()) ((Texture)e.getComponent(Texture.class)).set(Texture.UNKNOWN2);	//TODO: Fix
//		}
//		add(e0);
//		entitySystem.printEntity(e0);
		
		add(map);
		
	}

	@Override
	public void onUnload() {
		
	}

}
