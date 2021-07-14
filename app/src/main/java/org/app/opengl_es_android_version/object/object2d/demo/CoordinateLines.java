package org.app.opengl_es_android_version.object.object2d.demo;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.object.object2d.Object2D;
import org.app.opengl_es_android_version.util.ColorHelper;

import static android.opengl.GLES20.GL_FLOAT;

/***
 * 用折线来绘制一个圆形
 */
public class CoordinateLines extends Object2D {

    private static final int POSITION_COMPONENT_COUNT = 2;

    private int programId;
    private int aMatrixLocation;
    private int uColorLocation;
    private int aPositionLocation;
    Context context;

    final private int count;

    VertexArray vertexArray;

    public CoordinateLines(Context context) {
        super();
        this.context = context;
        vertexArray = new VertexArray(CoordinateLines);
        count = vertexArray.getFloatBuffer().limit() / POSITION_COMPONENT_COUNT;
    }

    float r = 3.0f;

    private float[] CoordinateLines = {
            -r, 0f,
            r, 0f,
            0f, r,
            0f, -r
    };

    @Override
    public void bindData(Context context) {
        super.bindData(context);
    }

    @Override
    public void draw() {
        colorShaderProgram.userProgram();
        GLES20.glUniformMatrix4fv(colorShaderProgram.aMatrixLocation, 1, false, mvpMatrix, 0);
        //告诉opengl从缓冲区vertextData中取数据找到属性a_Position的数据
        GLES20.glVertexAttribPointer(colorShaderProgram.aPositionLocation,
                POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexArray.getFloatBuffer());
        //使能顶点数组
        GLES20.glEnableVertexAttribArray(colorShaderProgram.aPositionLocation);
        ColorHelper.setColor(colorShaderProgram.aColorLocation, context.getColor(R.color.colorPrimary));
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, 2);
        ColorHelper.setColor(colorShaderProgram.aColorLocation, context.getColor(R.color.red1));
        GLES20.glDrawArrays(GLES20.GL_LINES, 2, 3);
    }

    @Override
    public void unbind() {
        GLES20.glUseProgram(0);
        GLES20.glDisableVertexAttribArray(aPositionLocation);
    }

}
