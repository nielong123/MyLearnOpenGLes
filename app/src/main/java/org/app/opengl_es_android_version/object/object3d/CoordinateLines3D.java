package org.app.opengl_es_android_version.object.object3d;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.util.ColorHelper;

import static android.opengl.GLES20.GL_DEPTH_TEST;

/***
 * 用折线来绘制一个圆形
 */
public class CoordinateLines3D extends Object3D {

    private static final int POSITION_COMPONENT_COUNT = 3;

    final private int count;

    public CoordinateLines3D() {
        super();
        vertexArray = new VertexArray(CoordinateLines);
        count = vertexArray.getFloatBuffer().limit() / 3;
    }

    float r = 3.0f;

    private float[] CoordinateLines = {
            0f, r, 0f,
            0f, -r, 0f,
            -r, 0f, 0f,
            r, 0f, 0f,
            0, 0f, -r,
            -0, 0f, r
    };

    @Override
    public void bindData(Context context) {
        super.bindData(context);
    }

    @Override
    protected void draw() {
        colorShaderProgram.userProgram();
        GLES20.glUniformMatrix4fv(colorShaderProgram.aMatrixLocation, 1, false, mvpMatrix, 0);
        //告诉opengl从缓冲区vertextData中取数据找到属性a_Position的数据
        vertexArray.enableVertexAttributePointer(colorShaderProgram.aPositionLocation, POSITION_COMPONENT_COUNT,
                0, 0);
        //使能顶点数组
        GLES20.glEnableVertexAttribArray(colorShaderProgram.aPositionLocation);
        ColorHelper.setColor(colorShaderProgram.aColorLocation, context.getColor(R.color.blue1));
        GLES20.glLineWidth(10);
        GLES20.glEnable(GL_DEPTH_TEST);
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, 2);
        GLES20.glLineWidth(1);
        ColorHelper.setColor(colorShaderProgram.aColorLocation, context.getColor(R.color.green1));
        GLES20.glDrawArrays(GLES20.GL_LINES, 2, 2);
        ColorHelper.setColor(colorShaderProgram.aColorLocation, context.getColor(R.color.red1));
        GLES20.glDrawArrays(GLES20.GL_LINES, 4, 2);
        GLES20.glDisable(GL_DEPTH_TEST);
    }

    @Override
    public void unbind() {
        GLES20.glDisableVertexAttribArray(colorShaderProgram.aPositionLocation);
        GLES20.glUseProgram(0);
    }

}
