package nebulous.testGame;

import nebulous.Game;
import nebulous.addons.entity.simple.GUIElement;

public class BackgroundEntity extends GUIElement {
	
	public BackgroundEntity() {
		setTexture(TestLevel.STONE);
		setScale(3f, 3f);
	}
	
	@Override
	public void update(Game game) {
		super.update(game);
		
	}

}
