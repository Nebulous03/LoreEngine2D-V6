package nebulous.mapEditor;

import nebulous.Game;
import nebulous.component.Entity2D;
import nebulous.graphics.Camera;
import nebulous.graphics.GameWindow;
import nebulous.graphics.primatives.Mesh;
import nebulous.graphics.shaders.Shader;

public class MouseEntity extends Entity2D{

	public MouseEntity(Mesh mesh, float x, float y) {
		super(mesh, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update(Game game, double delta) {
		
	}
	
	@Override
	public void render(GameWindow window, Camera camera, Shader shader) {
		super.render(window, camera, shader);
	}

}
