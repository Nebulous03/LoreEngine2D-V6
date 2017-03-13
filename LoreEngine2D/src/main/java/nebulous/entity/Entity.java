package nebulous.entity;

import java.util.ArrayList;

import nebulous.Game;
import nebulous.logic.Instance;

public class Entity {
	
	public long ID = -1;
	private boolean registered;
	
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
		
		if(registered)
			return instance.getEntitySystem().hasComponent(this, componentClass);
		else {
			for(Component c : components)
				if(c.getClass().equals(componentClass)) return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getComponent(Class<?> componentClass) {
		
		if(registered) {
			T component = (T) instance.getEntitySystem().getComponentFromEntity(this, componentClass);
			if(component == null)
				new Exception("Component '" + componentClass.getSimpleName() + "' is not bound to entity").printStackTrace();
			return component;
		} else {
			for(Component c : components)
				if(c.getClass().equals(componentClass)) return (T)c;
		} 
		
		return null;
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

	public boolean isRegistered() {
		return registered;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

}
