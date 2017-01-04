package nebulous.component;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import nebulous.Game;
import nebulous.graphics.Camera;
import nebulous.graphics.GameWindow;
import nebulous.graphics.primatives.Mesh;
import nebulous.graphics.primatives.Texture;
import nebulous.graphics.shaders.Shader;

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
		position = toWorldSpace(game.getWindow(), customCamera, pixelX, pixelY);
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
	
	public Vector2f toPixelSpace(GameWindow window, float glCoordX, float glCoordY) {
		
		float xw = (glCoordX + 1) * (window.getWidth() / 2) + glCoordX;
		float yw = (glCoordY + 1) * (window.getHeight() / 2) + glCoordY;
		
		return new Vector2f(xw, yw);
	}
	
	float nx = 0;
	float ny = 0;
	
	Vector4f clipCoords = null;
	Matrix4f invertedProjection = null;
	Vector4f eyeCoords = null;
	Matrix4f invertedViewMatrix = null;
	Vector4f worldCoords = null;
	
	public Vector2f toWorldSpace(GameWindow window, Camera camera, float pixelX, float pixelY) {
		
		// TO NORMALIZED DEVICE COORDS
		
		nx = (2.0f * pixelX) / (float)window.getWidth() - 1.0f;
		ny = 1.0f - (2.0f * pixelY) / (float)window.getHeight();
		
		// TO HOMOGENEOUS CLIP COORDS
		
		clipCoords = new Vector4f(nx, ny, -1.0f, 1.0f);
		
		// TO EYESPACE COORDS
		
		invertedProjection = camera.calculateProjectionMatrix(window).invertAffine();
		eyeCoords = invertedProjection.transform(clipCoords);
		
		eyeCoords.z = -1f;
		eyeCoords.w = 0f;
		
		// TO WORLD SPACE
		
		invertedViewMatrix = camera.calculateViewMatrix(window).invertAffine();
		worldCoords = invertedViewMatrix.transform(eyeCoords);
		
		return new Vector2f(worldCoords.x, worldCoords.y);
	}
}
