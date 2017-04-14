#version 120

in vec2 vTexCoord;
out vec4 fragColor;

uniform sampler2D sampler;

void main()
{
    gl_fragColor = texture(sampler, vTexCoord);
}