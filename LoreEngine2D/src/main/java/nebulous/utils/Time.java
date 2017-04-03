package nebulous.utils;

import org.lwjgl.glfw.GLFW;

public class Time {
	
	public static final double SECOND = 1000000000L;
	public static final double MILISECOND = 1000000L;
	
	public static double getTime(){
		return ((double) (GLFW.glfwGetTime()));
	}
	
	public static double getTimeMils() {
		return ((double) System.nanoTime()/(double)MILISECOND);
	}

}
