package nebulous.testGame;

import nebulous.Game;
import nebulous.addons.entity.simple.GUIElement;
import nebulous.object.Level;

public class MenuInstance extends Level {

	GUIElement exit = null;
	
	@Override
	public void init(Game game) {
		exit = new ExitButton();
	}

	@Override
	public void update(Game game, double delta) {
		
	}

	@Override
	public void onLoad() {
		add(exit);
	}

	@Override
	public void onUnload() {
		remove(exit);
	}
	

}
