//package org.app.opengl_es_android_version.object.object2d;
//
//import android.content.Context;
//import android.opengl.GLES20;
//import android.opengl.Matrix;
//
//import org.app.opengl_es_android_version.R;
//import org.app.opengl_es_android_version.contant.Constants;
//import org.app.opengl_es_android_version.data.VertexArray;
//import org.app.opengl_es_android_version.util.ShaderHelper;
//
//import java.nio.ByteBuffer;
//
//import static android.opengl.GLES20.GL_FLOAT;
//import static android.opengl.GLES20.GL_UNSIGNED_BYTE;
//
//public class Rectangle1 extends Object2D {
//
//    private static final int POSITION_COMPONENT_COUNT = 2;
//    private int uColorLocation;
//    private int aPositionLocation;
//    private int aMatrixLocation;
//
//    private int programId;
//
//    private static final float r = 0.3f;
//
//    private static final float[] VERTEX_DATA = {
//            -r, -r,
//            r, -r,
//            r, r,
//            -r, r
//    };
//
//    private ByteBuffer indexArray = ByteBuffer.allocateDirect(6)
//            .put(new byte[]{
//                    0, 1, 2, 2, 3, 0
//            });
//
//    private final VertexArray vertexArray;
//
//    public Rectangle1(Context context) {
//        super(context);
//        vertexArray = new VertexArray(VERTEX_DATA);
//        indexArray.position(0);
//    }
//
//    @Override
//    public void bindData(Context context) {
//        super.bindData(context);
//        programId = ShaderHelper.buildProgram(context,
//                R.raw.texture_vertex_shader_copy, R.raw.simple_fragment_shader1_5);
//        GLES20.glUseProgram(programId);
//        uColorLocation = GLES20.glGetUniformLocation(programId, Constants.U_COLOR);
//        aPositionLocation = GLES20.glGetAttribLocation(programId, Constants.A_POSITION);
//        aMatrixLocation = GLES20.glGetUniformLocation(programId, Constants.U_MATRIX);
//    }
//
//    @Override
//    public void draw() {
//        GLES20.glUniformMatrix4fv(aMatrixLocation, 1, false, mvpMatrix, 0);
//        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexArray.getFloatBuffer());
//        GLES20.glEnableVertexAttribArray(aPositionLocation);
//        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 0.6f, 1.0f);
//        GLES20.glDrawElements(GLES20.GL_TRIANGLE_FAN, indexArray.limit(), GL_UNSIGNED_BYTE, indexArray);
//    }
//
//    @Override
//    public void unbind() {
//        GLES20.glDisableVertexAttribArray(aPositionLocation);
//    }
//}
