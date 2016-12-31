package nebulous.game;

import org.joml.Vector3f;

import nebulous.graphics.Camera;
import nebulous.graphics.GameWindow;
import nebulous.graphics.Mesh;
import nebulous.graphics.Texture;
import nebulous.graphics.shaders.Shader;

public class GameObject {
	
	protected Mesh    mesh 		   = null;
	protected Texture texure 	   = null;
	protected Shader  shader	   = null;
	
	protected Vector3f position  = null;
	protected Vector3f rotation  = null;
	protected Vector3f scale	 = null;
	
	protected GameObject() {}
	
	public GameObject(Mesh mesh) {
		this.mesh = mesh;
		this.texure = mesh.texture;
		this.position = new Vector3f(0);
		this.rotation = new Vector3f(0);
		this.scale = new Vector3f(1);
	}
	
	public GameObject(Mesh mesh, Shader shader) {
		this.mesh = mesh;
		this.texure = mesh.texture;
		this.shader = shader;
		this.position = new Vector3f(0);
		this.rotation = new Vector3f(0);
		this.scale = new Vector3f(1);
	}
	
	public void render(GameWindow window, Camera camera, Shader shader){
		
		// BIND SHADER
		shader.bind();
		
		// CALCULATE PROJECTION
		shader.setUniform("projectionMatrix", camera.calculateProjectionMatrix(window));
		shader.setUniform("viewMatrix", camera.calculateViewMatrix(window));
		
		// UPDATE UNIFORMS
		shader.updateUniforms();
		
		// RENDER OBJECT
		shader.setUniform("modelMatrix", camera.getModelViewMatrix(this));
		mesh.renderMesh();
		
		// UNBIND SHADER
	    shader.unbind();
		
	}
	
	public void setPosition(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
	}
	
	public void move(float offsetX, float offsetY, float offsetZ) {
		position.x += offsetX;
		position.y += offsetY;
		position.z += offsetZ;
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
		return texure;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
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
