package test;

import java.util.LinkedHashMap;

import nebulous.entity.Entity;
import nebulous.entity.EntityBuilder;
import nebulous.entity.EntitySystem;
import nebulous.entity.Instance;
import nebulous.entity.component.Component;
import nebulous.entity.component.Transform;
import nebulous.entity.component.Update;

public class EntityTest {
	
	static Instance instance = new Instance();
	
	public static int sampleSize = 1000;
	public static int runSize = 1000;
	
	public static void main(String[] args) {
		
		instance.load();
		
		System.out.println("GENERATING ENTITIES");
		
		double startGen = System.nanoTime();
		
		for(int i = 0; i < sampleSize; i++) {
			Entity e = new EntityBuilder(instance)
					.add(new Transform())
					.add(new Update())
					.build();
		}
		
		double endGen = System.nanoTime();
		
		System.out.println("GEN TIME: " + ((endGen - startGen) / 1000000000d) + " mils");
		
		double startRun = System.nanoTime();
		
		EntitySystem system = instance.getEntitySystem();
		LinkedHashMap<Long, Component> hash = instance.getEntitySystem().getComponentHash(Update.class);
		
		int total = 0;
		
		for(int i = 0; i < runSize; i++) {
			double startRunSmall = System.nanoTime();
			for(Component u : hash.values()) {
				((Update)u).update();
				total++;
			}
			double endRunSmall = System.nanoTime();
			System.out.println("ITTERATION TIME: " + ((endRunSmall - startRunSmall) / 1000000000d));
		}
		
		double endRun = System.nanoTime();
		
		System.out.println("RUN TIME: " + ((endRun - startRun) / 1000000000d) + ", TOTAL: " + total);
	}

}
