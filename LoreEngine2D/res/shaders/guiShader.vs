#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;

out vec2 vTexCoord;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;

void main()
{
    gl_Position = modelMatrix * vec4(position, 1.0);
	vTexCoord = texCoord;
	
}