package org.app.opengl_es_android_version.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import org.app.opengl_es_android_version.object.Object;
import org.app.opengl_es_android_version.object.object2d.Triangle;
import org.app.opengl_es_android_version.program.ColorShaderProgram;
import org.app.opengl_es_android_version.program.TriangleTextureShaderProgram;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class My2DRenderer_1 implements GLSurfaceView.Renderer {

    private final Context context;

    List<Object> drawObjectList = new ArrayList<>();

    TriangleTextureShaderProgram textureShaderProgram;
    ColorShaderProgram colorShaderProgram;

    Triangle triangle, triangle1;

//    FloatBuffer vertexData;

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int BYTES_PER_FLOAT = 4;
    private int program;//存储链接程序的ID
    private static final String U_COLOR = "u_Color";

    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;


    public My2DRenderer_1(Context context) {
        this.context = context;

        triangle = new Triangle();
        drawObjectList.add(triangle);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        textureShaderProgram = new TriangleTextureShaderProgram(context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        textureShaderProgram.userProgram();

        triangle.bindData(textureShaderProgram);
        for (Object object : drawObjectList) {
            object.draw();
        }


//        GLES20.glDrawArrays(GL_TRIANGLES, 2, 3);
//        GLES20.glLineWidth(10);
//        GLES20.glDrawArrays(GL_LINES, 6, 2);
//        GLES20.glDrawArrays(GL_LINES, 8, 2);
//        triangle.draw();
    }
}
