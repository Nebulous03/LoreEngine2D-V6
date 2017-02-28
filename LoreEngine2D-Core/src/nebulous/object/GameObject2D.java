package nebulous.object;

import org.joml.Vector2f;
import org.joml.Vector3f;

import nebulous.graphics.Camera;
import nebulous.graphics.GameWindow;
import nebulous.graphics.primatives.Mesh;
import nebulous.graphics.primatives.Texture;
import nebulous.graphics.shaders.Shader;
import nebulous.physics.BoundingBox2D;

public class GameObject2D {
	
	protected Mesh mesh = null;
	
	public BoundingBox2D boundingBox = null;
	
	protected Vector2f position  = null;
	protected Vector3f rotation  = null;
	protected Vector3f scale	 = null;
	
	protected Shader shader = null;
	
	protected GameObject2D() {}
	
	public GameObject2D(Mesh mesh) {
		this(mesh, 0, 0, null, null);
	}
	
	public GameObject2D(Mesh mesh, float x, float y) {
		this(mesh, x, y, null, null);
	}
	
	public GameObject2D(Mesh mesh, BoundingBox2D box) {
		this(mesh, 0, 0, box, null);
	}
	
	public GameObject2D(Mesh mesh, Shader shader) {
		this(mesh, 0, 0, null, shader);
	}
	
	public GameObject2D(Mesh mesh, float x, float y, BoundingBox2D box) {
		this(mesh, x, y, box, null);
	}
	
	public GameObject2D(Mesh mesh, float x, float y, Shader shader) {
		this(mesh, x, y, null, shader);
	}
	
	public GameObject2D(Mesh mesh, float x, float y, BoundingBox2D box, Shader shader) {
		this.mesh = mesh;
		this.position = new Vector2f(x, y);
		this.rotation = new Vector3f(0);
		this.boundingBox = box;
		this.shader = shader;
		this.scale = new Vector3f(1);
	}
	
	public void render(GameWindow window, Camera camera, Shader shader){
		
		shader.bind();
		
		shader.updateUniforms();

		shader.setUniform("projectionMatrix", camera.calculateProjectionMatrix(window));
		shader.setUniform("viewMatrix", camera.calculateViewMatrix(window));
		shader.setUniform("modelMatrix", camera.getModelViewMatrix(this));
		
		mesh.renderMesh();
		
	    shader.unbind();
		
	}
	
	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
		
		if(boundingBox != null){
			boundingBox.origin.x = x;
			boundingBox.origin.y = y;
		}
	}
	
	public void move(float offsetX, float offsetY) {
		position.x += offsetX;
		position.y += offsetY;
	}
	
	public void setRotation(float rollX, float rollY, float rollZ) {
		rotation.x = rollX;
		rotation.y = rollY;
		rotation.z = rollZ;
	}
	
	public void rotate(float offsetX, float offsetY, float offsetZ) {
		rotation.x += offsetX;
		rotation.y += offsetY;
		rotation.z += offsetZ;
	}
	
	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}

	public Mesh getMesh() {
		return mesh;
	}

	public Texture getTexure() {
		return mesh.getTexture();
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}

}
