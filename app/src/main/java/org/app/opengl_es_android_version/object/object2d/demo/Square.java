package org.app.opengl_es_android_version.object.object2d.demo;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.object.object2d.Object2D;
import org.app.opengl_es_android_version.util.ShaderHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


public class Square extends Object2D {

    //float类型的字节数
    private static final int BYTES_PER_FLOAT = 4;
    // 数组中每个顶点的坐标数
    static final int COORDS_PER_VERTEX = 2;
    //矩形顶点坐标
    static float squareCoords[] = {
            -0.5f, 0.5f,   // top left
            -0.5f, -0.5f,   // bottom left
            0.5f, -0.5f,   // bottom right
            0.5f, 0.5f      // top right
    };

    private FloatBuffer vertexBuffer;

    //------------第一步 : 定义两个标签，分别于着色器代码中的变量名相同,
    //------------第一个是顶点着色器的变量名，第二个是片段着色器的变量名
    private static final String A_POSITION = "a_Position";
    private static final String U_COLOR = "u_Color";

    //------------第二步: 定义两个ID,我们就是通ID来实现数据的传递的,这个与前面
    //------------获得program的ID的含义类似的
    private int uColorLocation;
    private int aPositionLocation;

    private int program;

    //---------第四步:定义坐标元素的个数，这里有三个顶点
    private static final int POSITION_COMPONENT_COUNT = 4;


    public Square() {
        vertexBuffer = ByteBuffer
                .allocateDirect(squareCoords.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        // 把坐标们加入FloatBuffer中
        vertexBuffer.put(squareCoords);
        // 设置buffer，从第一个坐标开始读
        vertexBuffer.position(0);
    }

    @Override
    public void bindData(Context context) {
        //链接glsl
        program = ShaderHelper.buildProgram(context, R.raw.simple_vertex_shader1_5, R.raw.simple_fragment_shader1_5);
        GLES20.glUseProgram(program);

        //----------第三步: 获取这两个ID ，是通过前面定义的标签获得的
        uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);

    }

    @Override
    public void draw() {
        //---------第五步: 传入数据
        GLES20.glVertexAttribPointer(aPositionLocation, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(aPositionLocation);
        GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, POSITION_COMPONENT_COUNT);
    }

    @Override
    public void draw(float[] viewProjectMatrix) {

    }
}
