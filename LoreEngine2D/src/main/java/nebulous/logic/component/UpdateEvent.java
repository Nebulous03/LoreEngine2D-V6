package nebulous.logic.component;

import nebulous.Game;

public abstract class UpdateEvent {

	public abstract void invoke(Game game, double delta);
	
}
