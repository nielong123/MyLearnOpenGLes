//设置工作精度
precision mediump float;

varying vec4 v_Color;

void main() {

    // gl_FragColor 内置变量，用于保存片元着色器计算完成的颜色值
    gl_FragColor = v_Color;
}
