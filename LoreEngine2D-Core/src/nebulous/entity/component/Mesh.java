package nebulous.entity.component;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Mesh extends Component {
	
	private int vCount  = 0;
	private int vao		= 0;
	private int vbo		= 0;
	private int ibo		= 0;
	
	private int tbo     = 0;
	
	protected float[] vertices;
	protected int[]   indices;
	protected float[] texCoords;
	
	protected Texture texture;
	
	public Mesh(float[] vertices, int[] indices, float[] texCoords) {
		vCount = indices.length;
		this.vertices = vertices;
		this.indices = indices;
		this.texCoords = texCoords;
	}
	
	@Override
	public void init() {
		
		texture = (Texture) parent.getComponent(Texture.class);
		
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		// BIND VBO
		vbo = glGenBuffers();
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
		vertexBuffer.put(vertices).flip();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		// BIND IBO
		ibo = glGenBuffers();
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(indices.length);
		indexBuffer.put(indices).flip();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
		
		// BIND TBO
		tbo = glGenBuffers();
		FloatBuffer textureBuffer = BufferUtils.createFloatBuffer(texCoords.length);
		textureBuffer.put(texCoords).flip();
		glBindBuffer(GL_ARRAY_BUFFER, tbo);
		glBufferData(GL_ARRAY_BUFFER, textureBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		
	}
	
	public void renderMesh() {
		
        // Bind VAO Data
        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        
        // Bind Texture Data
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
        
        // Draw Mesh
        glDrawElements(GL_TRIANGLES, vCount, GL_UNSIGNED_INT, 0);

        // Restore state
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        
	}
	
	public void cleanup() {
		
		glDisableVertexAttribArray(0);
	
	    glBindBuffer(GL_ARRAY_BUFFER, 0);
	    glDeleteBuffers(vbo);
	    glDeleteBuffers(ibo);

	    glBindVertexArray(0);
	    glDeleteVertexArrays(vao);
	    
	}
	
	public static final Mesh PLANE(){
		
		float[] vertices = new float[]{
				 -0.5f,  0.5f, 0.0f,
			     -0.5f, -0.5f, 0.0f,
			      0.5f, -0.5f, 0.0f,
			      0.5f,  0.5f, 0.0f,
		    };
		
		int[] indices = new int[]{
		        0, 1, 3, 3, 1, 2,
		    };

		float[] textureCoords = new float[]{
				0,0,
				0,1,
				1,1,
				1,0
		};

		return new Mesh(vertices, indices, textureCoords);
		//TODO: DONT USE NEW MESH
	}

	public int getVCount() {
		return vCount;
	}

	public int getVao() {
		return vao;
	}

	public int getVbo() {
		return vbo;
	}

	public int getIbo() {
		return ibo;
	}

	public float[] getVertices() {
		return vertices;
	}

	public int[] getIndices() {
		return indices;
	}

	public float[] getTexCoords() {
		return texCoords;
	}

}
