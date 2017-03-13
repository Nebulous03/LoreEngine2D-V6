package nebulous.object;

import java.util.LinkedHashMap;

import org.joml.Vector3f;

import nebulous.Game;
import nebulous.entity.Component;
import nebulous.graphics.Camera;
import nebulous.graphics.component.Render;
import nebulous.logic.Instance;
import nebulous.logic.component.Update;

public abstract class Level extends Instance {

	protected Camera camera	= null;
	
	public abstract void init(Game game);
	public abstract void update(Game game, double delta);
	public abstract void onLoad();
	public abstract void onUnload();
	
	public Level() {
		camera = new Camera(new Vector3f(0f,0f,1f), new Vector3f(0));
		//TODO: Support for different camera?
	}
	
	public void initAll(Game game) {
		super.load(game);
		init(game);
		onLoad();
//		entitySystem.initAllEntities(game);
		entitySystem.initAllComponents(game);
	}
	
	public void updateAll(Game game, double delta) {
		update(game, delta);
		LinkedHashMap<Long, Component> hash = entitySystem.getComponentHash(Update.class);	// TODO: Make an updated list checker
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
	
	public Camera getCamera() {
		return camera;
	}
}
