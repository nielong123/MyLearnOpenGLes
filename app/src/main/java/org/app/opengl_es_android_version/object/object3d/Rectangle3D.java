package org.app.opengl_es_android_version.object.object3d;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.util.Geometry;

import java.nio.ByteBuffer;

import static android.opengl.GLES20.GL_UNSIGNED_BYTE;

/***
 * 用折线来绘制一个圆形
 */
public class Rectangle3D extends Object3D {

    //因为是x和y,z，所以3个值代表一个属性
    private static final int POSITION_COMPONENT_COUNT = 3;

    final private int count;

    Geometry.Rect rectangle;

    private ByteBuffer indexArray = ByteBuffer.allocateDirect(6)
            .put(new byte[]{
                    0, 1, 2, 2, 3, 0
            });

    public Rectangle3D() {
        super();
        Geometry.Point point = new Geometry.Point(0f, 0f, 0f);
        rectangle = new Geometry.Rect(point, 1f, 1f);
        vertexArray = new VertexArray(getVertexWithRectangle(rectangle));
        count = vertexArray.getFloatBuffer().limit() / 3;
        indexArray.position(0);
    }


    private float[] getVertexWithRectangle(Geometry.Rect rectangle) {

        float[] vertexs = new float[12];

        //左下角
        vertexs[0] = rectangle.point.x - rectangle.width / 2;
        vertexs[1] = rectangle.point.y - rectangle.height / 2;
        vertexs[2] = rectangle.point.z;

        //右下角
        vertexs[3] = rectangle.point.x + rectangle.width / 2;
        vertexs[4] = vertexs[1];
        vertexs[5] = rectangle.point.z;

        //右上角
        vertexs[6] = rectangle.point.x + rectangle.width / 2;
        vertexs[7] = rectangle.point.y + rectangle.height / 2;
        vertexs[8] = rectangle.point.z;

        //左上角
        vertexs[9] = vertexs[0];
        vertexs[10] = vertexs[7];
        vertexs[11] = rectangle.point.z;


        return vertexs;
    }


    @Override
    public void bindData(Context context) {
        super.bindData(context);
    }

    @Override
    protected void draw() {
        GLES20.glUniformMatrix4fv(colorShaderProgram.aMatrixLocation, 1, false, mvpMatrix, 0);
        vertexArray.enableVertexAttributePointer(colorShaderProgram.aPositionLocation, POSITION_COMPONENT_COUNT, 0, 0);
//        GLES20.glVertexAttribPointer(
//                colorShaderProgram.aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexArray.getFloatBuffer());
        //使能顶点数组
//        GLES20.glEnableVertexAttribArray(colorShaderProgram.aPositionLocation);
        GLES20.glUniform4f(colorShaderProgram.aColorLocation, 1.0f, 0.0f, 2.0f, 0.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, indexArray.limit(), GL_UNSIGNED_BYTE, indexArray);
//        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, count);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
    }

    @Override
    public void unbind() {
        GLES20.glDisableVertexAttribArray(colorShaderProgram.aPositionLocation);
    }

}
