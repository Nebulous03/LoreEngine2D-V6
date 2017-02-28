package nebulous.object;

import nebulous.Game;
import nebulous.entity.Entity;
import nebulous.entity.Instance;

public abstract class Level extends Instance {

	public abstract void init(Game game);
	public abstract void update(Game game, float delta);
	public abstract void onLoad();
	public abstract void onUnload();
	
	public void add(Entity entity) {	
		getEntitySystem().addEntity(entity);
	}
}
