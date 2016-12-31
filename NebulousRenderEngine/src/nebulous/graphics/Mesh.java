package nebulous.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Mesh {
	
	public int vCount   = 0;
	public int vao		= 0;
	public int vbo		= 0;
	public int ibo		= 0;
	public int tbo		= 0;
	
	public Texture texture;
	
	public Mesh(float[] vertices, int[] indices, float[] texCoords, Texture texture) {
		
		if(texture == null) texture = Texture.UNKNOWN;
		this.texture = texture;
		
		vCount = indices.length;
		vao = glGenVertexArrays();
		tbo = glGenBuffers();
		
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
		
		// BIND TEXCOORDS
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
        glBindTexture(GL_TEXTURE_2D, texture.textureID);
        
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
	
	public static final Mesh PLANE(Texture texture){
		
		float[] vertices = new float[]{
//		        -1.0f,  1.0f, 0.0f,
//		        -1.0f, -1.0f, 0.0f,
//		         1.0f, -1.0f, 0.0f,
//		         1.0f,  1.0f, 0.0f,
				
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

		return new Mesh(vertices, indices, textureCoords, texture);
	}

}
