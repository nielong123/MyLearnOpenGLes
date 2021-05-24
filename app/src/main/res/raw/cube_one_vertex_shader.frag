#version 100

//uniform = 全局只读,mat4  = 浮点类型的4维矩阵
uniform mat4 u_Matrix;

//attribute = 只能用于顶点着色器中，不能用于片元着色器。 一般用该变量来表示一些顶点数据
//vec4 = 4维浮点向量
attribute vec4 a_Position;

attribute vec4 a_Color;

//varying = 类型变量 是从顶点着色器传递到片元着色器的数据变量
varying vec4 v_Color;


void main() {
    v_Color = a_Color;
    //gl_Position = 内置特殊变量，表示变换后点的空间位置，单位vec4
    gl_Position = u_Matrix * a_Position;
    //gl_PointSize = 内置特殊变量，需要绘制点的大小
    gl_PointSize = 10.0;
}
