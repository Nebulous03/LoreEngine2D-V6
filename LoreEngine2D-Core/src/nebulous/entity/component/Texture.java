package nebulous.entity.component;

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

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import nebulous.utils.Console;

public class Texture extends Component {
	
	private int textureID;
	private String fileLocation;
	
	public static int UNKNOWN  = new Texture("/textures/unknown.png").getTextureID();
	public static int UNKNOWN2 = new Texture("/textures/unknown2.png").getTextureID();
	private static int textureIndexSize;
	
	private int width  = 0;
	private int height = 0;
	
	public Texture(String fileLocation) {
		this.fileLocation = fileLocation;
		this.textureID = loadTexture(fileLocation);
		textureIndexSize++;
	}
	
	public Texture(int textureID) {
		this.textureID = textureID;
		// this.fileLocation = getById( blahh... )
		//TODO: Add global texture hash
	}
	
	private int loadTexture(String filename){
		boolean successful = true;
		BufferedImage image;
		try {
			try{
				image = ImageIO.read(Texture.class.getResource(filename));
			} catch(Exception e){
				Console.printErr("ERROR","UNABLE TO READ FILE: '" + filename + "'");
				Console.println("ERROR", "Error reading buffered image, replaced with 'unknown.png'.");
				image = ImageIO.read(Texture.class.getResource("/textures/unknown.png"));
				successful = false;
				e.printStackTrace();
			}

			width = image.getWidth();
			height = image.getHeight();

			int[] pixels = new int[width * height * 4];
			pixels = image.getRGB(0, 0, width, height, null, 0, width);

			ByteBuffer pixelBuffer = BufferUtils.createByteBuffer(width * height * 4);
			
			boolean hasAlpha = image.getColorModel().hasAlpha();

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int pixel = pixels[y * width + x];
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

			if(successful)
			Console.println("Textures","Texture successfully created: " + filename + " (id:" + id + ")");
			
			return id;

		} catch (IOException e) {
			Console.println("Error","Unknown error generating textures");
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int getTextureID() {
		return textureID;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public String getFileLocation() {
		return fileLocation;
	}

	public static int getIndexSize() {
		return textureIndexSize;
	}

	public void set(Texture texture) {
		this.textureID = texture.textureID;
	}
	
	public void set(int textureID) {
		this.textureID = textureID;
	}
	
	@Override
	public String toString() {
		return "TextureID = " + textureID;
	}

}
