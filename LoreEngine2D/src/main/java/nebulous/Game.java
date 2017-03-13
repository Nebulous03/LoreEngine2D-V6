package nebulous;

import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.util.HashMap;

import nebulous.graphics.Window;
import nebulous.logic.Input;
import nebulous.object.Level;
import nebulous.utils.Console;
import nebulous.utils.Time;

/**
 * The Game class is the top of the LoreEngine archetecture.
 * Extend this class and run the start() method to run
 * your game.
 * @author Nebulous
 */

public abstract class Game {
	
	protected Window window 	 			= null;
	protected HashMap<String, Level> levels = null;
	protected Level activeLevel 			= null;
	
	private double TPS = 60.0D;
	private boolean running = false;
	
	/**
	 *  Constructor
	 *  Do not override this method. Use the init methods to
	 *  add pre-game logic.
	 *  @author Nebulous
	 */
	
	public Game() {
		this.window = new Window();
		this.levels = new HashMap<String, Level>();
	}
	
	/**
	 * PreInit();
	 * This method runs as soon as the game starts. You may modify
	 * window / game parameters here. 
	 * Note: GL Context is NOT created at this point.
	 */
	
	public abstract void preInit();
	
	public abstract void init();
	
	public abstract void postInit();
	
	public abstract void update(Game game, double delta);
	
	private void initialize() {
		preInit();
		window.createWindow();
		window.init();
		Input.init(window);
//		AudioManager.init();
		init();
		postInit();
	}
	
	/**
	 * @param delta
	 */
	
	private void update(double delta) {
		updateGame(this, delta);
		Input.update();
		window.update();
	}
	
	private void render() {
		if(activeLevel != null) //TODO: Switch to instance
			renderGame(window);
		window.render();
	}
	
	public void start() {
		
		int frames = 0;
        double frameCounter = 0;
		
		double lastTime = Time.getTime();
		double unprocessedTime = 0;
		
		initialize();
		
		while(!glfwWindowShouldClose(window.getWindow()) && !running){
			
			double startTime = Time.getTime();
            double pastTime = startTime - lastTime;
			lastTime = startTime;
			unprocessedTime += pastTime;
			frameCounter += pastTime;
			
			while(unprocessedTime > (1.0 / TPS)){
				
				unprocessedTime -= (1.0 / TPS);
				
				update(pastTime);
				
				if(frameCounter >= 1.0){
					
					// TODO: REMOVE THIS CODE :
					
					Console.println("Debug", "FPS: " + frames);
					window.setWindowTitle("NebulousGameEngine V6.0 - FPS: " + frames);
					
					// -----------------------
					
					frameCounter = 0;
					frames = 0;
				}
			}
			
			render();
			
			frames++;
			
		}
		
		glfwTerminate();
	}
	
	public void updateGame(Game game, double delta){
		update(game, delta);
		if(activeLevel != null){
			activeLevel.update(game, delta);
			activeLevel.updateAll(game, delta);
		}
	}
	
	public void renderGame(Window window){
		if(activeLevel != null) activeLevel.renderAll();
	}
	
	public void stop() {
		running = true;
	}

	public void setTickrate(double tps) {
		this.TPS = tps;
	}
	
	public double getTickrate() {
		return TPS;
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

	public Window getWindow() {
		return window;
	}

	public HashMap<String, Level> getLevels() {
		return levels;
	}

}
