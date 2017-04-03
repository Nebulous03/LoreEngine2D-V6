package nebulous.addons.entity.simple;

import nebulous.entity.Entity;
import nebulous.graphics.component.Render;
import nebulous.graphics.component.Transform;
import nebulous.graphics.font.FontMap;
import nebulous.graphics.font.TextData;
import nebulous.graphics.renderers.TextRenderer;

public class TextElement extends Entity {
	
	public TextData text;
	
	public TextElement(float x, float y, FontMap map, int size, String text) {
		this.text = new TextData(map, size, text);
		add(new Transform(x, y));
		add(new Render(new TextRenderer(this.text)));
	}

}
