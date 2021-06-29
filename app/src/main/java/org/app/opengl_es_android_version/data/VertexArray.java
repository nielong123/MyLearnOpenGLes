package org.app.opengl_es_android_version.data;

import android.opengl.GLES20;

import org.app.opengl_es_android_version.contant.Constants;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class VertexArray {

    private final FloatBuffer floatBuffer;

    public VertexArray(float[] vertexData) {
        floatBuffer = ByteBuffer.allocateDirect(vertexData.length * Constants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
        floatBuffer.position(0);
    }

    /**
     * 这个方法最好不要随便改。。。。在冰球部分要用的
     *
     * @param attributeLocation
     * @param componentCount
     * @param stride
     * @param dataOffset
     */
    public void setVertexAttributePointer(int attributeLocation,
                                          int componentCount, int stride, int dataOffset) {
        floatBuffer.position(dataOffset);
        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_FLOAT,
                false, stride, floatBuffer);
        GLES20.glEnableVertexAttribArray(attributeLocation);
        floatBuffer.position(0);
    }

    /**
     * 自己加的
     *
     * @param attributeLocation
     * @param componentCount
     * @param stride
     * @param dataOffset
     */
    public void enableVertexAttributePointer(int attributeLocation,
                                             int componentCount, int stride, int dataOffset) {
        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_FLOAT,
                false, stride, floatBuffer);
        GLES20.glEnableVertexAttribArray(attributeLocation);
        floatBuffer.position(0);
    }

    public FloatBuffer getFloatBuffer() {
        return floatBuffer;
    }
}
