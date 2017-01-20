package nebulous.terrariaClone;

import nebulous.Game;
import nebulous.utils.Console;

public class TerrariaClone extends Game{
	
	public static void main(String[] args) {
		new TerrariaClone();
	}

	@Override
	public void preInit() {
		window.enableVSync(false);
		
		System.out.println(" -------------------------");
		System.out.println("   NebulousDev Presents:  ");
		System.out.println(" -------------------------");
		System.out.println("    TERRARIA CLONE V1.0   ");
		System.out.println(" -------------------------");
		
		Console.enablePrefix(true);
		Console.enableTimestamp(true);
	}

	@Override
	public void init() {
		
		window.printGLStats();
		System.out.println(" -------------------------");
		
		World world = new World();
		addLevel("world", world);
		
		loadLevel("world");
	}

	@Override
	public void update(Game game, double delta) {
		
	}

}
