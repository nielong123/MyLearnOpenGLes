package org.app.opengl_es_android_version.object.object2d.demo;


import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.contant.Constants;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.object.object2d.Object2D;
import org.app.opengl_es_android_version.util.ShaderHelper;

import static android.opengl.GLES20.GL_TRIANGLES;

public class Triangle extends Object2D {

    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int STRIDE = POSITION_COMPONENT_COUNT * Constants.POSITION_COMPONENT_COUNT;


    private int programId;
    private int uColorLocation;
    private int aPositionLocation;
    private int aMatrixLocation;
    private final VertexArray vertexArray;

    public Triangle(Context context) {
        super(context);
        vertexArray = new VertexArray(tableVerticesWithTriangles);
    }

    public Triangle(Context context, float x1, float y1, float x2, float y2, float x3, float y3) {
        super(context);
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
        programId = ShaderHelper.buildProgram(context,
                R.raw.texture_vertex_shader_copy, R.raw.simple_fragment_shader1_5);
        GLES20.glUseProgram(programId);
        uColorLocation = GLES20.glGetUniformLocation(programId, Constants.U_COLOR);
        aPositionLocation = GLES20.glGetAttribLocation(programId, Constants.A_POSITION);
        aMatrixLocation = GLES20.glGetAttribLocation(programId, Constants.U_MATRIX);
    }

    @Override
    public void draw() {
        GLES20.glUniformMatrix4fv(aMatrixLocation, 1, false, mvpMatrix, 0);
        GLES20.glVertexAttribPointer(aPositionLocation, 2, GLES20.GL_FLOAT,
                false, 0, vertexArray.getFloatBuffer());
        GLES20.glEnableVertexAttribArray(aPositionLocation);
        GLES20.glUniform4f(uColorLocation, 1.0f, 3.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GL_TRIANGLES, 0, 3);
    }

    @Override
    public void unbind() {
        GLES20.glDisableVertexAttribArray(uColorLocation);
        GLES20.glDisableVertexAttribArray(aPositionLocation);
        GLES20.glDisableVertexAttribArray(aMatrixLocation);
    }

}
