package nebulous.logic;

import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import nebulous.Game;
import nebulous.graphics.GameWindow;
import nebulous.utils.Console;
import nebulous.utils.Time;

public class GameLoop {
	
	private double UPS = 60.0D;
	private boolean stop = false;
	
	public void start(Game game, GameWindow window) {
		int frames = 0;
        double frameCounter = 0;
		
		double lastTime = Time.getTime();
		double unprocessedTime = 0;
		
		game.preInit();
		
		window.createWindow();
		window.init();
		
		Input.init(window);
		
		game.init();
		
		while(!glfwWindowShouldClose(window.getWindow()) && !stop){
			
			double startTime = Time.getTime();
            double pastTime = startTime - lastTime;
			lastTime = startTime;
			unprocessedTime += pastTime;
			frameCounter += pastTime;
			
			while(unprocessedTime > (1.0 / UPS)){
				unprocessedTime -= (1.0 / UPS);
				
				game.updateGame(game, pastTime);
				Input.update();
				window.update();
				
				if(frameCounter >= 1.0){
					Console.println("Debug", "FPS: " + frames);
					window.setWindowTitle("NebulousGameEngine V6.0 - FPS: " + frames);
					frameCounter = 0;
					frames = 0;
				}
			}
			
			if(game.getActiveLevel() != null) //TODO: Switch to instance
				game.renderGame(window);
			window.render();
			
			frames++;
			
		}
		
		glfwTerminate();
	}

	public void stop() {
		stop = true;
	}

}
