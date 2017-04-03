package nebulous.entity;

import nebulous.Game;
import nebulous.loading.ISavable;

public class Component implements ISavable {
	
	protected int componentID;
	protected Entity parent = null;
	
	public void init(Game game) {}
	
	public void setParent(Entity entity) {
		this.parent = entity;
	}
	
	public Entity getParent() {
		return parent;
	}
	
	@Override
	public String toString() {
		return "No metadata available";
	}
	
	public int getComponentID() {
		return componentID;
	}
	
	public void setComponentID(int id) {
		
	}

	@Override
	public byte[] saveData() {
		return null;
	}

	@Override
	public byte[] readData() {
		return null;
	}
}
