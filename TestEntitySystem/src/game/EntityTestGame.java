package game;

import nebulous.Game;
import nebulous.object.Level;
import nebulous.utils.Console;

public class EntityTestGame extends Game {
	
	public static void main(String[] args) {
		Console.enablePrefix(true);
		Console.enableTimestamp(true);
		new EntityTestGame();
	}

	@Override
	public void preInit() {
		
	}

	@Override
	public void init() {
		Level test = new TestLevel();
		addLevel("test", test);
		loadLevel("test");
	}

	@Override
	public void update(Game game, double delta) {
		
	}

}
