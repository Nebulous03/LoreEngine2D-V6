package nebulous.loading;

import java.util.ArrayList;

import nebulous.entity.Entity;
import nebulous.object.Level;
import nebulous.object.TileMap;

//This File is WIP
//TODO: ctrl+shift+o & ctrl+shift+f this file!

public class LevelLoader {
	
	private ArrayList<Entity> entities;
	
	public void saveLevel(Level level, String path) {
		entities = level.getEntitySystem().getAllEntities();
		FileOutputStream out = new FileOutputStream(new File(path), true);
		for(Entity e : entities){
			//TODO: Add something to save the tag aswell. 
			//TODO: Change tag to a component.
			//TODO: Version 0.0.9Unfinished
			out.write(new Byte("e")); // write out a byte stating that the following bytes are an entity.
			for(ISavable component : e){
				out.write(new Byte("c")); // write out a byte stating that the following bytes are a component.
				out.write(component.saveData());
				out.write(new Byte("n")); // write out a byte stating that this is the end of the component.
			}
			out.write(new Byte("N")); // write out a byte stating that this is the end of the entity.
		}
		out.flush();
		out.close();
	}
		
	Level outLevel;
	public Level loadLevel(String path){
		File file = new File(path);
		if(file.exists()){
			
			return outLevel;
		}
		
		return null;
	}
	
	private TileMap loadMap(String levelFile) {
		return null;
	}
	
	private void saveMap(TileMap map, String levelFile) {
		
	}

}
