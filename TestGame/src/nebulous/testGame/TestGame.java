package nebulous.testGame;

import nebulous.Game;
import nebulous.graphics.GameWindow;
import nebulous.logic.Input;

public class TestGame extends Game{
	
	@Override
	public void preInit() {
		window.setDisplayMode(GameWindow.DISPLAY_MODE_WINDOWED);
		window.enableVSync(false);
//		window.setSize(1920, 1080);
		window.setSize(640, 480);
//		window.setSize(640, 640);
	}
	
	@Override
	public void init() {
		window.printGLStats();
		
		addLevel("testLevel", new TestLevel());
		addLevel("testLevel2", new TestLevel2());
		loadLevel("testLevel");
	}
	
	int cooldown = 0;
	
	@Override
	public void update(Game game, double delta) {
		
		if(cooldown > 0) cooldown--;
		
		if(Input.isKeyPressed(Input.KEY_1)) {
			cooldown += 20000;
			unloadLevel();
			loadLevel("testLevel");
		}
		
		if(Input.isKeyPressed(Input.KEY_2)) {
			cooldown += 20000;
			unloadLevel();
			loadLevel("testLevel2");
		}
		
		if(Input.isKeyPressed(Input.KEY_ESCAPE)) {
			game.stop();
		}
		
	}

	public static void main(String[] args) {
		new TestGame();
	}
	
	
}
