package org.app.opengl_es_android_version.object.object2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

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
        super();
        vertexArray = new VertexArray(VERTEX_DATA);
        textureArray = new VertexArray(TEXTURE_DATA);

//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        opts.inScaled = false;
//        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.map1, opts);
        modelMatrix = new float[]{1, 0.2f, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
//        modelMatrix = new float[]{1, 0.2f, 0, 0, 0.5f, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
        indexArray.position(0);
    }

    @Override
    public void bindData(Context context) {
        programId = ShaderHelper.buildProgram(context,
                R.raw.texture_vertex_shader_copy, R.raw.texture_fragment_shader_copy);
        GLES20.glUseProgram(programId);
        aTextureCoordinateLocation = GLES20.glGetAttribLocation(programId, Constants.A_COORDINATE);
        textureLoc = GLES20.glGetUniformLocation(programId, Constants.U_TEXTURE);
        aMatrixLocation = GLES20.glGetUniformLocation(programId, Constants.U_MATRIX);
        //获取属性位置
        aPositionLocation = GLES20.glGetAttribLocation(programId, Constants.A_POSITION);
        textureId = TextureHelper.loadTexture(context, R.mipmap.start);
//        textureId = TextureHelper.loadTexture(context, R.drawable.map1);
//        textureId = createTexture();
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
        GLES20.glUniformMatrix4fv(aMatrixLocation, 1, false, modelMatrix, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glUniform1i(textureLoc, 0);
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_FAN, indexArray.limit(), GL_UNSIGNED_BYTE, indexArray);
    }

    @Override
    public void draw(float[] viewProjectMatrix) {
        draw();
    }

    private int createTexture() {
        int[] texture = new int[1];
        if (bmp != null && !bmp.isRecycled()) {
            //生成纹理
            GLES20.glGenTextures(1, texture, 0);
            //生成纹理
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);
            //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            //根据以上指定的参数，生成一个2D纹理
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);
            bmp.recycle();
            return texture[0];
        }
        return 0;
    }
}
