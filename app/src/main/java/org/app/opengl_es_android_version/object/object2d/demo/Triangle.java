package org.app.opengl_es_android_version.object.object2d.demo;


import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.contant.Constants;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.object.object2d.Object2D;

import static android.opengl.GLES20.GL_TRIANGLES;

public class Triangle extends Object2D {

    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int STRIDE = POSITION_COMPONENT_COUNT * Constants.POSITION_COMPONENT_COUNT;

    private final VertexArray vertexArray;

    public Triangle() {
        super();
        vertexArray = new VertexArray(tableVerticesWithTriangles);
    }

    public Triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        super();
        tableVerticesWithTriangles[0] = x1;
        tableVerticesWithTriangles[1] = y1;
        tableVerticesWithTriangles[2] = x2;
        tableVerticesWithTriangles[3] = y2;
        tableVerticesWithTriangles[4] = x3;
        tableVerticesWithTriangles[5] = y3;
        vertexArray = new VertexArray(tableVerticesWithTriangles);
    }

    private float[] tableVerticesWithTriangles = {

            -0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f
    };


    @Override
    public void bindData(Context context) {
        super.bindData(context);
    }

    @Override
    public void draw() {
        colorShaderProgram.userProgram();
        GLES20.glUniformMatrix4fv(colorShaderProgram.aMatrixLocation, 1, false, mvpMatrix, 0);
        GLES20.glVertexAttribPointer(colorShaderProgram.aPositionLocation, 2, GLES20.GL_FLOAT,
                false, 0, vertexArray.getFloatBuffer());
        GLES20.glEnableVertexAttribArray(colorShaderProgram.aPositionLocation);
        GLES20.glUniform4f(colorShaderProgram.aColorLocation, 1.0f, 3.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GL_TRIANGLES, 0, 3);
    }

    @Override
    public void unbind() {
        GLES20.glUseProgram(0);
        GLES20.glDisableVertexAttribArray(colorShaderProgram.aPositionLocation);
    }

}
