package org.app.opengl_es_android_version.util;

import android.opengl.GLES20;

import java.util.ArrayList;
import java.util.List;

public class ObjectBuilder {

    private final List<DrawCommand> drawList = new ArrayList<>();

    private static final int FLOAT_PER_VERTEX = 3;
    private final float[] vertexData;
    private int offset = 0;

    private ObjectBuilder(int sizeInVertices) {
        vertexData = new float[sizeInVertices * FLOAT_PER_VERTEX];
    }

    private void createCircle(Geometry.Circle circle, int numPoints) {
        final int nubVertices = sizeOfCircleInVertices(numPoints);
        final int startVertex = offset / FLOAT_PER_VERTEX;

        vertexData[offset++] = circle.center.x;
        vertexData[offset++] = circle.center.y;
        vertexData[offset++] = circle.center.z;

        for (int i = 0; i <= numPoints; i++) {
            float angleInRadians =
                    ((float) i / (float) numPoints)
                            * ((float) Math.PI * 2f);

            vertexData[offset++] = circle.center.x + circle.radius * (float) Math.cos(angleInRadians);
            vertexData[offset++] = circle.center.y;
            vertexData[offset++] = circle.center.z + circle.radius * (float) Math.sin(angleInRadians);
        }

        drawList.add(new DrawCommand() {
            @Override
            public void draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, startVertex, nubVertices);
            }
        });
    }

    private void createCylinder(Geometry.Cylinder cylinder, final int numPoints) {

        final int startVertex = offset / FLOAT_PER_VERTEX;
        final int numVertex = sizeOfCylinderInVertices(numPoints);

        final float yStart = cylinder.center.y - (cylinder.height / 2);
        final float yEnd = cylinder.center.y + (cylinder.height / 2);

        for (int i = 0; i <= numPoints; i++) {
            float angleInRadians =
                    ((float) i / (float) numPoints)
                            * ((float) Math.PI * 2f);

            float xPosition = cylinder.center.x + cylinder.radius * (float) Math.cos(angleInRadians);
            float zPosition = cylinder.center.z + cylinder.radius * (float) Math.sin(angleInRadians);

            vertexData[offset++] = xPosition;
            vertexData[offset++] = yStart;
            vertexData[offset++] = zPosition;

            vertexData[offset++] = xPosition;
            vertexData[offset++] = yEnd;
            vertexData[offset++] = zPosition;
        }
        drawList.add(new DrawCommand() {
            @Override
            public void draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, startVertex, numVertex);
            }
        });
    }

    private GeneratedData build() {
        return new GeneratedData(drawList, vertexData);
    }


    public static GeneratedData createPuck(Geometry.Cylinder puck, int numPoints) {
        int size = sizeOfCircleInVertices(numPoints) +
                sizeOfCylinderInVertices(numPoints);

        ObjectBuilder builder = new ObjectBuilder(size);

        Geometry.Circle puckTop = new Geometry.Circle(
                puck.center.translateY(puck.height / 2),
                puck.radius);

        builder.createCircle(puckTop, numPoints);
        builder.createCylinder(puck, numPoints);

        return builder.build();
    }

    public static GeneratedData createPuck(float centerX, float centerY, float centerZ,
                                           float radius, float height) {
        int numPoints = 32;

        Geometry.Cylinder puck = new Geometry.Cylinder(new Geometry.Point(centerX, centerY, centerZ), radius, height);

        int size = sizeOfCircleInVertices(numPoints) +
                sizeOfCylinderInVertices(numPoints);

        ObjectBuilder builder = new ObjectBuilder(size);

        Geometry.Circle puckTop = new Geometry.Circle(
                puck.center.translateY(puck.height / 2),
                puck.radius);

        builder.createCircle(puckTop, numPoints);
        builder.createCylinder(puck, numPoints);

        return builder.build();
    }

    public static GeneratedData createMallet(Geometry.Point center, float radius, float height, int numPoints) {

        int size = (sizeOfCircleInVertices(numPoints) + sizeOfCylinderInVertices(numPoints)) * 2;
        ObjectBuilder objectBuilder = new ObjectBuilder(size);

        //底座部分
        float baseHeight = height * 0.25f;
        Geometry.Circle baseCircle = new Geometry.Circle(
                center.translateY(-baseHeight),
                radius
        );
        Geometry.Cylinder baseCylinder = new Geometry.Cylinder(
                baseCircle.center.translateY(-baseHeight / 2f),
                radius,
                baseHeight
        );
        objectBuilder.createCircle(baseCircle, numPoints);
        objectBuilder.createCylinder(baseCylinder, numPoints);

        //上半部分
        float handleHeight = height * 0.75f;
        float handleRadius = radius / 3f;
        Geometry.Circle handleCircle = new Geometry.Circle(
                center.translateY(height * 0.5f),
                handleRadius
        );
        Geometry.Cylinder handleCylinder = new Geometry.Cylinder(
                baseCircle.center.translateY(handleHeight / 2f),
                handleRadius,
                handleHeight
        );
        objectBuilder.createCircle(handleCircle, numPoints);
        objectBuilder.createCylinder(handleCylinder, numPoints);

        return objectBuilder.build();
    }

    public static GeneratedData createMallet(float centerX, float centerY, float centerZ,
                                             float radius, float height) {
        int numPoints = 32;
        int size = (sizeOfCircleInVertices(numPoints) + sizeOfCylinderInVertices(numPoints)) * 2;
        ObjectBuilder objectBuilder = new ObjectBuilder(size);

        Geometry.Point center = new Geometry.Point(centerX, centerY, centerZ);
        //底座部分
        float baseHeight = height * 0.25f;
        Geometry.Circle baseCircle = new Geometry.Circle(
                center.translateY(-baseHeight),
                radius
        );
        Geometry.Cylinder baseCylinder = new Geometry.Cylinder(
                baseCircle.center.translateY(-baseHeight / 2f),
                radius,
                baseHeight
        );
        objectBuilder.createCircle(baseCircle, numPoints);
        objectBuilder.createCylinder(baseCylinder, numPoints);
        //上半部分
        float handleHeight = height * 0.75f;
        float handleRadius = radius / 3f;
        Geometry.Circle handleCircle = new Geometry.Circle(
                center.translateY(height * 0.5f),
                handleRadius
        );
        Geometry.Cylinder handleCylinder = new Geometry.Cylinder(
                baseCircle.center.translateY(handleHeight / 2f),
                handleRadius,
                handleHeight
        );
        objectBuilder.createCircle(handleCircle, numPoints);
        objectBuilder.createCylinder(handleCylinder, numPoints);

        return objectBuilder.build();
    }


    public interface DrawCommand {
        void draw();
    }

    //构建一个圆所需的顶点数
    private static int sizeOfCircleInVertices(int numPoints) {
        return 1 + (numPoints + 1);
    }

    //构建一个圆柱侧面所需顶点数
    private static int sizeOfCylinderInVertices(int numPoints) {
        return (numPoints + 1) * 2;
    }

    public static class GeneratedData {
        public final List<DrawCommand> drawCommanList;
        public final float[] vertexData;

        GeneratedData(List<DrawCommand> drawCommonList, float[] vertexData) {
            this.drawCommanList = drawCommonList;
            this.vertexData = vertexData;
        }
    }

}
