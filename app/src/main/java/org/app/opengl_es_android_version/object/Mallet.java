package org.app.opengl_es_android_version.object;

import android.opengl.Matrix;

import org.app.opengl_es_android_version.contant.Constants;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.program.ColorShaderProgram;
import org.app.opengl_es_android_version.util.Geometry;
import org.app.opengl_es_android_version.util.ObjectBuilder;

import java.util.List;

public class Mallet {

    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT;

    public float[] modelMatrix = new float[16];

    //private static final float[] VERTEX_DATA = {
    //        // 两个木槌的质点位置
    //        //x,    y,    R, G, B
    //        0f,   -0.4f,  1f,1f,1f,
    //        0f,    0.4f,  1f,1f,1f,
    //};

    //public Mallet(){
    //    vertexArray = new VertexArray(VERTEX_DATA);
    //}

    private final VertexArray vertexArray;
    private List<ObjectBuilder.DrawCommand> drawCommandList;

    public final float radius;
    public final float height;

    public Geometry.Point position;
    public Geometry.Point previousPosition;
    public volatile boolean isPressed;

    public Mallet(float radius, float height, int numPointAroundMallet) {

        ObjectBuilder.GeneratedData mallet = ObjectBuilder.createMallet(
                new Geometry.Point(0f, 0f, 0f),
                radius, height, numPointAroundMallet);
        this.radius = radius;
        this.height = height;

        vertexArray = new VertexArray(mallet.vertexData);
        drawCommandList = mallet.drawCommanList;
        Matrix.setIdentityM(modelMatrix, 0);
    }

//    public Mallet(float centerX, float centerY) {
//
//        float centerZ = 0f;
//        this.radius = 0.08f;
//        this.height = 0.15f;
//        ObjectBuilder.GeneratedData mallet = ObjectBuilder.createMallet(centerX, centerY, centerZ, radius, height);
//
//        vertexArray = new VertexArray(mallet.vertexData);
//        drawCommandList = mallet.drawCommanList;
//        Matrix.setIdentityM(modelMatrix, 0);
////        Matrix.translateM(modelMatrix, 0, centerX, centerY, centerZ);
//    }

    public void bindData(ColorShaderProgram shaderProgram) {
        vertexArray.setVertexAttributePointer(
                shaderProgram.aPositionLocation,
                POSITION_COMPONENT_COUNT,
                0,
                0);
    }

    public void draw() {
        for (ObjectBuilder.DrawCommand drawCommand : drawCommandList) {
            drawCommand.draw();
        }
    }
}
