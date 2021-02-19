package org.app.opengl_es_android_version.object.object2d;

import android.opengl.GLES20;

import org.app.opengl_es_android_version.contant.Constants;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.object.Object;
import org.app.opengl_es_android_version.program.ColorShaderProgram;
import org.app.opengl_es_android_version.program.TextureShaderProgram;

public class Triangle extends Object {

    private final VertexArray vertexArray;

    static final int POSITION_COMPONENT_COUNT = 3;

    private static final int STRIDE = POSITION_COMPONENT_COUNT * Constants.POSITION_COMPONENT_COUNT;

    public Triangle() {
        vertexArray = new VertexArray(tableVerticesWithTriangles);
    }

    private float[] tableVerticesWithTriangles = {

            -0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f
    };

    @Override
    public void bindData(ColorShaderProgram colorShaderProgram) {

    }

    @Override
    public void bindData(TextureShaderProgram shaderProgram) {
        vertexArray.setVertexAttributePointer(
                shaderProgram.aPositionLocation,
                POSITION_COMPONENT_COUNT,
                STRIDE,
                0
        );
    }

    @Override
    public void draw() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, POSITION_COMPONENT_COUNT);
    }

}
