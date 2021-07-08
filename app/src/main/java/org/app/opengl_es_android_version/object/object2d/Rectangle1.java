package org.app.opengl_es_android_version.object.object2d;

import android.content.Context;
import android.opengl.GLES20;

import androidx.core.graphics.ColorUtils;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.util.ColorHelper;

import java.nio.ByteBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_UNSIGNED_BYTE;

public class Rectangle1 extends Object2D {

    private static final int POSITION_COMPONENT_COUNT = 2;

    private static final float r = 0.3f;

    private static final float[] VERTEX_DATA = {
            -r, -r,
            r, -r,
            r, r,
            -r, r
    };

    private ByteBuffer indexArray = ByteBuffer.allocateDirect(6)
            .put(new byte[]{
                    0, 1, 2, 2, 3, 0
            });

    private final VertexArray vertexArray;

    public Rectangle1() {
        super();
        vertexArray = new VertexArray(VERTEX_DATA);
        indexArray.position(0);
    }

    @Override
    public void bindData(Context context) {
        super.bindData(context);
    }

    @Override
    public void draw() {
        GLES20.glUniformMatrix4fv(colorShaderProgram.aMatrixLocation, 1, false, mvpMatrix, 0);
        GLES20.glVertexAttribPointer(colorShaderProgram.aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexArray.getFloatBuffer());
        GLES20.glEnableVertexAttribArray(colorShaderProgram.aPositionLocation);
        ColorHelper.setColor(colorShaderProgram.aColorLocation,context.getColor(R.color.blue1));
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_FAN, indexArray.limit(), GL_UNSIGNED_BYTE, indexArray);
    }

    @Override
    public void unbind() {
        GLES20.glDisableVertexAttribArray(colorShaderProgram.aPositionLocation);
    }
}
