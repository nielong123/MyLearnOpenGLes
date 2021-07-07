//package org.app.opengl_es_android_version.object.object2d;
//
//import android.content.Context;
//import android.opengl.GLES20;
//
//import org.app.opengl_es_android_version.R;
//import org.app.opengl_es_android_version.contant.Constants;
//import org.app.opengl_es_android_version.data.VertexArray;
//import org.app.opengl_es_android_version.util.ShaderHelper;
//
//import static android.opengl.GLES20.GL_FLOAT;
//
//
///***
// * 绘制折线
// */
//public class Polyline extends Object2D {
//
//    private static final int POSITION_COMPONENT_COUNT = 2;
//
//    private int programId;
//    private int uColorLocation;
//    private int aPositionLocation;
//
//    VertexArray vertexArray;
//
//    public Polyline(Context context) {
//        super(context);
//        vertexArray = new VertexArray(tableVerticesWithPolyline);
//    }
//
//    private float[] tableVerticesWithPolyline = {
//
//            -0.3f, -0.4f,
//            0.1f, 0.1f,
//            -0.35f, 0.5f
//    };
//
//
//    @Override
//    public void bindData(Context context) {
//        super.bindData(context);
//        programId = ShaderHelper.buildProgram(context,
//                R.raw.texture_vertex_shader_copy, R.raw.simple_fragment_shader1_5);
//        GLES20.glUseProgram(programId);
//        //获取uniform的位置，把位置存入uColorLocation中
//        uColorLocation = GLES20.glGetUniformLocation(programId, Constants.U_COLOR);
//        //获取属性位置
//        aPositionLocation = GLES20.glGetAttribLocation(programId, Constants.A_POSITION);
//    }
//
//    @Override
//    public void unbind() {
//        GLES20.glDisableVertexAttribArray(uColorLocation);
//        GLES20.glDisableVertexAttribArray(aPositionLocation);
//    }
//
//    @Override
//    public void draw() {
//        //告诉opengl从缓冲区vertextData中取数据找到属性a_Position的数据
//        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexArray.getFloatBuffer());
////        vertexArray.getFloatBuffer().position(0);
//        //使能顶点数组
//        GLES20.glEnableVertexAttribArray(aPositionLocation);
//        GLES20.glUniform4f(uColorLocation, 1.0f, 0.3f, 1.0f, 1.0f);
//        GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, 3);
//    }
//
//}
