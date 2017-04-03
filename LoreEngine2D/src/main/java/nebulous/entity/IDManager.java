package nebulous.entity;

import java.util.ArrayList;

public class IDManager {
	
	private static long currentEntityIDIndex = 0;
	private static long unusedEntityIDSize = 0;
	private static ArrayList<Long> removedIDsList = new ArrayList<Long>();
	
	
	public static long getNextEntityID() {
		return 0;
	}
	
	public static long getCurrentEntityIDIndex() {
		return currentEntityIDIndex;
	}
	
	public static long getEntityCount() {
		return currentEntityIDIndex - unusedEntityIDSize;
	}
	
	public static long getNextComponentID() {
		return 0;
	}

}
