precision mediump float;

uniform sampler2D u_TextTureUnit;
varying vec2 v_TextureCoordinates;

void main()
{
    gl_FragColor = texture2D(u_TextTureUnit,v_TextureCoordinates);
}