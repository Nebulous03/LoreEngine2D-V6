package nebulous.component;

import java.util.ArrayList;
import java.util.HashMap;

import nebulous.Game;
import nebulous.graphics.Camera;
import nebulous.graphics.RenderEngine;

public abstract class Level2D {
	
	protected Camera camera	= null;
	protected ArrayList<GameObject2D> worldElements;
	protected ArrayList<GuiElement> guiElements;
	protected HashMap<String, TileMap> tileMapHash;
	protected HashMap<String, Entity2D> entityHash;
	protected HashMap<String, GameObject2D> objectHash;
	protected HashMap<String, GuiElement> guiElementHash;
	protected ArrayList<TileMap> tileMapList;
	protected ArrayList<GameObject2D> objectList;
	protected ArrayList<Entity2D> entityList;
	
	public Level2D() {
		this(new Camera());
	}
	
	public Level2D(Camera camera) {
		this.worldElements = new ArrayList<GameObject2D>();
		this.guiElements = new ArrayList<GuiElement>();
		this.tileMapHash = new HashMap<String, TileMap>();
		this.entityHash = new HashMap<String, Entity2D>();
		this.objectHash = new HashMap<String, GameObject2D>();
		this.guiElementHash = new HashMap<String, GuiElement>();
		this.tileMapList = new ArrayList<TileMap>();
		this.entityList = new ArrayList<Entity2D>();
		this.objectList = new ArrayList<GameObject2D>();
		this.camera = camera;
	}
	
	public abstract void init(Game game);
	
	public abstract void update(Game game, double delta);
	
	public void initLevel(Game game) {
		for(Entity2D entity : entityList) {
			entity.init();
		}
		
		for(GuiElement element : guiElements) {
			element.initGuiElement(game);
			element.init(game);
		}
	}
	
	public void updateLevel(Game game, double delta) {
		for(int i = 0; i < entityList.size(); i++) {
			entityList.get(i).update(game, delta);
		}
		
		for(int i = 0; i < guiElements.size(); i++) {
			guiElements.get(i).updateGuiElement(game, delta);
			guiElements.get(i).update(game, delta);
		}
	}
	
	public void render(RenderEngine renderer) {
		for(int i = 0; i < worldElements.size(); i++) {
			renderer.render(camera, worldElements.get(i));
		}
		
		for(int i = 0; i < guiElements.size(); i++) {
			renderer.render(camera, guiElements.get(i));
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
	
	public ArrayList<GuiElement> getGuiElements() {
		return guiElements;
	}
	
	public void addTileMap(String tag, TileMap map) {
		tileMapHash.put(tag, map);
		worldElements.add(map);
		tileMapList.add(map);
	}
	
	public void removeTileMap(String tag) {
		tileMapList.remove(objectHash.get(tag));
		worldElements.remove(objectHash.get(tag));
		tileMapHash.remove(tag);
	}

	public void addEntity(String tag, Entity2D entity) {
		entityHash.put(tag, entity);
		worldElements.add(entity);
		entityList.add(entity);
	}
	
	public void removeEntity(String tag) {
		entityList.remove(objectHash.get(tag));
		worldElements.remove(objectHash.get(tag));
		entityHash.remove(tag);
	}
	
	public void addObject(String tag, GameObject2D object) {
		objectHash.put(tag, object);
		worldElements.add(object);
		objectList.add(object);
	}
	
	public void removeObject(String tag) {
		objectList.remove(objectHash.get(tag));
		worldElements.remove(objectHash.get(tag));
		objectHash.remove(tag);
	}
	
	public void addGuiElement(String tag, GuiElement element) {
		guiElementHash.put(tag, element);
		guiElements.add(element);
	}
	
	public void removeGuiElement(String tag) {
		guiElements.remove(guiElementHash.get(tag));
		guiElementHash.remove(tag);
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
	
	public GuiElement getGuiElement(String tag) {
		return guiElementHash.get(tag);
	}

	public Camera getCamera() {
		return camera;
	}

}
