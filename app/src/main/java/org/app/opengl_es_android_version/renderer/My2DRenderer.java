package org.app.opengl_es_android_version.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.object.object2d.Triangle;
import org.app.opengl_es_android_version.program.ColorShaderProgram;
import org.app.opengl_es_android_version.program.TextureShaderProgram;
import org.app.opengl_es_android_version.util.ShaderHelper;
import org.app.opengl_es_android_version.util.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TRIANGLES;


/***
 * 绘制一个三角形，可运行版本
 */

public class My2DRenderer implements GLSurfaceView.Renderer {

    private final Context context;

    private final FloatBuffer vertexData;
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int BYTES_PER_FLOAT = 4;
    private int program;//存储链接程序的ID
    private static final String U_COLOR = "u_Color";
    private int uColorLocation;
    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;


    public My2DRenderer(Context context) {
        this.context = context;

        float[] triangleVertices = {
                // 第一个三角形
                0f, 0.5f,
                -0.5f, 0f,
                0.5f, 0
        };
        vertexData = ByteBuffer.allocateDirect(triangleVertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(triangleVertices);//本地内存缓冲区
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader1_5);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader1_5);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        //链接着色器
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        //验证程序对于opengl是否有效  debug
        ShaderHelper.validateProgram(program);
        //告诉opengl绘制任何东西到屏幕上需要使用这里定义的程序
        GLES20.glUseProgram(program);
        //获取uniform的位置，把位置存入uColorLocation中
        uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR);
        //获取属性位置
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        //关联属性与顶点数据数组的数组
        //vertextData是我们在本地内存中创建的一个缓冲区，存的是位置
        //确保缓冲区从头开始读数据，置0
        vertexData.position(0);
        //告诉opengl从缓冲区vertextData中取数据找到属性a_Position的数据
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
        //使能顶点数组
        GLES20.glEnableVertexAttribArray(aPositionLocation);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
//        //绘制桌子
        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        //画个三角形，每个三角形有三个顶点，2个三角形6个顶点
        GLES20.glDrawArrays(GL_TRIANGLES, 0, 3);

    }
}
