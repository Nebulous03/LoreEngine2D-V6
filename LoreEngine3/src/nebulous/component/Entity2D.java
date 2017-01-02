package nebulous.component;

import nebulous.Game;
import nebulous.graphics.primatives.Mesh;

public abstract class Entity2D extends GameObject2D{
	
	public Entity2D(Mesh mesh, float x, float y) {
		super(mesh, x, y);
	}
	
	public abstract void init();
	
	public abstract void update(Game game, double delta);
	
}
