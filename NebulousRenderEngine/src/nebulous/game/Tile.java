package nebulous.game;

import nebulous.graphics.Camera;
import nebulous.graphics.GameWindow;
import nebulous.graphics.Mesh;
import nebulous.graphics.Texture;
import nebulous.graphics.shaders.Shader;

public class Tile extends GameObject{	// Make even more lightweight
	
	public static final Tile UNKNOWN_BLUE   = new Tile(Texture.UNKNOWN);
	public static final Tile UNKNOWN_ORANGE = new Tile(Texture.UNKNOWN2);

	public Tile() {}
	
	public Tile(Texture texture) {
		super(Mesh.PLANE(texture));
	}
	
	@Override
	public void render(GameWindow window, Camera camera, Shader shader) {
		shader.setUniform("modelMatrix", camera.getModelViewMatrix(this));
		mesh.renderMesh();
	}
	
}
