package example.game;

import nebulous.Game;
import nebulous.object.Level;

public class ExampleGame extends Game {

	public static void main(String[] args) {
		new ExampleGame().start();
	}

	@Override
	public void preInit() {
		Level exampleLevel = new ExampleLevel();
		add("example_level", exampleLevel);
	}

	@Override
	public void init() {
		load("example_level");
	}

	@Override
	public void postInit() {
		
	}

	@Override
	public void update(Game game, double delta) {
		
	}

}
