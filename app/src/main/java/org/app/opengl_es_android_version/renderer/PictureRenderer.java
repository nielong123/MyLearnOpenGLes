package org.app.opengl_es_android_version.renderer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import org.app.opengl_es_android_version.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class PictureRenderer implements GLSurfaceView.Renderer {

    // Our matrices
    private final float[] mtrxProjection = new float[16];
    private final float[] mtrxView = new float[16];
    private final float[] mtrxProjectionAndView = new float[16];

    // Geometric variables
    //第一张图片所对应的位置数组
    public float vertices[] = new float[]

            //这里的适配问题需要dp和px的转化
            {
                    0.0f, 1184f, 0.0f,
                    0.0f, 928f, 0.0f,
                    256f, 928f, 0.0f,
                    256f, 1184f, 0.0f,
            };
    //第二张图片所对应的位置数组
    public static float vertices1[] = new float[]
            {
                    256f, 1184f, 0.0f,
                    256f, 928f, 0.0f,
                    512f, 928f, 0.0f,
                    512f, 1184f, 0.0f,
            };


    //第三张图片所对应的位置数组
    public static float vertices2[] = new float[]
            {
                    512f, 1184f, 0.0f,
                    512f, 928f, 0.0f,
                    768f, 928f, 0.0f,
                    768f, 1184f, 0.0f,
            };


    //第四张图片所对应的位置数组
    public static float vertices3[] = new float[]
            {
                    0.0f, 928f, 0.0f,
                    0.0f, 672f, 0.0f,
                    256f, 672f, 0.0f,
                    256f, 928f, 0.0f,
            };


    //第五张图片所对应的位置数组
    public static float vertices4[] = new float[]
            {
                    256f, 928f, 0.0f,
                    256f, 672f, 0.0f,
                    512f, 672f, 0.0f,
                    512f, 928f, 0.0f,
            };

    //第六张图片所对应的位置数组
    public static float vertices5[] = new float[]
            {
                    512f, 928f, 0.0f,
                    512f, 672f, 0.0f,
                    768f, 672f, 0.0f,
                    768f, 928f, 0.0f,
            };

    //第7张图片所对应的位置数组
    public static float vertices6[] = new float[]
            {
                    0f, 672f, 0.0f,
                    0f, 416f, 0.0f,
                    256f, 416f, 0.0f,
                    256f, 672f, 0.0f,
            };

    //第8张图片所对应的位置数组
    public static float vertices7[] = new float[]
            {
                    256f, 672f, 0.0f,
                    256f, 416f, 0.0f,
                    512f, 416f, 0.0f,
                    512f, 672f, 0.0f,
            };

    //第9张图片所对应的位置数组
    public static float vertices8[] = new float[]
            {
                    512f, 672f, 0.0f,
                    512f, 416f, 0.0f,
                    768f, 416f, 0.0f,
                    768f, 672f, 0.0f,
            };

    //第10张图片所对应的位置数组
    public static float vertices9[] = new float[]
            {
                    0.0f, 416f, 0.0f,
                    0.0f, 160f, 0.0f,
                    256f, 160f, 0.0f,
                    256f, 416f, 0.0f,
            };

    //第11张图片所对应的位置数组
    public static float vertices10[] = new float[]
            {
                    256f, 416f, 0.0f,
                    256f, 160f, 0.0f,
                    512f, 160f, 0.0f,
                    512f, 416f, 0.0f,
            };

    //第12张图片所对应的位置数组
    public static float vertices11[] = new float[]
            {
                    512f, 416f, 0.0f,
                    512f, 160f, 0.0f,
                    768f, 160f, 0.0f,
                    768f, 416f, 0.0f,
            };

    //第13张图片所对应的位置数组
    public static float vertices12[] = new float[]
            {
                    0.0f, 160f, 0.0f,
                    0.0f, -96f, 0.0f,
                    256f, -96f, 0.0f,
                    256f, 160f, 0.0f,
            };

    //第14张图片所对应的位置数组
    public static float vertices13[] = new float[]
            {
                    256f, 160f, 0.0f,
                    256f, -96f, 0.0f,
                    512f, -96f, 0.0f,
                    512f, 160f, 0.0f,
            };

    //第15张图片所对应的位置数组
    public static float vertices14[] = new float[]
            {
                    512f, 160f, 0.0f,
                    512f, -96f, 0.0f,
                    768f, -96f, 0.0f,
                    768f, 160f, 0.0f,
            };


    public static short indices[];

    public static float uvs[];
    public FloatBuffer vertexBuffer;
    public ShortBuffer drawListBuffer;
    public FloatBuffer uvBuffer;

    float mScreenWidth = 1280;
    float mScreenHeight = 768;

    // Misc
    Context mContext;
    long mLastTime;
    private FloatBuffer mUvBuffer1;
    private FloatBuffer mVerticeBuffer;

    public PictureRenderer(Context c) {
        mContext = c;
        mLastTime = System.currentTimeMillis() + 100;
    }

    public void onResume() {
        /* Do stuff to resume the renderer */
        mLastTime = System.currentTimeMillis();
    }

    @Override
    public void onDrawFrame(GL10 unused) {

        // Get the current time
        long now = System.currentTimeMillis();

        // We should make sure we are valid and sane
        if (mLastTime > now) return;

        // Get the amount of time the last frame took.
        long elapsed = now - mLastTime;

        // Update our example

        // Render our example
        Render(mtrxProjectionAndView);

        // Save the current time to see how long it took :).
        mLastTime = now;

    }

    private void Render(float[] m) {

        // clear Screen and Depth Buffer, we have set the clear color as black.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        //绘制第一张图片的所有操作

//        // get handle to vertex shader's vPosition member
        int mPositionHandle = GLES20.glGetAttribLocation(riGraphicTools.sp_Image, "vPosition");
//        // Get handle to texture coordinates location
        int mTexCoordLoc = GLES20.glGetAttribLocation(riGraphicTools.sp_Image, "a_texCoord");
        //绘制第二张图片
        // get handle to vertex shader's vPosition member
        //拼接图片  one
        // Create the triangles
        mVerticeBuffer = setupTriangle(vertices);
        // Create the image information
        mUvBuffer1 = setupImage(R.drawable.map1);
        drawBitmap(m, mPositionHandle, mTexCoordLoc, mVerticeBuffer, mUvBuffer1, indices);

        FloatBuffer VertexPositionBuffer20 = setupTriangle(vertices);
        FloatBuffer uvBuffer20 = setupImage(R.drawable.map1);

        drawBitmap(m, mPositionHandle, mTexCoordLoc, VertexPositionBuffer20, uvBuffer20, indices);

        //拼接图片  two
        FloatBuffer VertexPositionBuffer1 = setupTriangle(vertices1);
        FloatBuffer uvBuffer1 = setupImage(R.drawable.map1);

        drawBitmap(m, mPositionHandle, mTexCoordLoc, VertexPositionBuffer1, uvBuffer1, indices);

        //拼接图片  three
        FloatBuffer VertexPositionBuffer2 = setupTriangle(vertices2);
        FloatBuffer uvBuffer2 = setupImage(R.drawable.map1);

        drawBitmap(m, mPositionHandle, mTexCoordLoc, VertexPositionBuffer2, uvBuffer2, indices);

        //拼接图片  four
        FloatBuffer VertexPositionBuffer3 = setupTriangle(vertices3);
        FloatBuffer uvBuffer3 = setupImage(R.drawable.map1);

        drawBitmap(m, mPositionHandle, mTexCoordLoc, VertexPositionBuffer3, uvBuffer3, indices);

        //拼接图片  five
        FloatBuffer VertexPositionBuffer4 = setupTriangle(vertices4);
        FloatBuffer uvBuffer4 = setupImage(R.drawable.map1);

        drawBitmap(m, mPositionHandle, mTexCoordLoc, VertexPositionBuffer4, uvBuffer4, indices);

        //拼接图片  six
        FloatBuffer VertexPositionBuffer5 = setupTriangle(vertices5);
        FloatBuffer uvBuffer5 = setupImage(R.drawable.map1);

        drawBitmap(m, mPositionHandle, mTexCoordLoc, VertexPositionBuffer5, uvBuffer5, indices);

        //拼接图片  7
        FloatBuffer VertexPositionBuffer6 = setupTriangle(vertices6);
        FloatBuffer uvBuffer6 = setupImage(R.drawable.map1);

        drawBitmap(m, mPositionHandle, mTexCoordLoc, VertexPositionBuffer6, uvBuffer6, indices);

        //拼接图片  8
        FloatBuffer VertexPositionBuffer7 = setupTriangle(vertices7);
        FloatBuffer uvBuffer7 = setupImage(R.drawable.map1);

        drawBitmap(m, mPositionHandle, mTexCoordLoc, VertexPositionBuffer7, uvBuffer7, indices);

        //拼接图片  9
        FloatBuffer VertexPositionBuffer8 = setupTriangle(vertices8);
        FloatBuffer uvBuffer8 = setupImage(R.drawable.map1);

        drawBitmap(m, mPositionHandle, mTexCoordLoc, VertexPositionBuffer8, uvBuffer8, indices);

        //拼接图片  10
        FloatBuffer VertexPositionBuffer9 = setupTriangle(vertices9);
        FloatBuffer uvBuffer9 = setupImage(R.drawable.map1);

        drawBitmap(m, mPositionHandle, mTexCoordLoc, VertexPositionBuffer9, uvBuffer9, indices);

        //拼接图片  11
        FloatBuffer VertexPositionBuffer10 = setupTriangle(vertices10);
        FloatBuffer uvBuffer10 = setupImage(R.drawable.map1);

        drawBitmap(m, mPositionHandle, mTexCoordLoc, VertexPositionBuffer10, uvBuffer10, indices);

        //拼接图片  12
        FloatBuffer VertexPositionBuffer11 = setupTriangle(vertices11);
        FloatBuffer uvBuffer11 = setupImage(R.drawable.map1);

        drawBitmap(m, mPositionHandle, mTexCoordLoc, VertexPositionBuffer11, uvBuffer11, indices);

        //拼接图片  13
        FloatBuffer VertexPositionBuffer12 = setupTriangle(vertices12);
        FloatBuffer uvBuffer12 = setupImage(R.drawable.map1);

        drawBitmap(m, mPositionHandle, mTexCoordLoc, VertexPositionBuffer12, uvBuffer12, indices);

        //拼接图片  14
        FloatBuffer VertexPositionBuffer13 = setupTriangle(vertices13);
        FloatBuffer uvBuffer13 = setupImage(R.drawable.map1);

        drawBitmap(m, mPositionHandle, mTexCoordLoc, VertexPositionBuffer13, uvBuffer13, indices);

        //拼接图片  15
        FloatBuffer VertexPositionBuffer14 = setupTriangle(vertices14);
        FloatBuffer uvBuffer14 = setupImage(R.drawable.map1);

        drawBitmap(m, mPositionHandle, mTexCoordLoc, VertexPositionBuffer14, uvBuffer14, indices);
    }

    private void drawBitmap(float[] m, int mPositionHandle, int mTexCoordLoc, FloatBuffer vertexBufferPosition, FloatBuffer uvBuffer1, short[] shortsIndices) {
        int mPositionHandle1 = GLES20.glGetAttribLocation(riGraphicTools.sp_Image, "vPosition");

        // Enable generic vertex attribute array
        GLES20.glEnableVertexAttribArray(mPositionHandle1);

        // Prepare the triangle coordinate data   绘制了两个三角形
        GLES20.glVertexAttribPointer(mPositionHandle, 3,
                GLES20.GL_FLOAT, false,
                0, vertexBufferPosition);

        // Get handle to texture coordinates location
        int mTexCoordLoc1 = GLES20.glGetAttribLocation(riGraphicTools.sp_Image, "a_texCoord");

        // Enable generic vertex attribute array
        GLES20.glEnableVertexAttribArray(mTexCoordLoc1);

        // Prepare the texturecoordinates
        GLES20.glVertexAttribPointer(mTexCoordLoc, 2, GLES20.GL_FLOAT,
                false,
                0, uvBuffer1);

        // Get handle to shape's transformation matrix
        int mtrxhandle1 = GLES20.glGetUniformLocation(riGraphicTools.sp_Image, "uMVPMatrix");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mtrxhandle1, 1, false, m, 0);

        // Get handle to textures locations
        int mSamplerLoc1 = GLES20.glGetUniformLocation(riGraphicTools.sp_Image, "s_texture");

        // Set the sampler texture unit to 0, where we have saved the texture.
        GLES20.glUniform1i(mSamplerLoc1, 0);

        // Draw the triangle
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, shortsIndices.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTexCoordLoc);
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        // We need to know the current width and height.
        mScreenWidth = width;
        mScreenHeight = height;

        // Redo the Viewport, making it fullscreen.
        GLES20.glViewport(0, 0, (int) width, (int) height);

        // Clear our matrices
        Matrix.setIdentityM(mtrxProjection, 0);
        Matrix.setIdentityM(mtrxView, 0);
        Matrix.setIdentityM(mtrxProjectionAndView, 0);
        //矩阵的偏移
        // Setup our screen width and height for normal sprite translation.
        Matrix.orthoM(mtrxProjection, 0, 0f, mScreenWidth, 0.0f, mScreenHeight, 0, 50);
        // Set the camera position (View matrix)
        Matrix.setLookAtM(mtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mtrxProjectionAndView, 0, mtrxProjection, 0, mtrxView, 0);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        // Set the clear color to black
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1);

        // Create the shaders, solid color
        int vertexShader = riGraphicTools.loadShader(GLES20.GL_VERTEX_SHADER, riGraphicTools.vs_SolidColor);
        int fragmentShader = riGraphicTools.loadShader(GLES20.GL_FRAGMENT_SHADER, riGraphicTools.fs_SolidColor);

        riGraphicTools.sp_SolidColor = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(riGraphicTools.sp_SolidColor, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(riGraphicTools.sp_SolidColor, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(riGraphicTools.sp_SolidColor);                  // creates OpenGL ES program executables

        // Create the shaders, images
        vertexShader = riGraphicTools.loadShader(GLES20.GL_VERTEX_SHADER, riGraphicTools.vs_Image);
        fragmentShader = riGraphicTools.loadShader(GLES20.GL_FRAGMENT_SHADER, riGraphicTools.fs_Image);

        riGraphicTools.sp_Image = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(riGraphicTools.sp_Image, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(riGraphicTools.sp_Image, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(riGraphicTools.sp_Image);                  // creates OpenGL ES program executables

        // Set our shader programm
        GLES20.glUseProgram(riGraphicTools.sp_Image);
    }

    public FloatBuffer setupImage(int id) {
        // Create our UV coordinates.
        //这里是干嘛的?  很关键
        uvs = new float[]{
                0.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 0.0f
        };

        // The texture buffer
        ByteBuffer bb = ByteBuffer.allocateDirect(uvs.length * 4);
        bb.order(ByteOrder.nativeOrder());
        uvBuffer = bb.asFloatBuffer();
        uvBuffer.put(uvs);
        uvBuffer.position(0);

        // Generate Textures, if more needed, alter these numbers.
        int[] texturenames = new int[1];
        GLES20.glGenTextures(1, texturenames, 0);

        // Retrieve our image from resources.
//        int id = mContext.getResources().getIdentifier("drawable/map1", null, mContext.getPackageName());

        // Temporary create a bitmap
        //通过options改变图片的参数

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), id, opts);

        // Bind texture to texturename
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[0]);

        // Set filtering
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);
        //开启gl的两个功能 使图片变成透明的
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glEnable(GLES20.GL_BLEND);

        // We are done using the bitmap so we should recycle it.
        bmp.recycle();

        return uvBuffer;

    }

    public FloatBuffer setupTriangle(float[] verticesPosition) {

        indices = new short[]{0, 1, 2, 0, 2, 3}; // The order of vertexrendering.

        // The vertex buffer.
        ByteBuffer bb = ByteBuffer.allocateDirect(verticesPosition.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(verticesPosition);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(indices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(indices);
        drawListBuffer.position(0);

        return vertexBuffer;
    }
}
