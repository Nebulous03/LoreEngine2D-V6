package example.game;

import nebulous.Game;
import nebulous.entity.Entity;
import nebulous.object.Level;

public class ExampleLevel extends Level {
	
	private Entity example;

	@Override
	public void init(Game game) {
		example = new ExampleEntity();
	}

	@Override
	public void update(Game game, double delta) {
		
	}

	@Override
	public void onLoad() {
		add("example", example);
	}

	@Override
	public void onUnload() {
		
	}

}
