package nebulous.entity.component;

import nebulous.graphics.Renderer;

public class Render extends Component {
	
	public Renderer renderer;
	
	public Render(Renderer renderer) {
		this.renderer = renderer;
	}
	
	public void render() {
		renderer.render(parent);
	}

}
