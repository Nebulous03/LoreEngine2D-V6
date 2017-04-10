package nebulous.testGame;

import nebulous.Game;
import nebulous.graphics.Window;
import nebulous.logic.Input;

public class TestGame extends Game {
	
	@Override
	public void preInit() {
		window.setDisplayMode(Window.DisplayMode.WINDOWED);
//		window.setDisplayMode(Window.DisplayMode.FULLSCREEN);
//		window.enableVSync(true);
		window.setSize(640, 480);
//		window.setSize(1920, 1080);
		setTickrate(60);
//		capFrames(60);
	}
	
	@Override
	public void init() {
		window.printGLStats();
		
		add("testLevel", new TestLevel());
		add("testLevel2", new TestLevel2());
		add("testLevel3", new TestLevel3());
		add("menu", new MenuInstance());
		
		load("testLevel");
	}
	
	@Override
	public void postInit() {
		
	}
	
	int cooldown = 0;
	boolean swap = false;
	
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
		
		if(Input.isKeyPressed(Input.KEY_3)) {
			cooldown += 20000;
			unloadActiveLevel();
			load("testLevel3");
		}
		
		if(Input.isKeyPressed(Input.KEY_ESCAPE)) {
			cooldown += 20000;
			unloadActiveLevel();
			load("menu");
		}
		
	}

	public static void main(String[] args) {
		new TestGame().start();
	}
	
}
