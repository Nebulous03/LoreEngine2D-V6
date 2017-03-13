package nebulous.graphics.component;

import nebulous.Game;
import nebulous.entity.Component;
import nebulous.graphics.Renderer;

public class Render extends Component {
	
	public Renderer renderer;
	
	public Render(Renderer renderer) {
		this.renderer = renderer;
	}
	
	@Override
	public void init(Game game) {
		renderer.init(game);	//TODO: create a renderer library to handle this stuff
	}
	
	public void render() {
		renderer.render(parent);
	}
	
}
