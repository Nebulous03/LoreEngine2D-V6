package nebulous.entity;

import java.util.ArrayList;

import nebulous.logic.Instance;

public class EntityBuilder {
	
	private Entity entity;
	private ArrayList<Component> components;
	
	public EntityBuilder(Instance instance) {
		entity = new Entity();
		components = new ArrayList<>();
	}

	public EntityBuilder add(Component component) {
		components.add(component);
		return this;
	}
	
	public Entity build() {
		entity.ID = EntitySystem.getActiveInstance().getEntitySystem().getNextID();
		EntitySystem.getActiveSystem().addEntity(entity, components);
		return entity;
	}
	
}
