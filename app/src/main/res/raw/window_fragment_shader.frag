#version 100

precision mediump float;
uniform sampler2D uTexture;
varying vec2 vTextureCoord;
void main(){
    gl_FragColor = texture2D(uTexture, vTextureCoord);
}