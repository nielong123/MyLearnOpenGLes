uniform mat4 uMVPMatrix;//总变换矩阵
attribute vec3 aPosition;//顶点位置


void main() {
    gl_Position = uMVPMatrix * vec4(aPosition, 1.0);
}
