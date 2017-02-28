package game;

import nebulous.Game;
import nebulous.object.Level;

public class EntityTestGame extends Game {
	
	public static void main(String[] args) {
		new EntityTestGame();
	}

	@Override
	public void preInit() {
		
	}

	@Override
	public void init() {
		Level test = new TestLevel();
		test.load();
	}

	@Override
	public void update(Game game, double delta) {
		
	}

}
