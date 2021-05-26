package org.app.opengl_es_android_version.util;

import android.opengl.Matrix;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by wuwang on 2016/10/30
 */

public class VaryTools {

    final private float[] viewMatrix = new float[16];    //相机矩阵
    final private float[] projectionMatrix = new float[16];    //投影矩阵
    private float[] viewProjectionMatrix = new float[16];      //原始矩阵

    //和这个变量相关的方法不知道有什么用
    private Stack<float[]> mStack;      //变换矩阵堆栈

    public VaryTools() {
        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.setIdentityM(projectionMatrix, 0);
        Matrix.setIdentityM(viewProjectionMatrix, 0);
        mStack = new Stack<>();
    }

    //保护现场
    public void pushMatrix() {
        mStack.push(Arrays.copyOf(viewProjectionMatrix, 16));
    }

    //恢复现场
    public void popMatrix() {
        viewProjectionMatrix = mStack.pop();
    }

    public void clearStack() {
        mStack.clear();
    }

    //平移变换
    public void translate(float x, float y, float z) {
        Matrix.translateM(viewProjectionMatrix, 0, x, y, z);
    }

    //旋转变换
    public void rotate(float angle, float x, float y, float z) {
        Matrix.rotateM(viewProjectionMatrix, 0, angle, x, y, z);
    }

    //缩放变换
    public void scale(float x, float y, float z) {
        Matrix.scaleM(viewProjectionMatrix, 0, x, y, z);
    }

    //初始化投影
    public void setProjection(int width, int height) {
        MatrixHelper.perspectiveM(projectionMatrix, 35, (float) width / (float) height, 1f, 100f);
    }

    //设置相机
    public void setCamera(float ex, float ey, float ez, float cx, float cy, float cz, float ux, float uy, float uz) {
        Matrix.setLookAtM(viewMatrix, 0, ex, ey, ez, cx, cy, cz, ux, uy, uz);
    }

    public void frustum(float left, float right, float bottom, float top, float near, float far) {
        Matrix.frustumM(projectionMatrix, 0, left, right, bottom, top, near, far);
    }

    public void ortho(float left, float right, float bottom, float top, float near, float far) {
        Matrix.orthoM(projectionMatrix, 0, left, right, bottom, top, near, far);
    }

    public void resetMatrix() {
        Matrix.setIdentityM(viewProjectionMatrix, 0);
    }

    //获取投影和视图矩阵的乘积
    public float[] getViewProjectionMatrix() {
        float[] ans = new float[16];
        Matrix.multiplyMM(ans, 0, viewMatrix, 0, viewProjectionMatrix, 0);
        Matrix.multiplyMM(ans, 0, projectionMatrix, 0, ans, 0);
        return ans;
    }

}
