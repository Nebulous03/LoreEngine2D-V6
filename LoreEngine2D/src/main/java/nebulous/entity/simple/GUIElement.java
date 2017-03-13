package nebulous.entity.simple;

import nebulous.entity.Entity;
import nebulous.graphics.component.Mesh;
import nebulous.graphics.component.Render;
import nebulous.graphics.component.Texture;
import nebulous.graphics.component.Transform;
import nebulous.graphics.renderers.GUIRenderer;

public class GUIElement extends Entity {
	
	public GUIElement(float x, float y) {
		add(new Transform(x, y));
		add(Mesh.PLANE());
		add(new Texture(Texture.UNKNOWN));
		add(new Render(GUIRenderer.instance()));
	}
	
	public GUIElement() {
		this(0,0);
	}
	
	public GUIElement setPosition(float x, float y) {
		((Transform)getComponent(Transform.class)).position.x = x;
		((Transform)getComponent(Transform.class)).position.y = y;
		return this;
	}
	
	public GUIElement setSize(float width, float height) {
		//TODO: DO!
		return null;
	}
	
	public GUIElement scale(float scaleX, float scaleY) {
		((Transform)getComponent(Transform.class)).scale.x = scaleX;
		((Transform)getComponent(Transform.class)).scale.y = scaleY;
		return this;
	}

}
