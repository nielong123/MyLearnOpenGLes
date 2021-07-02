package org.app.opengl_es_android_version.object.object2d;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.contant.Constants;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.util.ColorHelper;
import org.app.opengl_es_android_version.util.Geometry;
import org.app.opengl_es_android_version.util.ShaderHelper;

import static android.opengl.GLES20.GL_FLOAT;

/***
 * 用折线来绘制一个圆形
 */
public class Rectangle extends Object2D {

    //因为是x和y，所以两个值代表一个属性
    private static final int POSITION_COMPONENT_COUNT = 2;

    private int programId;
    private int uColorLocation;
    private int aPositionLocation;
    private int aMatrixLocation;

    final private int count;

    Geometry.Rect rectangle;

    VertexArray vertexArray;

    public Rectangle(Context context) {
        super(context);
        Geometry.Point point = new Geometry.Point(0f, 0f, 0f);
        rectangle = new Geometry.Rect(point, 1f, 1f);
        vertexArray = new VertexArray(getVertexWithRectangle(rectangle));
        count = vertexArray.getFloatBuffer().limit() / 2;
    }

    public Rectangle(Context context, Geometry.Rect rect) {
        super(context);
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
        programId = ShaderHelper.buildProgram(context,
                R.raw.texture_vertex_shader_copy, R.raw.simple_fragment_shader1_5);
        GLES20.glUseProgram(programId);
        //获取uniform的位置，把位置存入uColorLocation中
        uColorLocation = GLES20.glGetUniformLocation(programId, Constants.U_COLOR);
        //获取属性位置
        aPositionLocation = GLES20.glGetAttribLocation(programId, Constants.A_POSITION);

        aMatrixLocation = GLES20.glGetUniformLocation(programId, Constants.U_MATRIX);
    }

    @Override
    public void draw() {
//        GLES20.glDisable(GLES20.GL_TEXTURE_2D);
        GLES20.glUniformMatrix4fv(aMatrixLocation, 1, false, mvpMatrix, 0);
        //告诉opengl从缓冲区vertextData中取数据找到属性a_Position的数据
        GLES20.glVertexAttribPointer(
                aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexArray.getFloatBuffer());
        //使能顶点数组
        GLES20.glEnableVertexAttribArray(aPositionLocation);
        ColorHelper.setColor(uColorLocation, context.getColor(R.color.red1));
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, count);
    }

    @Override
    public void unbind() {
//        GLES20.glDisableVertexAttribArray(uColorLocation);
        GLES20.glDisableVertexAttribArray(aPositionLocation);
//        GLES20.glDisableVertexAttribArray(aMatrixLocation);
    }

}
