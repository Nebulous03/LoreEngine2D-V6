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
 * The Game class is the top of the LoreEngine architecture.
 * Extend this class and run the start() method to run
 * your game.
 * @author Nebulous
 */

public abstract class Game {
	
	protected Window window 	 			= null;
	protected HashMap<String, Level> levels = null;
	protected Level activeLevel 			= null;
	
	private double FPS = 60.0D;
	private double TPS = 60.0D;
	
	private boolean frameCap = false;
	private boolean running  = false;
	private boolean paused   = false;
	
	/**
	 *  Constructor:
	 *  Do not override this method. Use the init methods to
	 *  add pre-game logic.
	 *  @author Nebulous
	 */
	
	public Game() {
		this.window = new Window();
		this.levels = new HashMap<String, Level>();
	}
	
	/**
	 * Runs as soon as the game starts, before OpenGL is
	 * initialized. You may modify window / game parameters here. 
	 * Note: GL Context has NOT been created at this point.
	 * @author Nebulous
	 */
	
	public abstract void preInit();
	
	/**
	 * Runs after the engine and OpenGL is initialized.
	 * Note: GL Context has been created.
	 * @author Nebulous
	 */
	
	public abstract void init();
	
	/**
	 * Runs after all other init methods. Game is about to start.
	 * @author Nebulous
	 */
	
	public abstract void postInit();
	
	/**
	 * Runs every game tick. Do not run level or component update methods through
	 * this method. They are updated internally.
	 * @param game
	 * @param delta
	 * @author Nebulous
	 */
	
	public abstract void update(Game game, double delta);
	
	/**
	 * Internal initialization method.
	 * @author Nebulous
	 */
	
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
	 * Internal update method
	 * @param delta
	 * @author Nebulous
	 */
	
	private void update(double delta) {
		update(this, delta);
		if(!paused) {
			if(activeLevel != null)
			activeLevel.updateAll(this, delta);
			Input.update();
			window.update();
		}
	}
	
	/**
	 * Internal render method
	 * @author Nebulous
	 */
	
	private void render() {
		if(activeLevel != null) activeLevel.renderAll();
		window.render();
	}
	
	/**
	 * Starts the game loop. All update and render functionality is updated
	 * according to TPS and FPS. The loop maybe stopped with the stop() method
	 * or paused with the pause() method.
	 * @author Nebulous
	 */
	
	public void start() {
		
		if(paused) {
			unpause();
			return;
		}
		
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
		
		cleanUp();
		glfwTerminate();
	}
	
	/**
	 * Stops the game loop. 
	 * Call stop() to safely stop the loop and terminate OpenGL before exiting.
	 * @author Nebulous
	 */
	
	public void stop() {
		running = true;
	}
	
	/**
	 * Pauses the game loop. Only the game update method will be called while pause is active.
	 * Use the unpause() method to unpause. Do not use start()
	 * @author Nebulous
	 */
	
	public void pause() {
		paused = true;
	}
	
	/**
	 * Unpauses the game loop.
	 * @author Nebulous
	 */
	
	public void unpause() {
		paused = false;
	}
	
	private void cleanUp() {		//TODO: Do
//		window.cleanUp();
	}
	
	public void capFrames(double frames) {
		this.FPS = frames;
		this.frameCap = true;
	}
	
	public void uncapFrames() {
		this.frameCap = false;
	}
	
	public boolean areFramesCapped() {
		return frameCap;
	}
	
	public double getMaxFrames() {
		return FPS;
	}

	public void setTickrate(double tps) {
		this.TPS = tps;
	}
	
	public double getTickrate() {
		return TPS;
	}
	
	public void add(String tag, Level level) {
		levels.put(tag, level);
	}
	
	public Level getLevel(String tag) {
		return levels.get(tag);
	}
	
	public void unloadActiveLevel() {
		activeLevel.onUnload();
		activeLevel = null;
	}
	
	public void load(String tag) {
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
