#version 100
attribute vec4 a_Position;
attribute vec2 a_TexCoordinate;
uniform mat4 u_Matrix;
uniform float m_pointSize;

void main()
{
    gl_PointSize = m_pointSize;
//    gl_PointSize = 32.0;
    gl_Position = u_Matrix * a_Position;
}