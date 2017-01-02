package nebulous.component;

import java.util.ArrayList;
import java.util.HashMap;

import nebulous.Game;
import nebulous.graphics.Camera;
import nebulous.graphics.RenderEngine;

public abstract class Level2D {
	
	protected Camera camera	= null;
	protected ArrayList<GameObject2D> renderStack;
	protected HashMap<String, TileMap> tileMapHash;
	protected HashMap<String, Entity2D> entityHash;
	protected HashMap<String, GameObject2D> objectHash;
	protected ArrayList<TileMap> tileMapList;
	protected ArrayList<GameObject2D> objectList;
	protected ArrayList<Entity2D> entityList;
	
	public Level2D() {
		this(new Camera());
	}
	
	public Level2D(Camera camera) {
		this.renderStack = new ArrayList<GameObject2D>();
		this.tileMapHash = new HashMap<String, TileMap>();
		this.entityHash = new HashMap<String, Entity2D>();
		this.objectHash = new HashMap<String, GameObject2D>();
		this.tileMapList = new ArrayList<TileMap>();
		this.entityList = new ArrayList<Entity2D>();
		this.objectList = new ArrayList<GameObject2D>();
		this.camera = camera;
	}
	
	public abstract void init();
	
	public abstract void update(Game game, double delta);
	
	public void initLevel() {
		for(Entity2D entity : entityList) {
			entity.init();
		}
	}
	
	public void updateLevel(Game game, double delta) {
		for(int i = 0; i < entityList.size(); i++) {
			entityList.get(i).update(game, delta);
		}
	}
	
	public void render(RenderEngine renderer) {
		for(int i = 0; i < renderStack.size(); i++) {
			renderer.render(camera, renderStack.get(i));
		}
	}
	
	public ArrayList<TileMap> getTileMaps() {
		return tileMapList;
	}
	
	public ArrayList<Entity2D> getEntities() {
		return entityList;
	}
	
	public ArrayList<GameObject2D> getObjects() {
		return objectList;
	}
	
	public void addTileMap(String tag, TileMap map) {
		tileMapHash.put(tag, map);
		renderStack.add(map);
		tileMapList.add(map);
	}
	
	public void removeTileMap(String tag) {
		tileMapList.remove(objectHash.get(tag));
		renderStack.remove(objectHash.get(tag));
		tileMapHash.remove(tag);
	}

	public void addEntity(String tag, Entity2D entity) {
		entityHash.put(tag, entity);
		renderStack.add(entity);
		entityList.add(entity);
	}
	
	public void removeEntity(String tag) {
		entityList.remove(objectHash.get(tag));
		renderStack.remove(objectHash.get(tag));
		entityHash.remove(tag);
	}
	
	public void addObject(String tag, GameObject2D object) {
		objectHash.put(tag, object);
		renderStack.add(object);
		objectList.add(object);
	}
	
	public void removeObject(String tag) {
		objectList.remove(objectHash.get(tag));
		renderStack.remove(objectHash.get(tag));
		objectHash.remove(tag);
	}
	
	public TileMap getTileMap(String tag) {
		return tileMapHash.get(tag);
	}
	
	public Entity2D getEntity(String tag) {
		return entityHash.get(tag);
	}
	
	public GameObject2D getObject(String tag) {
		return objectHash.get(tag);
	}

	public Camera getCamera() {
		return camera;
	}

}
