package nebulous.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Console {
	
	private static boolean enablePrefix 	  = false;
	private static boolean enableTimestamp    = false;
	private static String prefix 	          = "[Console] ";
	private static Date date 	              = null;
	private static DateFormat format          = new SimpleDateFormat("HH:mm:ss");
	
	public static void print(String text) {
		print(prefix, text);
	}
	
	public static void print(String prefix, String text) {
		if(enablePrefix)
			text = prefix + text;
		if(enableTimestamp) {
			date = new Date();
			text = "[" + format.format(date).toString() + "]" + (enablePrefix ? "" : " ") + text;
		} System.out.print(text);
	}
	
	public static void println(String text) {
		println(prefix, text);
	}
	
	public static void println(String prefix, String text) {
		if(enablePrefix)
			text = "[" + prefix + "]" + text;
		if(enableTimestamp) {
			date = new Date();
			text = "[" + format.format(date).toString() + "]" + (enablePrefix ? "" : " ") + text;
		} System.out.println(text);
	}
	
	public static void printErr(String text) {
		printErr(prefix, text);
	}
	
	public static void printErr(String prefix, String text) {
		if(enablePrefix)
			text = "[" + prefix + "]" + text;
		if(enableTimestamp) {
			date = new Date();
			text = "[" + format.format(date).toString() + "]" + (enablePrefix ? "" : " ") + text;
		} System.err.println(text);
	}

	public static void enablePrefix(boolean enablePrefix) {
		Console.enablePrefix = enablePrefix;
	}

	public static void enableTimestamp(boolean enableTimestamp) {
		Console.enableTimestamp = enableTimestamp;
	}

	public static void setPrefix(String prefix) {
		Console.prefix = prefix;
	}

}
