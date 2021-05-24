#version 100
precision mediump float;

uniform sampler2D u_TextTureUnit;
varying vec2 v_TextureCoordinates;

void main()
{
    gl_FragColor = texture2D(u_TextTureUnit,v_TextureCoordinates);
}


//precision mediump float;
//void main() {
//    gl_FragColor = vec4(0.5,0,0,1);
//}