package org.app.opengl_es_android_version.data;

import android.opengl.GLES20;

import org.app.opengl_es_android_version.contant.Constants;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class IndexBuffer {

    public final int indexBufferId;

    public IndexBuffer(short[] indexData) {
        final int buffers[] = new int[1];
        GLES20.glGenBuffers(buffers.length, buffers, 0);
        if (buffers[0] == 0) {
            int errorCode = GLES20.glGetError();
            throw new RuntimeException("Could not create a new vertex buffer object and errorCode = " + errorCode);
        }
        indexBufferId = buffers[0];
        //绑定数据
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, buffers[0]);
        //将java数据转存至native
        ShortBuffer indexArray = ByteBuffer.allocateDirect(indexData.length * Constants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(indexData);
        indexArray.position(0);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexArray.capacity() * Constants.BYTES_PER_FLOAT,
                indexArray, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public IndexBuffer(int[] indexData) {
        final int buffers[] = new int[1];
        GLES20.glGenBuffers(buffers.length, buffers, 0);
        if (buffers[0] == 0) {
            int errorCode = GLES20.glGetError();
            throw new RuntimeException("Could not create a new vertex buffer object and errorCode = " + errorCode);
        }
        indexBufferId = buffers[0];
        //绑定数据
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, buffers[0]);
        //将java数据转存至native
        IntBuffer indexArray = ByteBuffer.allocateDirect(indexData.length * Constants.BYTES_PER_INT)
                .order(ByteOrder.nativeOrder())
                .asIntBuffer()
                .put(indexData);
        indexArray.position(0);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexArray.capacity() * Constants.BYTES_PER_INT,
                indexArray, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }
}
