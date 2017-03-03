package game;

import java.util.ArrayList;
import java.util.Random;

import nebulous.Game;
import nebulous.entity.Entity;
import nebulous.graphics.Camera;
import nebulous.object.Level;

public class TestLevel extends Level {
	
	public static final int ENTITY_COUNT = 2600;
	public ArrayList<Entity> temp_entities;
	
	private Entity e0;

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
			if(rand.nextBoolean()) e.reverse();
			temp_entities.add(e);
		}
		
		
	}

	@Override
	public void update(Game game, double delta) {
		
	}

	@Override
	public void onLoad() {
		for(Entity e : temp_entities) add(e);
//		add(e0);
//		entitySystem.printEntity(e0);
	}

	@Override
	public void onUnload() {
		
	}

}
