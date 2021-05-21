package org.app.opengl_es_android_version.object.object2d;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.contant.Constants;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.util.Geometry;
import org.app.opengl_es_android_version.util.ShaderHelper;

/***
 * 用折线来绘制一个圆形
 */
public class Circle extends Object2D {

    private String TAG = this.getClass().getSimpleName();

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int STRIDE = POSITION_COMPONENT_COUNT * Constants.POSITION_COMPONENT_COUNT;

    private int programId;
    private int uColorLocation;
    private int aPositionLocation;

    final private int count;

    Geometry.Circle circle;

    VertexArray vertexArray;

    public Circle(Context context) {
        super(context);
        Geometry.Point point = new Geometry.Point(0f, 0f, 0f);
        circle = new Geometry.Circle(point, 0.5f);
        vertexArray = new VertexArray(getVertexWithCircle(circle));
        count = vertexArray.getFloatBuffer().limit() / 2;

    }

    private float[] getVertexWithCircle(Geometry.Circle circle) {

        int all = 360;
        //这个count是vertexs数组的长度，因为是二维，每2个float表示一组行，y，所以要用360/分度，再乘以2
        int count = (int) (all / circle.angle) * 2;
        float[] vertexs = new float[count];
        int offset = 0;
        for (int angdeg = 0; angdeg < all; angdeg += circle.angle) {
            double radians = Math.toRadians(angdeg);
            vertexs[offset++] = (float) (circle.center.x - circle.radius * Math.sin(radians));
            vertexs[offset++] = (float) (circle.center.y + circle.radius * Math.cos(radians));
        }

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
    }

    @Override
    public void unbind() {
        GLES20.glDisableVertexAttribArray(uColorLocation);
        GLES20.glDisableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void draw() {
        //告诉opengl从缓冲区vertextData中取数据找到属性a_Position的数据
        GLES20.glVertexAttribPointer(aPositionLocation,
                POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 0, vertexArray.getFloatBuffer());
        //使能顶点数组
        GLES20.glEnableVertexAttribArray(aPositionLocation);
        GLES20.glUniform4f(uColorLocation, 1.0f, 0.8f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, count);
    }

//    @Override
//    public void draw(float[] viewProjectMatrix) {
//        super.draw(viewProjectMatrix);
//    }
}
