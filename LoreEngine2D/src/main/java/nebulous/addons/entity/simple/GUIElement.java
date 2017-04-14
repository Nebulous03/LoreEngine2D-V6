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
import nebulous.logic.component.Update;
import nebulous.logic.component.UpdateEvent;

public class GUIElement extends Entity {
	
	protected Snap snap = Snap.NONE;
	
	protected float offsetX = 0;
	protected float offsetY = 0;
	
	protected float scaleX = 0;
	protected float scaleY = 0;
	
	public static enum Snap {
		NORTH, SOUTH, EAST, WEST,
		NORTH_EAST, NORTH_WEST,
		SOUTH_EAST, SOUTH_WEST,
		CENTER, NONE
	}
	
	public GUIElement(Snap snap, float offsetX, float offsetY) {
		this.snap = snap;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		add(new Transform(0,0));
		add(Mesh.PLANE());
		add(new Texture(Texture.UNKNOWN));
		add(new Render(GUIRenderer.instance()));
		add(new Update(new UpdateEvent() {
			public void invoke(Game game, double delta) { update(game); }
		}));
	}
	
	public GUIElement(float x, float y) {
		this(Snap.CENTER, x, y);
	}
	
	public GUIElement() {
		this(Snap.CENTER, 0,0);
	}
	
	@Override
	public void init(Game game) {
		updatePosition(game.getWindow());
	}
	
	public void update(Game game) {	
		updatePosition(game.getWindow());
	}
	
	public Vector2f getPosition() {
		return ((Transform)getComponent(Transform.class)).position;
	}
	
	Mesh reset = Mesh.PLANE();
	public void updatePosition(Window window) {
		int width = window.getWidth();
		int height = window.getHeight();
		int halfWidth = window.getWidth() / 2;
		int halfHeight = window.getHeight() / 2;
		
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
	
	public GUIElement setPixelPosition(float x, float y) {
		Window window = getInstance().getGame().getWindow();
		Camera camera = GUIRenderer.instance().getCamera();
		Vector2f pos = VectorUtils.toWorldSpace2D(window, camera, x, y);
		((Transform)getComponent(Transform.class)).position.x = ((float) pos.x);
		((Transform)getComponent(Transform.class)).position.y = ((float) pos.y);
		return this;
	}
	
	public GUIElement setScale(float scaleX, float scaleY) {
		((Transform)getComponent(Transform.class)).scale.x = scaleX;
		((Transform)getComponent(Transform.class)).scale.y = scaleY;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		return this;
	}

	public Snap getSnap() {
		return snap;
	}
	
	public GUIElement snap(Snap snap) {
		this.snap = snap;
		return this;
	}

	public float getOffsetX() {
		return offsetX;
	}
	
	public GUIElement offset(float x, float y) {
		offsetX = x;
		offsetY = y;
		return this;
	}

	public GUIElement setOffsetX(float offsetX) {
		this.offsetX = offsetX;
		return this;
	}

	public float getOffsetY() {
		return offsetY;
	}

	public GUIElement setOffsetY(int offsetY) {
		this.offsetY = offsetY;
		return this;
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
