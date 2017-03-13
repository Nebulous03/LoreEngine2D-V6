package nebulous.graphics.font;

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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import nebulous.graphics.component.Mesh;
import nebulous.graphics.component.Texture;
import nebulous.utils.Console;

public class FontMap {
	
	private String imageLocation;
	private String fontLocation;
	
	private Texture fullTexture;
	
	private float mapWidth;
	private float mapHeight;
	
	private BufferedImage fontMap;
	private HashMap<Character, FontChar> charHash;
//	private HashMap<Character, Texture> texHash;
	private HashMap<Character, Mesh> meshHash;
	
	public FontMap(String image, String font) {
		this.imageLocation = image;
		this.fontLocation = font;
		this.charHash = new HashMap<Character, FontChar>();
//		this.texHash = new HashMap<Character, Texture>();
		this.meshHash = new HashMap<Character, Mesh>();
		fullTexture = new Texture(image);
		loadFontMapImage(image);
		loadFontMapData(font);
	}
	
	private void loadFontMapData(String location) {
		
		ArrayList<String> fontData = new ArrayList<String>();
		
		try {
			
			Console.println("Font","Loading Font " + location);
			Scanner scanner = null;
			
			try {
				scanner = new Scanner(new File(FontMap.class.getResource(location).toURI().getPath()));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			
			while(scanner.hasNextLine())
				fontData.add(scanner.nextLine());
			
			for(String s : fontData) {										//FIXME: This is not the best way to do this..
				if(s.startsWith("char") && !s.startsWith("chars")) {
					FontChar fchar = new FontChar();
					String[] data = s.split("\\s+");
					fchar.id = Integer.parseInt(data[1].split("=")[1]);
					fchar.x = Integer.parseInt(data[2].split("=")[1]);
					fchar.y = Integer.parseInt(data[3].split("=")[1]);
					fchar.width = Integer.parseInt(data[4].split("=")[1]);
					fchar.height = Integer.parseInt(data[5].split("=")[1]);
					fchar.xOffset = Integer.parseInt(data[6].split("=")[1]);
					fchar.yOffset = Integer.parseInt(data[7].split("=")[1]);
					fchar.xAdvance = Integer.parseInt(data[8].split("=")[1]);
//					texHash.put((char)fchar.id, generateTexture(fchar.x, fchar.y, fchar.width, fchar.height));
					meshHash.put((char)fchar.id, generateMesh(fchar.width, fchar.height, fchar.x, fchar.y));
//					meshHash.put((char)fchar.id, Mesh.PLANE().create());
					charHash.put((char)fchar.id, fchar);
				}
			}
			
			scanner.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void loadFontMapImage(String location){
		
		try {
			try{
				fontMap = ImageIO.read(Texture.class.getResource(location));
				mapWidth = fontMap.getWidth();
				mapHeight = fontMap.getHeight();
			} catch(Exception e){
				Console.printErr("ERROR","UNABLE TO READ FILE: '" + location + "'");
				Console.println("ERROR", "Error reading buffered image, replaced with 'unknown.png'.");
				fontMap = ImageIO.read(Texture.class.getResource("/textures/unknown.png"));
				e.printStackTrace();
			}

		} catch (IOException e) {
			Console.println("Error","Unknown error generating textures");
			e.printStackTrace();
		}
		
	}
	
//	private Texture generateTexture(int x, int y, int width, int height) {
//		try {
//			
//			int[] pixels = new int[width * height * 4];
//			pixels = fontMap.getRGB(x, y, width, height, null, 0, width);
//
//			ByteBuffer pixelBuffer = BufferUtils.createByteBuffer(width * height * 4);
//			
//			boolean hasAlpha = fontMap.getColorModel().hasAlpha();
//
//			for (int i = 0; i < height; i++) {
//				for (int j = 0; j < width; j++) {
//					int pixel = pixels[i * width + j];
//					pixelBuffer.put((byte) ((pixel >> 16) & 0xFF)); // RED
//					pixelBuffer.put((byte) ((pixel >> 8) & 0xFF)); // GREEN
//					pixelBuffer.put((byte) ((pixel >> 0) & 0xFF)); // BLUE
//					if(hasAlpha) pixelBuffer.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA
//					else pixelBuffer.put((byte)(0xFF));
//				}
//			}
//
//			pixelBuffer.flip();
//
//			int id = glGenTextures();
//			glBindTexture(GL_TEXTURE_2D, id);
//			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
//			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
//			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST); //GL_LINEAR
//			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST); //GL_LINEAR
//
//			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixelBuffer);
//
//			return new Texture(id);	//TODO: maybe do this better?
//			
//		} catch (Exception e) {
//			Console.println("Error","Unknown error generating textures");
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
	
//	public Texture getTexture(int charID) {
//		return texHash.get(charID);
//	}
	
	public Mesh generateMesh(float width, float height, float texX, float texY) {
		System.out.println("x = " + texX);
		System.out.println("y = " + texY);
		System.out.println("width = " + width);
		System.out.println("heigth = " + height);
		return Mesh.PLANE(1, 1, 443, 171, width / mapWidth, height / mapHeight).create();
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public String getFontLocation() {
		return fontLocation;
	}

	public BufferedImage getFontMapImage() {
		return fontMap;
	}

	public float getMapWidth() {
		return mapWidth;
	}

	public float getMapHeight() {
		return mapHeight;
	}

	public BufferedImage getFontMap() {
		return fontMap;
	}

	public HashMap<Character, FontChar> getCharHash() {
		return charHash;
	}

//	public HashMap<Character, Texture> getTexHash() {
//		return texHash;
//	}

	public HashMap<Character, Mesh> getMeshHash() {
		return meshHash;
	}

	public Texture getFullTexture() {
		return fullTexture;
	}
	
}
