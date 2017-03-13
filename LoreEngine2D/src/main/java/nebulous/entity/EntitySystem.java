package nebulous.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import nebulous.Game;
import nebulous.logic.Instance;
import nebulous.utils.Console;

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
		
		try{
			for(Component c : components) {	//TODO: Maybe switch all these out with index for loops?
				if(componentHash.get(c.getClass()) == null)
					componentHash.put(c.getClass(), new LinkedHashMap<Long, Component>());
				componentHash.get(c.getClass()).put(entity.ID, c);
				c.setParent(entity);
			}
		} catch (Exception e) {
			Console.printErr("Entity/Warning", "Entity '" + entity.ID + "' was added but contains no data.");
		}
		
//		for(Component c : components) c.init();	//TODO: I dont like this second loop
		
		entity.setRegistered(true);
		
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
	
	public Component getComponentFromEntity(Entity entity, Class<?> componentClass) {	//TODO: fix this?
		LinkedHashMap<Long, Component> map = componentHash.get(componentClass);
		if(map == null) return null;
		else {
			Component result = map.get(entity.ID);
			if(result == null) return null;
			return result;
		}
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
	
	public void printEntity(Entity entity) {
		System.out.println(entity.getClass().getSimpleName() + ":" + entity.ID);
		for(Class<?> c : componentHash.keySet()) {
			Component comp = componentHash.get(c).get(entity.ID);
			System.out.println("\t" + comp.getClass().getSimpleName() + ":\n\t\t" + comp);
		}
	}
	
	public void initAllEntities(Game game) {
		for(Entity e : allEntities){
			e.init(game);
		}
	}

	public void initAllComponents(Game game) {
		for(Class<?> c : componentHash.keySet()) {
			for(Long l : componentHash.get(c).keySet()) {
				componentHash.get(c).get(l).init(game);
			}
		}
	}

	public boolean hasComponent(Entity entity, Class<?> componentClass) {
		return getComponentFromEntity(entity, componentClass) != null;
	}

}
