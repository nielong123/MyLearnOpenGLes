package org.app.opengl_es_android_version.object.object3d;

import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.util.ColorHelper;

public class Point3D extends Object3D {

    VertexArray vertexArray;

    float[] vertexData = new float[8];

    public Point3D(float x, float y, float z) {
        super();
        vertexData[0] = x;
        vertexData[1] = y;
        vertexData[2] = z;
        vertexArray = new VertexArray(vertexData);
    }

    @Override
    protected void draw() {
        colorShaderProgram.userProgram();
        GLES20.glUniform1f(colorShaderProgram.aPointSizeLocation, 10);
        GLES20.glUniformMatrix4fv(colorShaderProgram.aMatrixLocation, 1, false, mvpMatrix, 0);
        ColorHelper.setColor(colorShaderProgram.aColorLocation, context.getColor(R.color.red1));
        vertexArray.enableVertexAttributePointer(colorShaderProgram.aPositionLocation, 3,
                0, 0);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 2);
        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
    }

    @Override
    public void unbind() {
        GLES20.glDisableVertexAttribArray(colorShaderProgram.aPositionLocation);
        GLES20.glUseProgram(0);
    }
}
