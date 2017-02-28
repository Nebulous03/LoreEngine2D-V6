package nebulous.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import nebulous.entity.component.Component;

public class EntitySystem {
	
	private ArrayList<Entity> allEntities;
	private HashMap<Long, Entity> idHash;
	private HashMap<Class<?>, LinkedHashMap<Long, Component>> componentHash;
	private long idCount = 0; // TODO: Implement something better
	
	private static Instance activeInstance = null;
	
	public EntitySystem() {
		
		allEntities = new ArrayList<Entity>();
		idHash = new HashMap<Long, Entity>();
		componentHash = new HashMap<Class<?>, LinkedHashMap<Long, Component>>();
		
	}
	
	public Entity addEntity(Entity entity, ArrayList<Component> components) {
		
		if(entity.ID == -1) entity.ID = getNextID();
		
		allEntities.add(entity);
		idHash.put(entity.ID, entity);
		
		for(Component c : components) {
			if(componentHash.get(c.getClass()) == null)
				componentHash.put(c.getClass(), new LinkedHashMap<Long, Component>());
			componentHash.get(c.getClass()).put(entity.ID, c);
		}
		
		return entity;
	}
	
	public Entity addEntity(Entity entity) {
		return addEntity(entity, entity.getComponents());
	}
	
	public long getNextID() {
		return idCount++;
	}

	public Entity removeEntity(Entity entity) {
		
		allEntities.remove(entity);
		idHash.remove(entity.ID);
		
		return entity;
	}
	
	public void removeEntity(long id) {
		
		allEntities.remove(idHash.get(id));
		idHash.remove(id);
		
	}
	
	public Component getComponentFromEntity(Entity entity, Class<?> componentClass) {
		return componentHash.get(componentClass).get(entity.ID);
	}
	
	public ArrayList<Entity> getAllEntities() { 
		return allEntities;
	}
	
	public static void setInstance(Instance instance) {
		activeInstance = instance;
	}
	
	public static Instance getActiveInstance() {
		return activeInstance;
	}
	
	public static EntitySystem getActiveSystem() {
		return activeInstance.getEntitySystem();
	}
	
	public LinkedHashMap<Long, Component> getComponentHash(Class<?> componentClass) {
		return componentHash.get(componentClass);
	}

	public void addComponent(Entity entity, Component component) {
		if(componentHash.get(component.getClass()) == null)
			componentHash.put(component.getClass(), new LinkedHashMap<Long, Component>());
		componentHash.get(component.getClass()).put(entity.ID, component);
	}

	public void removeComponent(Entity entity, Component component) {
		//TODO: do
	}

}
