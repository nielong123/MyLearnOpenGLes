package org.app.opengl_es_android_version.object.object2d;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.util.ColorHelper;
import org.app.opengl_es_android_version.util.Geometry;

import static android.opengl.GLES20.GL_FLOAT;

/***
 * 用折线来绘制一个圆形
 */
public class Rectangle extends Object2D {

    //因为是x和y，所以两个值代表一个属性
    private static final int POSITION_COMPONENT_COUNT = 2;

    final private int count;

    Geometry.Rect rectangle;

    VertexArray vertexArray;

    public Rectangle() {
        super();
        Geometry.Point point = new Geometry.Point(0f, 0f, 0f);
        rectangle = new Geometry.Rect(point, 1f, 1f);
        vertexArray = new VertexArray(getVertexWithRectangle(rectangle));
        count = vertexArray.getFloatBuffer().limit() / 2;
    }

    public Rectangle(Geometry.Rect rect) {
        super();
        vertexArray = new VertexArray(getVertexWithRectangle(rect));
        count = vertexArray.getFloatBuffer().limit() / 2;
    }


    private float[] getVertexWithRectangle(Geometry.Rect rectangle) {

        float[] vertexs = new float[8];

        //左下角
        vertexs[0] = rectangle.point.x - rectangle.width / 2;
        vertexs[1] = rectangle.point.y - rectangle.height / 2;

        //右下角
        vertexs[2] = rectangle.point.x + rectangle.width / 2;
        vertexs[3] = vertexs[1];

        //右上角
        vertexs[4] = rectangle.point.x + rectangle.width / 2;
        vertexs[5] = rectangle.point.y + rectangle.height / 2;

        //左上角
        vertexs[6] = vertexs[0];
        vertexs[7] = vertexs[5];

        return vertexs;
    }


    @Override
    public void bindData(Context context) {
        super.bindData(context);
    }

    @Override
    public void draw() {
        GLES20.glUniformMatrix4fv(colorShaderProgram.aMatrixLocation, 1, false, mvpMatrix, 0);
        //告诉opengl从缓冲区vertextData中取数据找到属性a_Position的数据
        GLES20.glVertexAttribPointer(
                colorShaderProgram.aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexArray.getFloatBuffer());
        //使能顶点数组
        GLES20.glEnableVertexAttribArray(colorShaderProgram.aPositionLocation);
        ColorHelper.setColor(colorShaderProgram.aColorLocation, context.getColor(R.color.red1));
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, count);
    }

    @Override
    public void unbind() {
        GLES20.glDisableVertexAttribArray(colorShaderProgram.aPositionLocation);
    }

}
