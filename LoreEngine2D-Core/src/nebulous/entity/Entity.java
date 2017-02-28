package nebulous.entity;

import java.util.ArrayList;

import nebulous.entity.component.Component;
import nebulous.object.GameObject;

public class Entity implements GameObject {
	
	public long ID = -1;
	public Instance instance;
	
	private ArrayList<Component> components = null;
	
	public void addComponent(Component component){
		if(components == null) {
			components = new ArrayList<Component>();
		} else {
			components.add(component);
			instance.getEntitySystem().addComponent(this, component);
		}
	}
	
	public void removeComponent(Component component){
		components.remove(component);
		instance.getEntitySystem().removeComponent(this, component);
	}
	
	public boolean hasComponent(Component component) {
		return components.contains(component);
	}
	
	public Component getComponent(Class<?> componentClass) {
		return instance.getEntitySystem().getComponentFromEntity(this, componentClass);
	}

	public ArrayList<Component> getComponents() {
		return components;
	}
}
