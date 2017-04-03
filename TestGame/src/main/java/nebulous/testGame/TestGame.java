package nebulous.testGame;

import nebulous.Game;
import nebulous.graphics.Window.DisplayMode;
import nebulous.logic.Input;

public class TestGame extends Game {
	
	@Override
	public void preInit() {
		window.setDisplayMode(DisplayMode.WINDOWED).vSync().setSize(640, 480);
		window.vSync(false);
//		window.setSize(640, 480);
//		capFrames(60);
	}
	
	@Override
	public void init() {
		window.printGLStats();
		
		add("testLevel", new TestLevel());
		add("testLevel2", new TestLevel2());
		load("testLevel");
	}
	
	@Override
	public void postInit() {
		
	}
	
	int cooldown = 0;
	
	@Override
	public void update(Game game, double delta) {
		
		if(cooldown > 0) cooldown--;
		
		if(Input.isKeyPressed(Input.KEY_1)) {
			cooldown += 20000;
			unloadActiveLevel();
			load("testLevel");
		}
		
		if(Input.isKeyPressed(Input.KEY_2)) {
			cooldown += 20000;
			unloadActiveLevel();
			load("testLevel2");
		}
		
		if(Input.isKeyPressed(Input.KEY_ESCAPE)) {
			game.stop();
		}
		
	}

	public static void main(String[] args) {
		new TestGame().start();
	}
	
}
