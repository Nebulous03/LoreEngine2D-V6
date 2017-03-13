package nebulous.graphics;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import nebulous.graphics.component.Texture;
import nebulous.utils.Console;

public class SpriteSheet {
	
	private BufferedImage sheet;
	private String location;
	
	private int width = 0;
	private int height = 0;
	
	private int textureIntexSize = 0;
	
	private ArrayList<Texture> textures;
	
	public SpriteSheet(String location, int tileSize) {
		this.location = location;
		this.textures = new ArrayList<Texture>();
		sheet = loadSpriteMap(location);
		for(int y = 0; y < height / tileSize; y++) {
			for(int x = 0; x < width / tileSize; x++) {
				textures.add(generateTexture(x * tileSize, y * tileSize, tileSize, tileSize));
			}
		}
	}
	
	private BufferedImage loadSpriteMap(String location){
		
		BufferedImage image;
		
		try {
			try{
				image = ImageIO.read(Texture.class.getResource(location));
				width = image.getWidth();
				height = image.getHeight();
			} catch(Exception e){
				Console.printErr("ERROR","UNABLE TO READ FILE: '" + location + "'");
				Console.println("ERROR", "Error reading buffered image, replaced with 'unknown.png'.");
				image = ImageIO.read(Texture.class.getResource("/textures/unknown.png"));
				e.printStackTrace();
			}

			return image;
			
		} catch (IOException e) {
			Console.println("Error","Unknown error generating textures");
			e.printStackTrace();
		}
		
		return null;
	}
	
	private Texture generateTexture(int x, int y, int width, int height) {
		try {
			
			int[] pixels = new int[width * height * 4];
			pixels = sheet.getRGB(x, y, width, height, null, 0, width);

			ByteBuffer pixelBuffer = BufferUtils.createByteBuffer(width * height * 4);
			
			boolean hasAlpha = sheet.getColorModel().hasAlpha();

			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					int pixel = pixels[i * width + j];
					pixelBuffer.put((byte) ((pixel >> 16) & 0xFF)); // RED
					pixelBuffer.put((byte) ((pixel >> 8) & 0xFF)); // GREEN
					pixelBuffer.put((byte) ((pixel >> 0) & 0xFF)); // BLUE
					if(hasAlpha) pixelBuffer.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA
					else pixelBuffer.put((byte)(0xFF));
				}
			}

			pixelBuffer.flip();

			int id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST); //GL_LINEAR
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST); //GL_LINEAR

			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixelBuffer);

			Console.println("Textures","Texture successfully created: " + location + ":" + textureIntexSize + " (id:" + id + ")");
			
			textureIntexSize++;
			
			return new Texture(id);	//TODO: maybe do this better?
			
		} catch (Exception e) {
			Console.println("Error","Unknown error generating textures");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Texture getTexture(int index) {	//Swap with name when atlas is implemented
		return textures.get(index);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
