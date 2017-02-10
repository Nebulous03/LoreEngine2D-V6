package nebulous.component;

import nebulous.Game;
import nebulous.graphics.primatives.Mesh;
import nebulous.physics.BoundingBox2D;
import nebulous.utils.Console;

public abstract class Entity2D extends GameObject2D{
	
	public Entity2D(Mesh mesh, float x, float y) {
		super(mesh, x, y);
		boundingBox = new BoundingBox2D(1, 1, position);
		Console.println("Entity", "Entity loaded at " + position);	//TODO: Add entity naming
	}
	
	public abstract void init();
	
	public abstract void update(Game game, double delta);
	
}
