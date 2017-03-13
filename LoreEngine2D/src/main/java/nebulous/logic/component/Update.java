package nebulous.logic.component;

import nebulous.Game;
import nebulous.entity.Component;

public class Update extends Component {
	
	private UpdateEvent event = null;

	public void update(Game game, double delta) {
		if(event != null) event.invoke(game, delta);
	}
	
	public Update(UpdateEvent event) {
		this.event = event;
	}
	
	public void swapUpdateEvent(UpdateEvent event) {
		this.event = event;
	}

}
