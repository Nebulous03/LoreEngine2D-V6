package nebulous.entity;

public class Instance {

	private EntitySystem entitySystem;
	
	public Instance() {
		entitySystem = new EntitySystem();
	}
	
	public void load() {
		EntitySystem.setInstance(this);
	}
	
	public EntitySystem getEntitySystem() {
		return entitySystem;
	}
	
}
