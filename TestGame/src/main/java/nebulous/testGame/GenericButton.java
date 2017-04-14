package nebulous.testGame;

import nebulous.Game;
import nebulous.addons.entity.simple.GUIButton;
import nebulous.graphics.component.Texture;

public class GenericButton extends GUIButton {
	
	public GenericButton() {
		setHoverOffset(0.02f);
	}

	@Override
	public void onMouseHover(Game game) {
		
	}

	@Override
	public void onMouseClick(Game game) {
		System.out.println("CLICK");
		game.stop();
	}

	@Override
	public void onMouseEnter(Game game) {
		setTexture(Texture.UNKNOWN2);
		setScale(0.25f, 0.25f);
	}

	@Override
	public void onMouseExit(Game game) {
		setTexture(Texture.UNKNOWN);
		setScale(0.2f, 0.2f);
	}

}
