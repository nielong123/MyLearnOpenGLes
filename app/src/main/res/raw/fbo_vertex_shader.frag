#version 100

attribute vec2 aPosition;
attribute vec2 aTextureCoord;
varying vec2 vTextureCoord;

void main()
{
    gl_Position = vec4(aPosition, 0, 1);
    vTextureCoord = aTextureCoord;
}