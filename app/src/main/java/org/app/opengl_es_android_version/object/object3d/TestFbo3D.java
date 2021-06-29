package org.app.opengl_es_android_version.object.object3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.util.ShaderHelper;
import org.app.opengl_es_android_version.util.TextureHelper;

import java.nio.ByteBuffer;

public class TestFbo3D extends Object3D {

    //顶点坐标
    private float vertexPos[] = {
            -1.0f, 1.0f, 0f, 0f,
            -1.0f, -1.0f, 0f, 0f,
            1.0f, 1.0f, 0f, 0f,
            1.0f, -1.0f, 0f, 0f
    };

    //纹理坐标
    private float[] textureCoord = {
            0.0f, 0.0f, 0f, 0f,
            0.0f, 1.0f, 0f, 0f,
            1.0f, 0.0f, 0f, 0f,
            1.0f, 1.0f, 0f, 0f
    };

    private ByteBuffer indexArray = ByteBuffer.allocateDirect(6)
            .put(new byte[]{
                    0, 1, 2, 0, 2, 3
            });

    private VertexArray textureContainer, vertexContainer;

    int mFrameBufferProgram, mWindowProgram;

    int textureId;

    //这是什么？？？
    private ByteBuffer mBuffer;

    private int[] fFrame = new int[1];
    private int[] fRender = new int[1];
    private int[] fTexture = new int[2];

    public TestFbo3D(Context context) {
        super(context);
        vertexContainer = new VertexArray(vertexPos);
        textureContainer = new VertexArray(textureCoord);
        indexArray.position(0);
        textureId = TextureHelper.loadTexture(context, R.drawable.map1);
    }

    @Override
    public void bindData(Context context) {
        super.bindData(context);
        ShaderHelper.buildProgram(context, R.raw.fbo_vertex_shader, R.raw.fbo_fragment_shader);

        aPositionLocation = GLES20.glGetAttribLocation(mFrameBufferProgram, "aPosition");
        aTextureCoord = GLES20.glGetAttribLocation(mFrameBufferProgram, "aTextureCoord");
        int uTexture1 = GLES20.glGetUniformLocation(mFrameBufferProgram, "uTexture");
    }

    @Override
    public void unbind() {

    }

    @Override
    protected void draw() {
        createEnvi();
    }

    public void createEnvi() {
        Bitmap mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.map1);
        GLES20.glGenRenderbuffers(1, fRender, 0);
        GLES20.glGenFramebuffers(1, fFrame, 0);
        GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, fRender[0]);
        GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER, GLES20.GL_DEPTH_COMPONENT16,
                mBitmap.getWidth(), mBitmap.getHeight());
        GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT,
                GLES20.GL_RENDERBUFFER, fRender[0]);
        GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, 0);
        GLES20.glGenTextures(2, fTexture, 0);
        for (int i = 0; i < 2; i++) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, fTexture[i]);
            if (i == 0) {
                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, mBitmap, 0);
            } else {
                GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, mBitmap.getWidth(), mBitmap.getHeight(),
                        0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
            }
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        }
        mBuffer = ByteBuffer.allocate(mBitmap.getWidth() * mBitmap.getHeight() * 4);
    }
}
