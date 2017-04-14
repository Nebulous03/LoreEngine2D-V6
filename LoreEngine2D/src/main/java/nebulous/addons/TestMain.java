package nebulous.addons;

import nebulous.Game;
import nebulous.addons.entity.simple.EntityMovable;
import nebulous.object.Level;

public class TestMain extends Game {
	
	public static void main(String[] args) {
		new TestMain().start();
	}

	@Override
	public void preInit() {
	}

	@Override
	public void init() {
		window.forceLegacyMode();
		getWindow().printGLStats();
		add("lvl", new Level() {

			@Override
			public void init(Game game) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void update(Game game, double delta) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onLoad() {
				add("test", new EntityMovable() {
					
					@Override
					public void update(Game game, double delta) {
						
					}
				});
				
			}

			@Override
			public void onUnload() {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		load("lvl");
	}

	@Override
	public void postInit() {
		
	}

	@Override
	public void update(Game game, double delta) {
		
	}

}
