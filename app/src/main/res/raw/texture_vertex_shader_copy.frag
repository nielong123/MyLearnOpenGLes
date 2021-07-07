#version 100
attribute vec4 a_Position;
attribute vec2 a_TexCoordinate;
varying vec2 v_TexCoord;
uniform mat4 u_Matrix;

void main()
{
    v_TexCoord = a_TexCoordinate;
    gl_Position = u_Matrix * a_Position;
}