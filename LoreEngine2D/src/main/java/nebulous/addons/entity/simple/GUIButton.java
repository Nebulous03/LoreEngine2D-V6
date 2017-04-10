package nebulous.addons.entity.simple;

import org.joml.Vector2f;

import nebulous.Game;
import nebulous.addons.util.VectorUtils;
import nebulous.graphics.Camera;
import nebulous.graphics.Window;
import nebulous.graphics.renderers.GUIRenderer;
import nebulous.logic.Input;

public abstract class GUIButton extends GUIElement {
	
	private boolean mouseHovering = false;
	private boolean exitingFlag = false;
	private float hoverOffset = 0f;
	
	public abstract void onMouseHover(Game game);
	
	public abstract void onMouseClick(Game game);
	
	public abstract void onMouseEnter(Game game);
	
	public abstract void onMouseExit(Game game);
	
	@Override
	public void update(Game game) {
		super.update(game);
		checkEvents(game, game.getWindow(), GUIRenderer.instance().getCamera());
	}
	
	Vector2f mousePos;
	Vector2f position;
	
	private void checkEvents(Game game, Window window, Camera camera) {
		
		mousePos = VectorUtils.getMouseWorldPos(window, camera);
		position = getPosition();
		
		float halfWidth  = (scaleX + hoverOffset) / 2.0f;
		float halfHeight = (scaleY + hoverOffset) / 2.0f;
		
		if(!mouseHovering & exitingFlag){
			onMouseExit(game);
			exitingFlag = false;
		}
		
		mouseHovering = false;
		
		if((mousePos.x > position.x - halfWidth) & (mousePos.y > position.y - halfHeight)) {
			if((mousePos.x < position.x + halfWidth) & (mousePos.y < position.y + halfHeight)) {
				
				onMouseHover(game);
				
				if(!mouseHovering) onMouseEnter(game);
				
				exitingFlag = true;
				mouseHovering = true;
				
				if(Input.isMouseButtonClicked(0))
					onMouseClick(game);
				
			}
		}
		
	}

	public float getHoverOffset() {
		return hoverOffset;
	}

	public GUIElement setHoverOffset(float offset) {
		this.hoverOffset = offset;
		return this;
	}

}
