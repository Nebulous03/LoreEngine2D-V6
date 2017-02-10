package nebulous.mapEditor;

import nebulous.Game;
import nebulous.component.GuiElement;
import nebulous.graphics.primatives.Texture;

public class GuiBackground extends GuiElement{
	
	private static final Texture sideBar = new Texture("/textures/sidebar_test.png");;

	public GuiBackground(int x, int y) {
		super(sideBar, x, y, 0.4f, 1, true);
	}

	@Override
	public void init(Game game) {
		
	}

	@Override
	public void update(Game game, double delta) {
		
	}

	@Override
	public void onMouseOver() {
		
	}

}
