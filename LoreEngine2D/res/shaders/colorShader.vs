#version 330

layout (location = 0) in vec3 position;

out vec4 vColor;

uniform vec4 color;
uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;

void main()
{
    gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(position, 1.0);
    vColor = color;
	
}