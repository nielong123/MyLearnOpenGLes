package org.app.opengl_es_android_version.object.object2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.contant.Constants;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.util.ShaderHelper;
import org.app.opengl_es_android_version.util.TextureHelper;

import java.nio.ByteBuffer;

import static android.opengl.GLES20.GL_UNSIGNED_BYTE;

public class RectangleWithTexture extends Object2D {

    private Bitmap bmp;

    private static final int POSITION_COMPONENT_COUNT = 2;

    private int aPositionLocation;

    private int aTextureCoordinateLocation;

    private int aMatrixLocation;

    private int textureLoc;

    private int programId;

    private int textureId;

    private static final float r = 0.6f;

    //绘图顶点
    private static final float[] VERTEX_DATA = {
            -r, r,   // top left
            -r, -r,   // bottom left
            r, -r,   // bottom right
            r, r  // top right
    };

    private static final float c = 1f;

    //图片上的顶点
    private static final float[] TEXTURE_DATA = {
            0.0f, 0.0f,
            0.0f, c,
            c, c,
            c, 0.0f
    };

    private ByteBuffer indexArray = ByteBuffer.allocateDirect(6)
            .put(new byte[]{
                    0, 1, 2, 0, 2, 3
            });


    private final VertexArray vertexArray;
    private final VertexArray textureArray;

    public RectangleWithTexture(Context context) {
        super(context);
        vertexArray = new VertexArray(VERTEX_DATA);
        textureArray = new VertexArray(TEXTURE_DATA);

        indexArray.position(0);
    }

    @Override
    public void bindData(Context context) {
        super.bindData(context);
        programId = ShaderHelper.buildProgram(context,
                R.raw.texture_vertex_shader_copy, R.raw.texture_fragment_shader_copy);
        GLES20.glUseProgram(programId);
        aTextureCoordinateLocation = GLES20.glGetAttribLocation(programId, Constants.A_COORDINATE);
        textureLoc = GLES20.glGetUniformLocation(programId, Constants.U_TEXTURE);
        aMatrixLocation = GLES20.glGetUniformLocation(programId, Constants.U_MATRIX);
        aPositionLocation = GLES20.glGetAttribLocation(programId, Constants.A_POSITION);
        textureId = TextureHelper.loadTexture(context, R.mipmap.start);
    }

    @Override
    public void draw() {
        GLES20.glEnableVertexAttribArray(aPositionLocation);
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 0, vertexArray.getFloatBuffer());
        vertexArray.getFloatBuffer().position(0);

        GLES20.glEnableVertexAttribArray(aTextureCoordinateLocation);
        GLES20.glVertexAttribPointer(aTextureCoordinateLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 0, textureArray.getFloatBuffer());
        textureArray.getFloatBuffer().position(0);
        //设置纹理
        GLES20.glUniformMatrix4fv(aMatrixLocation, 1, false, mvpMatrix, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glUniform1i(textureLoc, 0);
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_FAN, indexArray.limit(), GL_UNSIGNED_BYTE, indexArray);
    }

    @Override
    public void unbind() {
        GLES20.glDisableVertexAttribArray(aPositionLocation);
        GLES20.glDisableVertexAttribArray(aTextureCoordinateLocation);
    }
}
