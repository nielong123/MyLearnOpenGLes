package org.app.opengl_es_android_version.object;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.object.object2d.Object2D;
import org.app.opengl_es_android_version.renderer.MyFBORenderer;
import org.app.opengl_es_android_version.util.ShaderHelper;
import org.app.opengl_es_android_version.util.TextureHelper;

import java.nio.FloatBuffer;

public class Shape_FBO extends Object2D {

    private static String TAG = "ShapeFBO";

    private FloatBuffer mSqureBuffer;

    private FloatBuffer mSqureBufferfbo;

    private int mFrameBufferProgram;

    private int mWindowProgram;

    private int mLoadedTextureId;

    private Context mContext;

    float r = 1f;

    float f = 1f;

    public Shape_FBO(Context context) {
        super(context);
        this.mContext = context;
        this.initVetexData();
    }

    public void initVetexData() {

        mLoadedTextureId = TextureHelper.loadTexture(mContext, R.drawable.map1);

        float[] bgVertex = new float[]{
//                -r, -r, 0, 0,
//                -r, r, 0, 0,
//                r, -r, 0, 0,
//                r, r, 0, 0
                -r / 2, -r / 2, 0, r,
                -r / 2, r / 2, 0, 0,
                r / 2, -r / 2, r, r,
                r / 2, r / 2, r, 0
        };
        mSqureBuffer = new VertexArray(bgVertex).getFloatBuffer();

        float[] fboVertex = new float[]{
                -f, -f, 0, f,
                -f, f, 0, 0,
                f, -f, f, f,
                f, f, f, 0
        };
        mSqureBufferfbo = new VertexArray(fboVertex).getFloatBuffer();
    }

    @Override
    public void bindData(Context context) {
        super.bindData(context);
        mFrameBufferProgram = ShaderHelper.buildProgram(context, R.raw.fbo_vertex_shader, R.raw.fbo_fragment_shader);
        mWindowProgram = ShaderHelper.buildProgram(context, R.raw.window_vertex_shader, R.raw.window_fragment_shader);
    }

    @Override
    public void draw() {

        GLES20.glUseProgram(mFrameBufferProgram);
        GLES20.glUseProgram(mWindowProgram);

//        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        // 生成FrameBuffer
        int[] framebuffers = new int[1];
        GLES20.glGenFramebuffers(1, framebuffers, 0);
        //绑定framebuffer
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, framebuffers[0]);

        // 生成Texture
        int[] textures = new int[2];
        GLES20.glGenTextures(2, textures, 0);
        int colorTxtureId = textures[0];
        //绑定纹理缓存到纹理单元
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, colorTxtureId);
        //设置采样，拉伸方式
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_MIRRORED_REPEAT);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_MIRRORED_REPEAT);
        //生成2D纹理

        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB, MyFBORenderer.screenWidth * 2, MyFBORenderer.screenHeight * 2, 0, GLES20.GL_RGB, GLES20.GL_UNSIGNED_SHORT_5_6_5, null);

        //挂载textureID到framebufferId
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, colorTxtureId, 0);

        if (GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER) == GLES20.GL_FRAMEBUFFER_COMPLETE) {
            Log.e("shapefbo", "glFramebufferTexture2D error");
        }
        int positionHandle1 = GLES20.glGetAttribLocation(mFrameBufferProgram, "aPosition");
        int textureCoordHandle1 = GLES20.glGetAttribLocation(mFrameBufferProgram, "aTextureCoord");
        int textureHandle1 = GLES20.glGetUniformLocation(mFrameBufferProgram, "uTexture");
        mSqureBufferfbo.position(0);
        GLES20.glVertexAttribPointer(positionHandle1, 2, GLES20.GL_FLOAT, false, (2 + 2) * 4, mSqureBufferfbo);
        mSqureBufferfbo.position(2);
        GLES20.glVertexAttribPointer(textureCoordHandle1, 2, GLES20.GL_FLOAT, false, (2 + 2) * 4, mSqureBufferfbo);
        GLES20.glEnableVertexAttribArray(positionHandle1);
        GLES20.glEnableVertexAttribArray(textureCoordHandle1);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);//todo
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mLoadedTextureId);

        GLES20.glUniform1i(textureHandle1, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);

        /*================================================================*/
        // 切换到窗口系统的缓冲区

        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        int positionHandle = GLES20.glGetAttribLocation(mWindowProgram, "aPosition");
        int textureCoordHandle = GLES20.glGetAttribLocation(mWindowProgram, "aTextureCoord");
        int textureHandle = GLES20.glGetUniformLocation(mWindowProgram, "uTexture");
        mSqureBuffer.position(0);
        GLES20.glVertexAttribPointer(positionHandle, 2, GLES20.GL_FLOAT, false, (2 + 2) * 4, mSqureBuffer);
        mSqureBuffer.position(2);
        GLES20.glVertexAttribPointer(textureCoordHandle, 2, GLES20.GL_FLOAT, false, (2 + 2) * 4, mSqureBuffer);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glEnableVertexAttribArray(textureCoordHandle);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, colorTxtureId);
        GLES20.glUniform1i(textureHandle, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glDeleteTextures(2, textures, 0);
        GLES20.glDeleteFramebuffers(1, framebuffers, 0);
    }

    @Override
    public void unbind() {
//        GLES20.glDisable(GLES20.GL_TEXTURE_2D);
//        GLES20.glEnableVertexAttribArray(positionHandle1);
//        GLES20.glEnableVertexAttribArray(textureCoordHandle1);
    }

}
