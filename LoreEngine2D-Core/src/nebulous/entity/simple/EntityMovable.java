package nebulous.entity.simple;

import org.joml.Vector2f;

import nebulous.Game;
import nebulous.entity.Entity;
import nebulous.entity.component.Mesh;
import nebulous.entity.component.Render;
import nebulous.entity.component.Texture;
import nebulous.entity.component.Transform;
import nebulous.entity.component.Update;
import nebulous.entity.component.events.UpdateEvent;
import nebulous.graphics.Renderer;
import nebulous.graphics.shaders.DefaultShader;

public abstract class EntityMovable extends Entity {
	
	private float startX = 0;
	private float startY = 0;
	
	protected EntityMovable() { }
	
	public EntityMovable(float x, float y) {
		this.startX = x;
		this.startY = y;
	}
	
	@Override
	public void init(Game game) {
		add(new Transform(startX, startY));
		add(Mesh.PLANE());
		add(new Render(new Renderer(new DefaultShader(), game.getActiveLevel().getCamera(), game.getWindow())));
		add(new Update(new UpdateEvent() {public void invoke(Game game, double delta) {update(game, delta);}}));
		add(Texture.UNKNOWN);
	}
	
	public abstract void update(Game game, double delta);
	
	public void move(float deltaX, float deltaY) {
		Vector2f pos = ((Transform)getComponent(Transform.class)).position; // TODO: Move these to own instance?
		pos.x += deltaX;
		pos.y += deltaY;
	}
	
	public void rotate(float degrees) {
		((Transform)getComponent(Transform.class)).rotation += degrees;
	}
	
	public void setPosition(float x, float y) {
		Vector2f pos = ((Transform)getComponent(Transform.class)).position;
		pos.x = x;
		pos.y = y;
	}
	
	public void setRotation(float degrees) {
		((Transform)getComponent(Transform.class)).rotation = degrees;
	}
	
	public float getX() {
		return ((Transform)getComponent(Transform.class)).position.x;
	}
	
	public float getY() {
		return ((Transform)getComponent(Transform.class)).position.y;
	}
	
	public Vector2f getPosition() {
		return ((Transform)getComponent(Transform.class)).position;
	}
	
	public float getRotation() {
		return ((Transform)getComponent(Transform.class)).rotation;
	}
	
	public Vector2f getScale() {
		return ((Transform)getComponent(Transform.class)).scale;
	}
	
}
