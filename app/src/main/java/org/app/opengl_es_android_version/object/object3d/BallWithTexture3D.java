package org.app.opengl_es_android_version.object.object3d;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.contant.Constants;
import org.app.opengl_es_android_version.data.IndexBuffer;
import org.app.opengl_es_android_version.data.VertexBuffer;
import org.app.opengl_es_android_version.program.BallColorProgram;
import org.app.opengl_es_android_version.util.Geometry;
import org.app.opengl_es_android_version.util.TextureHelper;

import java.util.ArrayList;


/**
 * Created by zzr on 2018/3/23.
 */

public class BallWithTexture3D extends Object3D {

    private static final int POSITION_COORDIANTE_COMPONENT_COUNT = 3; // 每个顶点的坐标数 x y z
    private static final int TEXTURE_COORDIANTE_COMPONENT_COUNT = 2; // 每个顶点的坐标数 x y z
    private static final int STRIDE = (POSITION_COORDIANTE_COMPONENT_COUNT
            + TEXTURE_COORDIANTE_COMPONENT_COUNT)
            * Constants.BYTES_PER_FLOAT;

    Geometry.Circle circle;

    private Context context;
    IndexBuffer indexBuffer;
    VertexBuffer vertexBuffer;
    BallColorProgram ballColorProgram;
    private int numElements = 0; // 记录要画多少个三角形
    private int textureId;

    public BallWithTexture3D(Context context, float x, float y, float z, float radius) {
        this.circle = new Geometry.Circle(new Geometry.Point(x, y, z), radius);
        this.context = context;
        initVertexData();
        initTexture();
        buildProgram();
        setAttributeStatus();
    }

    public BallWithTexture3D(Context context) {
        this.context = context;
        initVertexData();
        initTexture();
        buildProgram();
        setAttributeStatus();
    }

    private void initVertexData() {
        final int angleSpan = 5;// 将球进行单位切分的角度，此数值越小划分矩形越多，球面越趋近平滑
        final float radius = 1.0f;// 球体半径
        short offset = 0;
        ArrayList<Float> vertexList = new ArrayList<>(); // 使用list存放顶点数据
        ArrayList<Short> indexList = new ArrayList<>();// 顶点索引数组

        float x = circle.center.x;
        float y = circle.center.y;
        float z = circle.center.z;
        for (int vAngle = 0; vAngle < 180; vAngle = vAngle + angleSpan) {
            for (int hAngle = 0; hAngle <= 360; hAngle = hAngle + angleSpan) {
                // st纹理坐标
                float s0 = hAngle / 360.0f; //左上角 s
                float t0 = vAngle / 180.0f; //左上角 t
                float s1 = (hAngle + angleSpan) / 360.0f; //右下角s
                float t1 = (vAngle + angleSpan) / 180.0f; //右下角t
                // 左上角 0
                float x0 = x + (float) (radius * Math.sin(Math.toRadians(vAngle)) * Math.sin(Math
                        .toRadians(hAngle)));
                float y0 = y + (float) (radius * Math.cos(Math.toRadians(vAngle)));
                float z0 = z + (float) (radius * Math.sin(Math.toRadians(vAngle)) * Math.cos(Math
                        .toRadians(hAngle)));
                vertexList.add(x0);
                vertexList.add(y0);
                vertexList.add(z0);
                vertexList.add(s0);
                vertexList.add(t0);
                // 右上角 1
                float x1 = x + (float) (radius * Math.sin(Math.toRadians(vAngle)) * Math.sin(Math
                        .toRadians(hAngle + angleSpan)));
                float y1 = y + (float) (radius * Math.cos(Math.toRadians(vAngle)));
                float z1 = z + (float) (radius * Math.sin(Math.toRadians(vAngle)) * Math.cos(Math
                        .toRadians(hAngle + angleSpan)));
                vertexList.add(x1);
                vertexList.add(y1);
                vertexList.add(z1);
                vertexList.add(s1);
                vertexList.add(t0);
                // 右下角 2
                float x2 = x + (float) (radius * Math.sin(Math.toRadians(vAngle + angleSpan)) * Math
                        .sin(Math.toRadians(hAngle + angleSpan)));
                float y2 = y + (float) (radius * Math.cos(Math.toRadians(vAngle + angleSpan)));
                float z2 = z + (float) (radius * Math.sin(Math.toRadians(vAngle + angleSpan)) * Math
                        .cos(Math.toRadians(hAngle + angleSpan)));
                vertexList.add(x2);
                vertexList.add(y2);
                vertexList.add(z2);
                vertexList.add(s1);
                vertexList.add(t1);
                // 左下角 3
                float x3 = x + (float) (radius * Math.sin(Math.toRadians(vAngle + angleSpan)) * Math
                        .sin(Math.toRadians(hAngle)));
                float y3 = y + (float) (radius * Math.cos(Math.toRadians(vAngle + angleSpan)));
                float z3 = z + (float) (radius * Math.sin(Math.toRadians(vAngle + angleSpan)) * Math
                        .cos(Math.toRadians(hAngle)));
                vertexList.add(x3);
                vertexList.add(y3);
                vertexList.add(z3);
                vertexList.add(s0);
                vertexList.add(t1);

                indexList.add((short) (offset + 0));
                indexList.add((short) (offset + 3));
                indexList.add((short) (offset + 2));
                indexList.add((short) (offset + 0));
                indexList.add((short) (offset + 2));
                indexList.add((short) (offset + 1));

                offset += 4; // 4个顶点的偏移
            }
        }

        numElements = indexList.size();// 记录有多少个索引点

        float[] data_vertex = new float[vertexList.size()];
        for (int i = 0; i < vertexList.size(); i++) {
            data_vertex[i] = vertexList.get(i);
        }
        vertexBuffer = new VertexBuffer(data_vertex);

        short[] data_index = new short[indexList.size()];
        for (int i = 0; i < indexList.size(); i++) {
            data_index[i] = indexList.get(i);
        }
        indexBuffer = new IndexBuffer(data_index);
    }

    private void buildProgram() {
        ballColorProgram = new BallColorProgram(context);
        ballColorProgram.userProgram();
    }

    private void setAttributeStatus() {
        vertexBuffer.setVertexAttributePointer(
                ballColorProgram.aPositionLocation,
                POSITION_COORDIANTE_COMPONENT_COUNT,
                STRIDE, 0);
        vertexBuffer.setVertexAttributePointer(
                ballColorProgram.aTextureCoordinatesLocation,
                TEXTURE_COORDIANTE_COMPONENT_COUNT,
                STRIDE,
                POSITION_COORDIANTE_COMPONENT_COUNT * Constants.BYTES_PER_FLOAT);
    }

    private void initTexture() {
        textureId = TextureHelper.loadTexture(context, R.drawable.world);
    }

    @Override
    public void unbind() {
        GLES20.glDisableVertexAttribArray(ballColorProgram.aPositionLocation);
    }

    @Override
    protected void draw() {
        setAttributeStatus();
        // 将最终变换矩阵写入
        ballColorProgram.setUniforms(mvpMatrix, textureId);

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBuffer.getIndexBufferId());
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, numElements, GLES20.GL_UNSIGNED_SHORT, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
    }


}
