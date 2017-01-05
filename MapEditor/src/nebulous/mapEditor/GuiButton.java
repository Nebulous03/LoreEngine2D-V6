package nebulous.mapEditor;

import nebulous.Game;
import nebulous.component.GuiComponent;
import nebulous.graphics.primatives.Texture;

public class GuiButton extends GuiComponent{
	
	private static final Texture button_blue = new Texture("/textures/button_blue.png");
	private static final Texture button_red = new Texture("/textures/button_red.png");

	public GuiButton(int x, int y, float width, float height) {
		super(button_blue, x, y, width, height, true);
	}

	@Override
	public void init(Game game) {
		
	}

	@Override
	public void update(Game game, double delta) {
		if(texture == button_red) {
			setTexture(button_blue);
		}
	}

	@Override
	public void onMouseOver() {
		setTexture(button_red);
	}

}
