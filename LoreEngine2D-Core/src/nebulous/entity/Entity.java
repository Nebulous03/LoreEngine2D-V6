package nebulous.entity;

import java.util.ArrayList;

import nebulous.Game;
import nebulous.entity.component.Component;

public class Entity {
	
	public long ID = -1;
	private Instance instance;
	
	private ArrayList<Component> components = null;
	
	public void init(Game game) {}
	
	public Entity add(Component component){	//TODO: add ability to add/remove after registering
		if(components == null)
			components = new ArrayList<Component>();
		components.add(component);
		return this;
	}
	
	public Entity remove(Component component){
		components.remove(component);
		return this;
	}
	
	public boolean hasComponent(Class<?> componentClass) {
		return instance.getEntitySystem().hasComponent(this, componentClass);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getComponent(Class<?> componentClass) {
		T component = (T) instance.getEntitySystem().getComponentFromEntity(this, componentClass);
		if(component == null)
			new Exception("Component '" + componentClass.getSimpleName() + "' is not bound to entity").printStackTrace();
		return component;
	}

	public ArrayList<Component> getComponents() {
		return components;
	}

	public Instance getInstance() {
		return instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

}
