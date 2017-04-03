package nebulous.testGame;

import org.joml.Vector3f;

import nebulous.Game;
import nebulous.addons.entity.simple.GUIElement;
import nebulous.addons.entity.simple.GUIElement.Snap;
import nebulous.addons.entity.simple.TextElement;
import nebulous.addons.util.VectorUtils;
import nebulous.graphics.Camera;
import nebulous.graphics.component.Texture;
import nebulous.logic.Input;
import nebulous.object.Level;
import nebulous.object.TileMap;

public class TestLevel extends Level {

	public TileMap map         = null;
	public TileMap map2        = null;
	public TileMap map3		   = null;
	public Player player       = null;
	public BlockEntity block   = null;
	public BlockEntity block2  = null;
	public BlockEntity block3  = null;
	public BlockEntity block4  = null;
	public BigBlock bigBlock   = null;
	public HalfBlock halfBlock = null;
	
	public BlockEntity mouseBlock = null;
	
	public TextElement text;
	public GUIElement gui;
	
	Texture STONE = new Texture("/textures/stone.png");
	Texture GRASS = new Texture("/textures/grass_side.png");
	Texture TORCH = new Texture("/textures/torch_on.png");
	
	@Override
	public void init(Game game) {
		
		game.setTickrate(10D);
		
		camera.setPerspective(Camera.PERSPECTIVE);
		camera.setPosition(new Vector3f(0,0,10f));
		
		map = new TileMap(128, 32).enableCulling().setCull(24, 16).populate(STONE);
		map2 = new TileMap(128, 32).enableCulling().setCull(24, 16);
		map3 = new TileMap(128, 32).enableCulling().setCull(24, 16);
		
		for(int i = 0; i < map2.getWidth(); i++) {
			map2.set(i, 0, GRASS);
		}
		
		for(int i = 4; i < 16; i++) {
			map2.set(i, 3, GRASS);
		}
		
		for(int i = 10; i < 16; i++) {
			map2.set(i, 5, GRASS);
		}
		
		for(int i = 17; i < 21; i++) {
			map2.set(i, 3, GRASS);
		}
		
		for(int i = 3; i < 7; i++) {
			map2.set(21, i, GRASS);
		}
		
		map3.set(4, 4, TORCH);
		map2.set(18, 5, GRASS);
		map2.set(19, 5, GRASS);
		map2.set(19, 6, GRASS);
		
		map2.set(0, 0, null);
		
		map2.set(31, 0, null);
		
		player = new Player(4, 4);
		block = new BlockEntity(8, 8);
		block2 = new BlockEntity(9, 8);
		block3 = new BlockEntity(10, 8);
		block4 = new BlockEntity(11, 8);
		
		bigBlock = (BigBlock) new BigBlock(20, 20).setTexture(GRASS);
		halfBlock = (HalfBlock) new HalfBlock(15, 15).setTexture(GRASS);
		
		// MOUSE BLOCK THING
		
		mouseBlock = (BlockEntity) new BlockEntity(0, 0).setTexture(GRASS);
		
//		FontMap font = new FontMap("/fonts/arial.png", "/fonts/arial.fnt");
//		text = new TextElement(0, 0, font, 1, "Hello");
		
		gui = new GUIElement(Snap.CENTER, 0, 0)
				.setTexture(Texture.UNKNOWN2)
				.scale(0.25f, 0.25f);
		
		player.rotate(30f);
		
	}
	
	@Override
	public void onLoad() {
		add("map1", map);
		add("map2", map2);
		add("map3", map3);
		add("player", player);
		add("entity_1", block);
		add("entity_2", block2);
		add("entity_3", block3);
		add("entity_4", block4);
		add("entity_big", bigBlock);
		add("entity_half", halfBlock);
//		add("text", text);
//		add("gui", gui);
		
	}

	boolean moveUp = true;
	boolean moveRight = true;
	
	int timer = 0;
	int index = 0;
	
