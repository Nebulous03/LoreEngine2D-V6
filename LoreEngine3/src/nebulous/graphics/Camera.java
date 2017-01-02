package nebulous.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import nebulous.component.GameObject2D;

public class Camera {
	
	public static final int ORTHOGRAPHIC = 0;
	public static final int PERSPECTIVE  = 1;
	
	private static final float FOV 		= (float) Math.toRadians(60.0f);
    private static final float Z_NEAR 	= 0.01f;
    private static final float Z_FAR 	= 1000.f;
    
    private float aspectRatio = 0;
	
	private Matrix4f projectionMatrix = null;
	private Matrix4f modelMatrix      = null;
	private Matrix4f viewMatrix 	  = null;

	private int perspective = 0;
	
	private Vector3f position = null;
	private Vector3f rotation = null;
	
	public Camera() {
		projectionMatrix = new Matrix4f();
		modelMatrix = new Matrix4f();
		viewMatrix = new Matrix4f();
		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
	}
	
	public Camera(Vector3f pos, Vector3f rot) {
		projectionMatrix = new Matrix4f();
		modelMatrix = new Matrix4f();
		viewMatrix = new Matrix4f();
		position = pos;
		rotation = rot;
	}
	
	public Matrix4f calculateProjectionMatrix(GameWindow window) {
		
		if(perspective == ORTHOGRAPHIC) {
			projectionMatrix.identity().ortho2D(-((float)window.getWidth() / window.getHeight()), (float)window.getWidth() / window.getHeight(), -1, 1);
			return projectionMatrix;
		} else {
			aspectRatio = (float) window.getWidth() / window.getHeight();
			projectionMatrix.identity().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
			return projectionMatrix;
		}
		
	}
	
	Vector3f vecRotation;
	
	public Matrix4f getModelViewMatrix(GameObject2D object) {	// Add support for 3D
		
		vecRotation = object.getRotation();
		modelMatrix.identity().translate(object.getPosition().x, object.getPosition().y, 0).
	        rotateX((float)Math.toRadians(-vecRotation.x)).
	        rotateY((float)Math.toRadians(-vecRotation.y)).
	        rotateZ((float)Math.toRadians(-vecRotation.z)).
	        scale(object.getScale());
		return modelMatrix;
		
	}
	
	Vector3f vec1X = new Vector3f(1,0,0);
	Vector3f vec1Y = new Vector3f(0,1,0);
	Vector3f vec1Z = new Vector3f(0,1,0);
	
	public Matrix4f calculateViewMatrix(GameWindow window) {
		
		viewMatrix.identity().rotate((float)Math.toRadians(rotation.x), vec1X)
        .rotate((float)Math.toRadians(rotation.y), vec1Y);
		viewMatrix.translate(-position.x, -position.y, -position.z);
		return viewMatrix;
		
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
	
	public void setPerspective(int perspective) {
		this.perspective = perspective;
	}
	
}
