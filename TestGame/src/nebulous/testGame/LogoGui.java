package nebulous.testGame;

import nebulous.Game;
import nebulous.graphics.primatives.Texture;
import nebulous.object.GuiElement;
import nebulous.utils.PositionHelper;

public class LogoGui extends GuiElement{

	private static Texture LOGO = new Texture("/textures/testImage.png");
	
	public LogoGui(int x, int y, float size) {
		super(LOGO, x, y, size, true);
	}

	@Override
	public void init(Game game) {
		
	}

	@Override
	public void update(Game game, double delta) {
		if(game.getWindow().resized()) {
			position = PositionHelper.toWorldSpace2D(game.getWindow(), camera, 100 , game.getWindow().getHeight() - 100);
		}
	}

	@Override
	public void onMouseOver() {
		
	}

}
