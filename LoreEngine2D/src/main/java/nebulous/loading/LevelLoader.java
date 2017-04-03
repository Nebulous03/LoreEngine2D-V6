package nebulous.loading;

import java.util.ArrayList;

import nebulous.entity.Entity;
import nebulous.object.Level;
import nebulous.object.TileMap;

public class LevelLoader {
	
	private ArrayList<Entity> entities;
	
	public void saveLevel(Level level) {
		entities = level.getEntitySystem().getAllEntities();
		
	}
	
	private TileMap loadMap(String levelFile) {
		return null;
	}
	
	private void saveMap(TileMap map, String levelFile) {
		
	}

}