	@Override
	public void update(Game game, double delta) {
		
//		gui.updateGUIPosition(game);
		
//		gui.setPixelPosition(0, 0);
		
//		timer++;
//		
//		if(timer >= 100) {
//			if(index >= 4) index = 0; // HARD CODED // NO!
//			player.setTexture(sheet.getTexture(index));
//			index++;
//			timer = 0;
//		}
		
//		if(block.getPosition().x > 8) moveRight = false;
//		if(block.getPosition().x < 4) moveRight = true;
//		if(moveRight) block.move(0.05f, 0);
//		else block.move(-0.05f, 0);
//	
//		if(block4.getPosition().y > 12) moveUp = false;
//		if(block4.getPosition().y < 6) moveUp = true;
//		if(moveUp) block4.move(0, 0.05f);
//		else block4.move(0, -0.05f);
		
		if(block.getPosition().x > 8) moveRight = false;
		if(block.getPosition().x < 4) moveRight = true;
		if(moveRight) block.move(4f * (float)delta, 0);
		else block.move(-4f * (float)delta, 0);
	
		if(block4.getPosition().y > 12) moveUp = false;
		if(block4.getPosition().y < 6) moveUp = true;
		if(moveUp) block4.move(0, 4f * (float)delta);
		else block4.move(0, -4f * (float)delta);
		
		controlCamera(delta);
		
		// MOUSE BLOCK
		
		map2.enableCollision();
		
		if(Input.isKeyHeld(Input.KEY_LEFT_SHIFT)) {
			Vector3f pos = VectorUtils.toWorldSpace3D(game.getWindow(), camera, (float) Input.mousePosX, (float) Input.mousePosY, camera.getPosition().z);
			mouseBlock.setPosition(pos.x, pos.y);
			System.out.println("-------\nX - " + pos.x);
			System.out.println("Y - " + pos.y);
			System.out.println("Z - " + pos.z);
			map2.disableCollision();
			
			if(Input.isMouseButtonHeld(0)) {
				map2.set((int)(pos.x + 0.5f), (int)(pos.y + 0.5f), GRASS);
			}
			
			if(Input.isMouseButtonHeld(1)) {
				map2.set((int)(pos.x + 0.5f), (int)(pos.y + 0.5f), null);
			}
			
		}
		
	}
	
	float scrollVelocity = 0;
	
	public void controlCamera(double delta) {
		
		if(Input.isKeyHeld(Input.KEY_G)) camera.setPosition(camera.getPosition().add(new Vector3f(0,0,0.1f)));
		if(Input.isKeyHeld(Input.KEY_H)) camera.setPosition(camera.getPosition().add(new Vector3f(0,0,-0.1f)));
		if(Input.isKeyHeld(Input.KEY_E)) camera.setRotation(camera.getRotation().add(new Vector3f(0,0.1f,0)));
		if(Input.isKeyHeld(Input.KEY_Q)) camera.setRotation(camera.getRotation().add(new Vector3f(0,-0.1f,0)));
		
		if(Input.isKeyHeld(Input.KEY_P)) camera.setRotation(0,0,0);
		
		if(Input.isMouseScrollIncrease()){ 
			scrollVelocity = -60;
		}
		
		if(Input.isMouseScrollDecrease()){
			scrollVelocity = 60;
		}
		
		if(scrollVelocity > 0) {
			camera.setPosition(camera.getPosition().add(new Vector3f(0, 0, 0.2f * (float)delta * scrollVelocity)));
			scrollVelocity -= 0.5f;
		}
		
		if(scrollVelocity < 0) {
			camera.setPosition(camera.getPosition().add(new Vector3f(0, 0, 0.2f * (float)delta * scrollVelocity)));
			scrollVelocity += 0.5f;
		}
		
		if(scrollVelocity < 0.2f && scrollVelocity > -0.2f) scrollVelocity = 0;
	}
	
//	@Override
//	public void render(RenderEngine renderer) {
//		super.render(renderer);
//		
//		if(Input.isKeyHeld(Input.KEY_LEFT_SHIFT)) {
//			mouseBlock.render(renderer.getWindow(), camera, renderer.getShader());
//		}
//	}

	@Override
	public void onUnload() {
		remove(map);	//TODO: add tag functionality back
		remove(map2);
		remove(map3);
		remove(player);
		remove(block);
		remove(block2);
		remove(block3);
		remove(block4);
		remove(bigBlock);
		remove(halfBlock);
	}

}
