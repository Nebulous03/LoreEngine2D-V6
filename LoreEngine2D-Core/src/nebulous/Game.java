package nebulous;

import java.util.HashMap;

import nebulous.graphics.GameWindow;
import nebulous.logic.GameLoop;
import nebulous.object.Level;

public abstract class Game{
	
	protected GameLoop	   loop	     = null;
	protected GameWindow   window 	 = null;
	
	protected HashMap<String, Level> levels;
	protected Level activeLevel = null;
	
	public Game() {
		this.loop = new GameLoop();
		this.window = new GameWindow();
		this.levels = new HashMap<String, Level>();
		loop.start(this, window);
	}
	
	public abstract void preInit();
	
	public abstract void init();
	
	public abstract void update(Game game, double delta);
	
	public void updateGame(Game game, double delta){
		update(game, delta);
		if(activeLevel != null){
			activeLevel.update(game, delta);
			activeLevel.updateAll(game, delta);
		}
	}
	
	public void renderGame(GameWindow window){
		if(activeLevel != null) activeLevel.renderAll();
	}
	
	public void stop() {
		loop.stop();
	}
	
	public void addLevel(String tag, Level level) {
		levels.put(tag, level);
	}
	
	public Level getLevel(String tag) {
		return levels.get(tag);
	}
	
	public void unloadLevel() {
		activeLevel.onUnload();
		activeLevel = null;
	}
	
	public void loadLevel(String tag) {
		activeLevel = getLevel(tag);
		activeLevel.initAll(this);
	}

	public Level getActiveLevel() {
		return activeLevel;
	}

	public GameWindow getWindow() {
		return window;
	}

	public HashMap<String, Level> getLevels() {
		return levels;
	}

}
