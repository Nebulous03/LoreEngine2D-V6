package nebulous.utils;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import nebulous.component.GameObject2D;
import nebulous.graphics.Camera;
import nebulous.graphics.GameWindow;

public class PositionHelper {
	
	private static float nx = 0;
	private static float ny = 0;
	
	private static Vector4f clipCoords = null;
	private static Matrix4f invertedProjection = null;
	private static Vector4f eyeCoords = null;
	private static Matrix4f invertedViewMatrix = null;
	private static Vector4f worldCoords = null;
	
	public static Vector2f toWorldSpace2D(GameWindow window, Camera camera, int pixelX, int pixelY) {
		return toWorldSpace2D(window, camera, (float)pixelX, (float)pixelY);
	}
	
	public static Vector2f toWorldSpace2D(GameWindow window, Camera camera, double pixelX, double pixelY) {
		return toWorldSpace2D(window, camera, (float)pixelX, (float)pixelY);
	}
	
	public static Vector2f toWorldSpace2D(GameWindow window, Camera camera, float pixelX, float pixelY) {
		
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
	
	public static Vector3f getRay3D(GameWindow window, Camera camera, float pixelX, float pixelY) {
		
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
	
	public static Vector3f toWorldSpace3D(GameObject2D object, GameWindow window, Camera camera, float pixelX, float pixelY, float distance){
		Vector3f ray = getRay3D(window, camera, pixelX, pixelY).mul(distance).add(camera.getPosition());
//		Vector3f pos = itterateRay3D(object, camera.getPosition(), ray, distance, 0);
		
		return ray;
	}
	
//	public static int MAX_COUNT = 12;
//	
//	public static Vector3f itterateRay3D(GameObject2D object, Vector3f startPoint, Vector3f ray, float distance, int count) {
//		float half = distance / 2;
//		if(count >= MAX_COUNT) {
//			Vector3f endPoint = getPointOnRay(ray, half);
//			if()
//		}
//	}
//	
//	public static Vector3f getPointOnRay(Vector3f ray, float distance) {
//		return ray.mul(distance);
//	}
	
	public static Vector2f toPixelSpace(GameWindow window, float glCoordX, float glCoordY) { // TODO: Move these to helper class
		
		float xw = (glCoordX + 1) * (window.getWidth() / 2) + glCoordX;
		float yw = (glCoordY + 1) * (window.getHeight() / 2) + glCoordY;
		
		return new Vector2f(xw, yw);
	}
	

}
