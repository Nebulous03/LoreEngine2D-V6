package nebulous.testGame;

import nebulous.Game;
import nebulous.component.GuiElement;
import nebulous.graphics.primatives.Texture;

public class LogoGui extends GuiElement{

	private static Texture LOGO = new Texture("/textures/testImage.png");
	
	public LogoGui() {
		super(-0.7f, -0.7f, 0.2f, 0.2f, LOGO);
	}

	@Override
	public void init(Game game) {
		
	}

	@Override
	public void update(Game game, double delta) {
		if(game.window.resized()) {
			System.out.println(game.window.getWidth() + ", " + game.window.getHeight());
		}
	}

	@Override
	public void onClick(boolean rightClick) {
		
	}

	@Override
	public void onMouseover() {
		
	}

	@Override
	public void onMouseEnter() {
		
	}

	@Override
	public void onMouseExit() {
		
	}

}
