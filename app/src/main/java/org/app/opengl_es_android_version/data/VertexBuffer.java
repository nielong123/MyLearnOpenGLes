package org.app.opengl_es_android_version.data;

import android.opengl.GLES20;

import org.app.opengl_es_android_version.contant.Constants;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


/***
 * VBO 模版
 */
public class VertexBuffer {

    private final int bufferId;

    public int getVertexBufferId() {
        return bufferId;
    }

    public VertexBuffer(float[] vertexData) {
        //向openGL服务端申请一个新的缓冲区
        final int buffers[] = new int[1];
        GLES20.glGenBuffers(buffers.length, buffers, 0);
        if (buffers[0] == 0) {
            int i = GLES20.glGetError();
            throw new RuntimeException("不能创建一个新的vertex buffer Object3D, glGetError = " + i);
        }
        //保留缓冲期表示
        bufferId = buffers[0];
        //绑定缓冲区为数组缓存
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0]);
        //将java数据转存至native
        FloatBuffer vertexArray = ByteBuffer.allocateDirect(vertexData.length * Constants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
        vertexArray.position(0);
        //将native数据保存到缓冲区，注意长度为字节单位，用途是GL_STATIC_DRAW
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexArray.capacity() * Constants.BYTES_PER_FLOAT,
                vertexArray, GLES20.GL_STATIC_DRAW);
        //解绑缓冲区
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }


    public void setVertexAttributePointer(int attributeLocation, int componentCount, int stride, int dataOffset) {
        //绑定标示缓冲区,同志openGL需要使用指定缓冲区
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, bufferId);
        //调用接口，设置着色器程序顶点属性指针
        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_FLOAT,
                false, stride, dataOffset);
        //使能着色器属性
        GLES20.glEnableVertexAttribArray(attributeLocation);
        //解绑
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }
}
