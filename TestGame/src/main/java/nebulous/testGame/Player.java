package nebulous.testGame;

import nebulous.Game;
import nebulous.addons.entity.simple.EntityMovable;
import nebulous.graphics.component.Transform;
import nebulous.graphics.sprite.SpriteSheet;
import nebulous.logic.Input;
import nebulous.utils.Console;

public class Player extends EntityMovable {
	
	private float walkSpeed = 6f;
	
	public SpriteSheet sheet;
	
	public Player(float x, float y) {
		super(x, y);
	}
	
	@Override
	public void init(Game game) {
		Console.println("Player", "Player initialized...");
		sheet = new SpriteSheet("/textures/spritesheet1.png", 64);
		setTexture(sheet.getTexture(0));
	}

	@Override
	public void update(Game game, double delta) {
		
		float deltaX = 0;
		float deltaY = 0;
		
		// SPACE
		if(Input.isKeyHeld(Input.KEY_SPACE)){
			
		}
		
		// W(UP)
		if(Input.isKeyHeld(Input.KEY_W)){
			deltaY += walkSpeed;					//TODO: FIX and multiply with delta
			setTexture(sheet.getTexture(1));
		}
		
		// A(LEFT)
		if(Input.isKeyHeld(Input.KEY_A)){
			deltaX += -(walkSpeed);
			setTexture(sheet.getTexture(0));
		}
		
		// S(DOWN)
		if(Input.isKeyHeld(Input.KEY_S)){
			deltaY += -(walkSpeed);
			setTexture(sheet.getTexture(3));
		}
		
		// D(RIGHT)
		if(Input.isKeyHeld(Input.KEY_D)){
			deltaX += walkSpeed;
			setTexture(sheet.getTexture(2));
		}
		
		attemptMove(game.getActiveLevel(), (float)(deltaX * delta), (float)(deltaY * delta));
		game.getActiveLevel().getCamera().setPosition(((Transform)getComponent(Transform.class)).position);
		
	}

}
