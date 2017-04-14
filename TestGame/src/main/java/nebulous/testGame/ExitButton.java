package nebulous.testGame;

import nebulous.Game;

public class ExitButton extends GenericButton {
	
	@Override
	public void onMouseClick(Game game) {
		super.onMouseClick(game);
		game.stop();
	}

}
