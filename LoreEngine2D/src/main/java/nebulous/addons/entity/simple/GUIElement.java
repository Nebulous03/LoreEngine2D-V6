package nebulous.addons.entity.simple;

import org.joml.Vector2f;

import nebulous.Game;
import nebulous.addons.util.VectorUtils;
import nebulous.entity.Entity;
import nebulous.graphics.Camera;
import nebulous.graphics.Window;
import nebulous.graphics.component.Mesh;
import nebulous.graphics.component.Render;
import nebulous.graphics.component.Texture;
import nebulous.graphics.component.Transform;
import nebulous.graphics.renderers.GUIRenderer;

public class GUIElement extends Entity {
	
	private Snap snap = Snap.NONE;
	
	private int offsetX = 0;
	private int offsetY = 0;
	
	public static enum Snap {
		NORTH, SOUTH, EAST, WEST,
		NORTH_EAST, NORTH_WEST,
		SOUTH_EAST, SOUTH_WEST,
		CENTER, NONE
	}
	
	public GUIElement(Snap snap, int offsetX, int offsetY) {
		this.snap = snap;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		add(new Transform(0,0));
		add(Mesh.PLANE());
		add(new Texture(Texture.UNKNOWN));
		add(new Render(GUIRenderer.instance()));
	}
	
	public GUIElement(float x, float y) {
		add(new Transform(x, y));
		add(Mesh.PLANE());
		add(new Texture(Texture.UNKNOWN));
		add(new Render(GUIRenderer.instance()));
	}
	
	public GUIElement() {
		this(0,0);
	}
	
	@Override
	public void init(Game game) {
		updateGUIPosition(game);
	}
	
	public void updateGUIPosition(Game game) {
		
		int width = game.getWindow().getWidth();
		int height = game.getWindow().getHeight();
		int halfWidth = game.getWindow().getWidth() / 2;
		int halfHeight = game.getWindow().getHeight() / 2;
		
		switch(snap) {
		case NORTH:
			setPixelPosition(halfWidth + offsetX, 0 + offsetY); break;
		case SOUTH:
			setPixelPosition(halfWidth + offsetX, height - offsetY); break;
		case EAST:
			setPixelPosition(width - offsetX, halfHeight + offsetY); break;
		case WEST:
			setPixelPosition(0 + offsetX, halfHeight + offsetY); break;
		case NORTH_EAST:
			setPixelPosition(width - offsetX, 0 + offsetY); break;
		case NORTH_WEST:
			setPixelPosition(0 + offsetX, 0 + offsetY); break;
		case SOUTH_EAST:
			setPixelPosition(width - offsetX, height - offsetY); break;
		case SOUTH_WEST: 
			setPixelPosition(0 + offsetX, height - offsetY); break;
		case CENTER:
			setPixelPosition(halfWidth + offsetX, halfHeight + offsetY); break;
		default:
			break;
		}
		
	}
	
	public GUIElement setPixelPosition(int x, int y) {
		Window window = getInstance().getGame().getWindow();
		Camera camera = GUIRenderer.instance().getCamera();
		Vector2f test = VectorUtils.toWorldSpace2D(window, camera, x, y);
		((Transform)getComponent(Transform.class)).position.x = ((float) test.x);
		((Transform)getComponent(Transform.class)).position.y = ((float) test.y);
		return this;
	}
	
	public GUIElement scale(float scaleX, float scaleY) {
		((Transform)getComponent(Transform.class)).scale.x = scaleX;
		((Transform)getComponent(Transform.class)).scale.y = scaleY;
		return this;
	}

	public Snap getSnap() {
		return snap;
	}

	public void setSnap(Snap snap) {
		this.snap = snap;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}
	
	public GUIElement setTexture(Texture texture) {
		((Texture)getComponent(Texture.class)).set(texture.getTextureID());
		return this;
	}

	public GUIElement setTexture(int texture) {
		((Texture)getComponent(Texture.class)).set(texture);
		return this;
	}

}
