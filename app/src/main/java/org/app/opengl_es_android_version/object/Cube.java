package org.app.opengl_es_android_version.object;

import android.opengl.GLES20;
import android.opengl.Matrix;

import org.app.opengl_es_android_version.contant.Constants;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.program.CubeShaderProgram;

import java.nio.ByteBuffer;

public class Cube {

    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT;

    private final VertexArray vertexArray;
    public float[] modelMatrix = new float[16];

    public Cube() {
        this.vertexArray = new VertexArray(cubeData);
        Matrix.setIdentityM(modelMatrix, 0);
        indexArray.position(0);
    }


    public void bindData(CubeShaderProgram cubeShaderProgram) {
        vertexArray.setVertexAttributePointer(cubeShaderProgram.uPositionLocation,
                POSITION_COMPONENT_COUNT,
                STRIDE,
                0);

        vertexArray.setVertexAttributePointer(
                cubeShaderProgram.aColorLocation,
                COLOR_COMPONENT_COUNT,
                STRIDE,
                POSITION_COMPONENT_COUNT
        );
    }

    public void draw() {
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6 * 3 * 2);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6 * 2 * 3, GLES20.GL_UNSIGNED_BYTE, indexArray);
    }


    private static final float[] cubeData = {
            //x,   y,    z     R,  G,  B
            -1f, 1f, 1f, 1f, 0f, 0f, // 0 left top near
            1f, 1f, 1f, 1f, 0f, 1f, // 1 right top near
            -1f, -1f, 1f, 0f, 0f, 1f, // 2 left bottom near
            1f, -1f, 1f, 0f, 1f, 0f, // 3 right bottom near
            -1f, 1f, -1f, 0f, 1f, 0f, // 4 left top far
            1f, 1f, -1f, 0f, 0f, 1f, // 5 right top far
            -1f, -1f, -1f, 1f, 0f, 1f, // 6 left bottom far
            1f, -1f, -1f, 1f, 0f, 0f, // 7 right bottom far

//            // x ,y ,z , R, G, B
//            1f, 1f, 1f, 1, 0, 1,   //近平面第一个三角形
//            -1f, 1f, 1f, 1, 0, 0,
//            -1f, -1f, 1f, 0, 0, 1,
//
//            1f, 1f, 1f, 1, 0, 1, //近平面第二个三角形
//            -1f, -1f, 1f, 0, 0, 1,
//            1f, -1f, 1f, 0, 1, 0,
//
//
//            1f, 1f, -1f, 0, 0, 1,   //远平面第一个三角形
//            -1f, 1f, -1f, 0, 1, 0,
//            -1f, -1f, -1f, 1, 0, 1,
//            1f, 1f, -1f, 0, 0, 1,   //远平面第二个三角形
//            -1f, -1f, -1f, 1, 0, 1,
//            1f, -1f, -1f, 1, 0, 0,
//
//
//            -1f, 1f, -1f, 0, 1, 0,   //左平面第一个三角形
//            -1f, 1f, 1f, 1, 0, 0,
//            -1f, -1f, 1f, 0, 0, 1,
//            -1f, 1f, -1f, 0, 1, 0,   //左平面第二个三角形
//            -1f, -1f, 1f, 0, 0, 1,
//            -1f, -1f, -1f, 1, 0, 1,
//
//            1f, 1f, -1f, 0, 0, 1,   //右平面第一个三角形
//            1f, 1f, 1f, 1, 0, 1,
//            1f, -1f, 1f, 0, 1, 0,
//            1f, 1f, -1f, 0, 0, 1,   //右平面第二个三角形
//            1f, -1f, 1f, 0, 1, 0,
//            1f, -1f, -1f, 1, 0, 0,
//
//            1f, 1f, -1f, 0, 0, 1,   //上平面第一个三角形
//            -1f, 1f, -1f, 0, 1, 0,
//            -1f, 1f, 1f, 1, 0, 0,
//            1f, 1f, -1f, 0, 0, 1,   //上平面第二个三角形
//            -1f, 1f, 1f, 1, 0, 0,
//            1f, 1f, 1f, 1, 0, 1,
//
//            1f, -1f, -1f, 1, 0, 0,   //下平面第一个三角形
//            -1f, -1f, -1f, 1, 0, 1,
//            -1f, -1f, 1f, 0, 0, 1,
//            1f, -1f, -1f, 1, 0, 0,   //下平面第二个三角形
//            -1f, -1f, 1f, 0, 0, 1,
//            1f, -1f, 1f, 0, 1, 0,
    };


    ByteBuffer indexArray = ByteBuffer.allocateDirect(6 * 2 * 3)
            .put(new byte[]{
                    //front
                    1, 0, 2,
                    1, 2, 3,
                    //back
                    5, 4, 6,
                    5, 6, 7,
                    //left
                    4, 0, 2,
                    4, 2, 6,
                    //right
                    5, 1, 3,
                    5, 3, 7,
                    //top
                    5, 4, 0,
                    5, 0, 1,
                    //bottom
                    7, 6, 2,
                    7, 2, 3
            });

}
