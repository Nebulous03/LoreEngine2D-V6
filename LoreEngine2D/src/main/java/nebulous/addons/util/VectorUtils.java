package nebulous.addons.util;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import nebulous.graphics.Camera;
import nebulous.graphics.Window;
import nebulous.logic.Input;

public class VectorUtils {
	
	private static float nx = 0;
	private static float ny = 0;
	
	private static Vector4f clipCoords = new Vector4f(0, 0, 0, 1);
	private static Matrix4f invertedProjection = null;
	private static Vector4f eyeCoords = null;
	private static Matrix4f invertedViewMatrix = null;
	private static Vector4f worldCoords = null;
	
	public static Vector2f toWorldSpace2D(Window window, Camera camera, int pixelX, int pixelY) {
		return toWorldSpace2D(window, camera, (float)pixelX, (float)pixelY);
	}
	
	public static Vector2f toWorldSpace2D(Window window, Camera camera, double pixelX, double pixelY) {
		return toWorldSpace2D(window, camera, (float)pixelX, (float)pixelY);
	}
	
	public static Vector2f toWorldSpace2D(Window window, Camera camera, float pixelX, float pixelY) {
		
		// TO NORMALIZED DEVICE COORDS
		
		nx = (2.0f * pixelX) / (float)window.getWidth() - 1.0f;
		ny = 1.0f - (2.0f * pixelY) / (float)window.getHeight();
		
		// TO HOMOGENEOUS CLIP COORDS
		
		clipCoords.set(nx, ny, -1.0f, 1.0f);
		
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
	
	public static float pixelToWorldDistance2D(Window window, Camera camera, float pixelDistance) {
		return toWorldSpace2D(window, camera, pixelDistance, 0).x;
	}
	
	static float mouseX = 0;
	static float mouseY = 0;
	
	public static Vector2f getMouseWorldPos(Window window, Camera camera) {
		mouseX = (float)Input.mousePosX;
		mouseY = (float)Input.mousePosY;
		return toWorldSpace2D(window, camera, mouseX, mouseY);
	}
	
	public static Vector3f getRay3D(Window window, Camera camera, float pixelX, float pixelY) {
		
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
		
		return new Vector3f(worldCoords.x, worldCoords.y, worldCoords.z);
	}
	
	public static Vector3f toWorldSpace3D(Window window, Camera camera, float pixelX, float pixelY, float distance){
		return getRay3D(window, camera, pixelX, pixelY).mul(distance).add(camera.getPosition());
	}
	
	public static Vector2f toPixelSpace(Window window, float glCoordX, float glCoordY) { // TODO: Move these to helper class
		
		float xw = (glCoordX + 1) * (window.getWidth() / 2) + glCoordX;
		float yw = (glCoordY + 1) * (window.getHeight() / 2) + glCoordY;
		
		return new Vector2f(xw, yw);
	}
	

}
