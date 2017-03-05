package nebulous.testGame;

import nebulous.Game;
import nebulous.entity.component.Transform;
import nebulous.entity.simple.EntityMovable;
import nebulous.logic.Input;
import nebulous.utils.Console;

public class Player extends EntityMovable {
	
	private float walkSpeed = 0.1f;
	
	public Player(float x, float y) {
		super(x, y);
	}
	
	@Override
	public void init(Game game) {
		Console.println("Player", "Player initialized...");
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
		}
		
		// A(LEFT)
		if(Input.isKeyHeld(Input.KEY_A)){
			deltaX += -(walkSpeed);
		}
		
		// S(DOWN)
		if(Input.isKeyHeld(Input.KEY_S)){
			deltaY += -(walkSpeed);
		}
		
		// D(RIGHT)
		if(Input.isKeyHeld(Input.KEY_D)){
			deltaX += walkSpeed;
		}
		
		attemptMove(game.getActiveLevel(), deltaX, deltaY);
		game.getActiveLevel().getCamera().setPosition(((Transform)getComponent(Transform.class)).position);
		
	}

}
