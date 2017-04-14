package nebulous.graphics.renderers;

import java.awt.Color;

import org.joml.Vector4f;

import nebulous.entity.Entity;
import nebulous.graphics.Renderer;
import nebulous.graphics.Window;
import nebulous.graphics.component.Mesh;
import nebulous.graphics.component.Texture;
import nebulous.graphics.component.Transform;
import nebulous.graphics.shaders.ColorShader;

public class ColorRenderer extends Renderer {
	
	public Color color;
	public Vector4f vecColor;

	public ColorRenderer(Color color) {
		super(new ColorShader(color));
		this.color = color;
		this.vecColor = new Vector4f(color.getRed(), color.getBlue(), color.getGreen(), color.getAlpha());
	}
	
	@Override
	public void render(Entity entity) {
		
		this.camera = entity.getInstance().getGame().getActiveLevel().getCamera();
		this.window = entity.getInstance().getGame().getWindow();
		
		shader.bind();
		
		if(!Window.isLegacy()) {
		
			shader.updateUniforms();
	
//			shader.setUniform("color", vecColor);
			shader.setUniform("projectionMatrix", camera.calculateProjectionMatrix(window));
			shader.setUniform("viewMatrix", camera.calculateViewMatrix(window));
			shader.setUniform("modelMatrix", camera.getModelViewMatrix((Transform) entity.getComponent(Transform.class)));
			
		}
		
		renderMesh((Mesh)entity.getComponent(Mesh.class), (Texture)entity.getComponent(Texture.class));
		
	    shader.unbind();
		
	}
	
	public void setColor(Color color) {
		this.color = color;
		this.vecColor = new Vector4f(color.getRed(), color.getBlue(), color.getGreen(), color.getAlpha());
	}

}
