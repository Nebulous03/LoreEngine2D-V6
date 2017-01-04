package nebulous.testGame;

import nebulous.Game;
import nebulous.component.GuiElement;
import nebulous.graphics.primatives.Texture;

public class LogoGui extends GuiElement{

	private static Texture LOGO = new Texture("/textures/testImage.png");
	
	public LogoGui(int pixelX, int pixelY) {
		super(pixelX, pixelY, 0.2f, 0.2f, LOGO);
	}

	@Override
	public void init(Game game) {
		
	}

	@Override
	public void update(Game game, double delta) {
		if(game.getWindow().resized()) {
			position = toWorldSpace(game.getWindow(), customCamera, 100 , game.getWindow().getHeight() - 100);
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
