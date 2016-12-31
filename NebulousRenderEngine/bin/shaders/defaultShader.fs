#version 330

in vec2 vTexCoord;
out vec4 fragColor;

uniform sampler2D sampler;

void main()
{
    fragColor = texture(sampler, vTexCoord);
}