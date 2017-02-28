package nebulous.graphics;

public class DefaultRenderer extends Renderer {

	protected static Renderer defaultRenderer;
	
	public DefaultRenderer() {
		defaultRenderer = new DefaultRenderer();
	}
	
	public static Renderer instance() {
		return defaultRenderer;
	}

}
