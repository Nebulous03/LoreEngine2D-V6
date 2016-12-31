package nebulous.game;

import nebulous.graphics.Camera;
import nebulous.graphics.GameWindow;
import nebulous.graphics.RenderEngine;
import nebulous.logic.GameLoop;

public abstract class Game{
	
	public GameLoop		loop	 = null;
	public GameWindow   window 	 = null;
	public RenderEngine renderer = null;
	public Camera		camera	 = null;
	
	public Game() {
		this.loop = new GameLoop();
		this.window = new GameWindow();
		this.renderer = new RenderEngine();
		this.camera = new Camera();
		loop.start(this, window, renderer, camera);
	}
	
	public abstract void preInit();
	
	public abstract void init();
	
	public abstract void update(double delta);
	
	public abstract void render(RenderEngine renderer);

}
