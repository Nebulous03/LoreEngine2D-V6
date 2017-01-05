package nebulous.component;

import org.joml.Vector3f;

import nebulous.Game;
import nebulous.graphics.Camera;
import nebulous.graphics.GameWindow;
import nebulous.graphics.primatives.Mesh;
import nebulous.graphics.primatives.Texture;
import nebulous.graphics.shaders.Shader;
import nebulous.utils.PositionHelper;

public abstract class GuiElement extends GameObject2D{
	
	public static Camera customCamera = new Camera(new Vector3f(0,0,1), new Vector3f(0)).setPerspective(Camera.ORTHOGRAPHIC);
	
	private float pixelX = 0;
	private float pixelY = 0;
	
	private float scaleX = 0;
	private float scaleY = 0;
	
	public abstract void init(Game game);
	public abstract void update(Game game, double delta);
	public abstract void onClick(boolean rightClick);
	public abstract void onMouseover();
	public abstract void onMouseEnter();
	public abstract void onMouseExit();
	
	public GuiElement(float pixelX, float pixelY, float scaleX, float scaleY, Texture texture) {
		super(Mesh.PLANE_GUI(texture), 0, 0);
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.pixelX = pixelX;
		this.pixelY = pixelY;
	}
	
	public void initGuiElement(Game game) {
		scale = new Vector3f(scaleX, scaleY, 0);
		position = PositionHelper.toWorldSpace2D(game.getWindow(), customCamera, pixelX, pixelY);
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
