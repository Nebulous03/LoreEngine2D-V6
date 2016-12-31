package nebulous.logic;

import static org.lwjgl.glfw.GLFW.*;

import nebulous.game.Game;
import nebulous.graphics.Camera;
import nebulous.graphics.GameWindow;
import nebulous.graphics.RenderEngine;
import nebulous.utils.Console;
import nebulous.utils.Time;

public class GameLoop {
	
	private double UPS = 60.0D;
	
	public void start(Game game, GameWindow window, RenderEngine renderer, Camera camera) {
		int frames = 0;
        double frameCounter = 0;
		
		double lastTime = Time.getTime();
		double unprocessedTime = 0;
		
		game.preInit();
		
		window.createWindow();
		renderer.init(window, camera);
		window.init();
		
		Input.init(window);
		
		game.init();
		
		while(!glfwWindowShouldClose(window.getWindow())){
			
			double startTime = Time.getTime();
            double pastTime = startTime - lastTime;
			lastTime = startTime;
			unprocessedTime += pastTime;
			frameCounter += pastTime;
			
			while(unprocessedTime > (1.0 / UPS)){
				unprocessedTime -= (1.0 / UPS);
				
				game.update(pastTime);
				Input.update();
				window.update();
				
				if(frameCounter >= 1.0){
					Console.println("FPS: " + frames);
					window.setWindowTitle("NebulousGameEngine V6.0 - FPS: " + frames);
					frameCounter = 0;
					frames = 0;
				}
			}
			
			game.render(renderer);
			window.render();
			
			frames++;
			
		}
	}

}
