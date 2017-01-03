package nebulous;

import java.util.HashMap;

import nebulous.component.Level2D;
import nebulous.graphics.GameWindow;
import nebulous.graphics.RenderEngine;
import nebulous.logic.GameLoop;

public abstract class Game{
	
	public GameLoop		loop	 = null;
	public GameWindow   window 	 = null;
	public RenderEngine renderer = null;
	
	public HashMap<String, Level2D> levels;
	public Level2D activeLevel = null;
	
	public Game() {
		this.loop = new GameLoop();
		this.window = new GameWindow();
		this.renderer = new RenderEngine();
		this.levels = new HashMap<String, Level2D>();
		loop.start(this, window, renderer);
	}
	
	public abstract void preInit();
	
	public abstract void init();
	
	public abstract void update(Game game, double delta);
	
	public void updateGame(Game game, double delta){
		update(game, delta);
		if(activeLevel != null) activeLevel.update(game, delta);
		if(activeLevel != null) activeLevel.updateLevel(game, delta);
		
	}
	
	public void renderGame(RenderEngine renderer){
		if(activeLevel != null) activeLevel.render(renderer);
	}
	
	public void addLevel(String tag, Level2D level) {
		levels.put(tag, level);
	}
	
	public Level2D getLevel(String tag) {
		return levels.get(tag);
	}
	
	public void unloadLevel() {
		activeLevel = null;
	}
	
	public void loadLevel(String tag) {
		activeLevel = getLevel(tag);
		activeLevel.init(this);
		activeLevel.initLevel(this);
	}

	public Level2D getActiveLevel() {
		return activeLevel;
	}

}
