package nebulous.logic;

import nebulous.Game;
import nebulous.entity.Entity;
import nebulous.entity.EntitySystem;

public class Instance {

	protected EntitySystem entitySystem;
	protected Game game;

	public Instance() {
		entitySystem = new EntitySystem();
	}
	
	public void add(Entity entity) {
		entity.setInstance(this);
		entity.init(game);
		getEntitySystem().addEntity(entity, entity.getComponents());
	}
	
	public void remove(Entity entity) {
		getEntitySystem().removeEntity(entity);
	}
	
	public void load(Game game) {
		this.game = game;
		EntitySystem.setInstance(this);
	}
	
	public void unload() {
		EntitySystem.clearAll();
	}
	
	public EntitySystem getEntitySystem() {
		return entitySystem;
	}

	public Game getGame() {
		return game;
	}
	
}
