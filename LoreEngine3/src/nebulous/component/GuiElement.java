package nebulous.component;

import org.joml.Vector3f;

import nebulous.Game;
import nebulous.graphics.Camera;
import nebulous.graphics.GameWindow;
import nebulous.graphics.primatives.Mesh;
import nebulous.graphics.primatives.Texture;
import nebulous.graphics.shaders.Shader;

public abstract class GuiElement extends GameObject2D{
	
	public static Camera customCamera = new Camera(new Vector3f(0,0,1), new Vector3f(0)).setPerspective(Camera.ORTHOGRAPHIC);
	
	private float scaleX = 0;
	private float scaleY = 0;
	
	public abstract void init(Game game);
	public abstract void update(Game game, double delta);
	public abstract void onClick(boolean rightClick);
	public abstract void onMouseover();
	public abstract void onMouseEnter();
	public abstract void onMouseExit();
	
	public GuiElement(float x, float y, float scaleX, float scaleY, Texture texture) {
		super(Mesh.PLANE_GUI(texture), x, y);
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}
	
	public void initGuiElement(Game game) {
		scale = new Vector3f(scaleX, scaleY, 0);
	}
	
	public void updateGuiElement(Game game, double delta) {
		
	}
	
	@Override
	public void render(GameWindow window, Camera camera, Shader shader) {
		
		shader.bind();
		
		shader.updateUniforms();
		
		shader.setUniform("projectionMatrix", customCamera.calculateProjectionMatrix(window));
		shader.setUniform("viewMatrix", customCamera.calculateViewMatrix(window));
		shader.setUniform("modelMatrix", customCamera.getModelViewMatrix(this));
		
		mesh.renderMesh();
		
	    shader.unbind();
		
	}
}
