package game;

import nebulous.Game;
import nebulous.entity.Entity;
import nebulous.object.Level;

public class TestLevel extends Level {
	
	public Entity e0;

	@Override
	public void init(Game game) {
		e0 = new Entity();
	}

	@Override
	public void update(Game game, float delta) {
		
	}

	@Override
	public void onLoad() {
		add(e0);
	}

	@Override
	public void onUnload() {
		
	}

}
