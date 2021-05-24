#version 100
precision mediump float;
uniform vec4 u_Color;

void main()
{
    //    gl_FragColor = vec4(1, 0, 0, 1);
    gl_FragColor = u_Color;
}