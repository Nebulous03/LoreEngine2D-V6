package nebulous.graphics.events;

public abstract class WindowResizeEvent {
	
	public abstract void invoke(int width, int height, int deltaWidth, int deltaHeight);

}
