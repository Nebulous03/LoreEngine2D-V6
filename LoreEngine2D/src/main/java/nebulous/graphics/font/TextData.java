package nebulous.graphics.font;

public class TextData {
	
	private String text;
	private FontMap map;
	private int size;
	
	public TextData(FontMap map, int size, String text) {
		this.text = text;
		this.map = map;
		this.size = size;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public FontMap getFont() {
		return map;
	}

	public void setFont(FontMap map) {
		this.map = map;
	}

	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}

}
