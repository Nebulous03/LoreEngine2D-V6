package nebulous.object;

import org.joml.Vector2f;
import org.joml.Vector3f;

import nebulous.Game;
import nebulous.graphics.Camera;
import nebulous.graphics.GameWindow;
import nebulous.graphics.primatives.Mesh;
import nebulous.graphics.primatives.Texture;
import nebulous.graphics.shaders.DefaultShader;
import nebulous.graphics.shaders.Shader;
import nebulous.utils.PositionHelper;

public abstract class GuiElement {
	
	public static Camera camera = new Camera(new Vector3f(0,0,1), new Vector3f(0)).setPerspective(Camera.ORTHOGRAPHIC);
	protected Mesh mesh;
	
	protected Vector2f position;
	protected Vector2f rotation;
	protected Vector2f scale;
	
	protected int pixelPosX   = 0;
	protected int pixelPosY   = 0;
	
	protected Texture texture = null;
	protected Shader  shader  = null;
	
	protected boolean preserveEdges = false;
	
	public GuiElement(Texture texture, int x, int y, float size, boolean preserveEdges) {
		this(texture, x, y, 0, 0, size, size, preserveEdges);
	}
	
	public GuiElement(Texture texture, int x, int y, float width, float height, boolean preserveEdges) {
		this(texture, x, y, 0, 0, width, height, preserveEdges);
	}
	
	public GuiElement(Texture texture, int x, int y, float rotX, float rotY, float width, float height, boolean preserveEdges) {
		this.mesh = Mesh.PLANE_GUI(texture);
		this.shader = new DefaultShader();
		this.texture = texture;
		this.pixelPosX = x;
		this.pixelPosY = y;
		this.position = new Vector2f(0);
		this.rotation = new Vector2f(rotX, rotY);
		this.scale = new Vector2f(width, height);
		this.preserveEdges = preserveEdges;
	}
	
	public abstract void init(Game game);
	public abstract void update(Game game, double delta);
	public abstract void onMouseOver();
	
	public void initGuiComponent(Game game) {
		position = PositionHelper.toWorldSpace2D(game.getWindow(), camera, pixelPosX, pixelPosY);
		position.x += scale.x;
		position.y -= scale.y;
	}
	
	Vector2f mousePos = null;
	
	public void updateGuiComponent(Game game, double delta) {
		
		if(preserveEdges) {
			position = PositionHelper.toWorldSpace2D(game.getWindow(), camera, pixelPosX, pixelPosY);
			position.x += scale.x;
			position.y -= scale.y;
		}
		
		mousePos = PositionHelper.getMouseWorldPos(game.getWindow(), camera);
		if(mousePos.x > position.x - scale.x && mousePos.y < position.y + scale.y){
			if(mousePos.x < position.x + scale.x && mousePos.y > position.y - scale.y){
				onMouseOver();
			}
		}
		
	}
	
	public void render(GameWindow window, Camera camera, Shader shader) {
		
		shader.bind();
		
		shader.updateUniforms();
		
		shader.setUniform("projectionMatrix", camera.calculateProjectionMatrix(window));
		shader.setUniform("viewMatrix", camera.calculateViewMatrix(window));
		shader.setUniform("modelMatrix", camera.getModelViewMatrix(this));
		
		mesh.renderMesh();
		
	    shader.unbind();
		
	}
	public Vector2f getPosition() {
		return position;
	}
	public Vector2f getRotation() {
		return rotation;
	}
	public Texture getTexture() {
		return texture;
	}

	public Vector2f getScale() {
		return scale;
	}

	public Shader getShader() {
		return shader;
	}

	public static Camera getCamera() {
		return camera;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
		this.mesh.setTexture(texture);
	}

}
