package nebulous.entity.component.events;

import nebulous.Game;

public abstract class UpdateEvent {

	public abstract void invoke(Game game, double delta);
	
}
