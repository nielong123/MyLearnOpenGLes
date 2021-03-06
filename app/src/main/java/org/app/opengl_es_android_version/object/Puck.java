package org.app.opengl_es_android_version.object;

import android.opengl.Matrix;

import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.program.ColorShaderProgram;
import org.app.opengl_es_android_version.program.TextureShaderProgram;
import org.app.opengl_es_android_version.util.Geometry;
import org.app.opengl_es_android_version.util.ObjectBuilder;

import java.util.List;

public class Puck extends Object3D {

    private static final int POSITION_COMPONENT_COUNT = 3;

    public float[] modelMatrix = new float[16];

    public final float radius, height;

    private final VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand> drawList;


    public Puck(float radius, float height, int numPointsAroundPuck) {

        ObjectBuilder.GeneratedData puck = ObjectBuilder.createPuck(
                new Geometry.Cylinder(new Geometry.Point(0f, 0f, 0f), radius, height),
                numPointsAroundPuck);

        vertexArray = new VertexArray(puck.vertexData);
        drawList = puck.drawCommanList;

        this.radius = radius;
        this.height = height;

        Matrix.setIdentityM(modelMatrix, 0);
    }

    @Override
    public void bindData(TextureShaderProgram shaderProgram) {

    }

    public void bindData(ColorShaderProgram colorShaderProgram) {
        vertexArray.setVertexAttributePointer(
                colorShaderProgram.aColorLocation,
                POSITION_COMPONENT_COUNT,
                0, 0);
    }

    public void draw() {
        for (ObjectBuilder.DrawCommand drawCommand : drawList) {
            drawCommand.draw();
        }
    }
}
