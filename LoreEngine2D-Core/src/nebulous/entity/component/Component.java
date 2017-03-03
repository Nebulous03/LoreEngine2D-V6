package nebulous.entity.component;

import nebulous.entity.Entity;

public class Component {
	
	protected Entity parent = null;
	
	public void init() {
		// Overwrite init
	}
	
	public void setParent(Entity entity) {
		this.parent = entity;
	}
	
	public Entity getParent() {
		return parent;
	}
}
