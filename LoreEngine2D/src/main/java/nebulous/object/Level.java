package nebulous.object;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.joml.Vector3f;

import nebulous.Game;
import nebulous.entity.Component;
import nebulous.entity.Entity;
import nebulous.graphics.Camera;
import nebulous.graphics.component.Render;
import nebulous.logic.Instance;
import nebulous.logic.component.Update;

public abstract class Level extends Instance {

	protected Camera camera	= null;
	
	protected HashMap<String, Entity> tags = null;
	
	public abstract void init(Game game);
	public abstract void update(Game game, double delta);
	public abstract void onLoad();
	public abstract void onUnload();
	
	public Level() {
		camera = new Camera(new Vector3f(0f,0f,1f), new Vector3f(0));
		tags = new HashMap<String, Entity>();
	}
	
	public void initAll(Game game) {
		super.load(game);
		this.init(game);
		this.onLoad();
		entitySystem.initAllComponents(game);
	}
	
	public void updateAll(Game game, double delta) {
		update(game, delta);
		LinkedHashMap<Long, Component> hash = entitySystem.getComponentHash(Update.class);
		if(hash != null) {
			for(Component u : hash.values()) {
				((Update)u).update(game, delta);
			}
		}
	}
	
	public void renderAll() {
		LinkedHashMap<Long, Component> hash = entitySystem.getComponentHash(Render.class);
		if(hash != null) {
			for(Component r : hash.values()) {
				((Render)r).render();
			}
		}
	}
	
	public Level add(String tag, Entity entity) {
		super.add(entity);
		this.tags.put(tag, entity);
		return this;
	}
	
	public Level remove(String tag) {
		super.remove(tags.get(tag));
		this.tags.remove(tag);
		return this;
	}
	
	public Entity get(String tag) {
		return tags.get(tag);
	}
	
	public Level setCamera(Camera camera) {
		this.camera = camera;
		return this;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public HashMap<String, Entity> getTags() {
		return tags;
	}
}
