package org.app.opengl_es_android_version.util;

public class Geometry {

    /**
     * 点
     */
    public static class Point {
        public final float x, y, z;

        public Point(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Point translateX(float value) {
            return new Point(x + value, y, z);
        }

        public Point translateY(float value) {
            return new Point(x, y + value, z);
        }

        public Point translateZ(float value) {
            return new Point(x, y, z + value);
        }

        public Point translate(Vector vector) {
            return new Point(this.x + vector.x, this.y + vector.y, this.z + vector.z);
        }
    }

    /**
     * 圆形 = 中心点+半径
     */
    public static class Circle {
        //中心点
        public final Point center;
        //半径
        public final float radius;
        //分度值，分度越小，越像一个圆形
        public final float angdeg = 20;

        public Circle(Point center, float radius) {
            this.center = center;
            this.radius = radius;
        }

        public Circle scale(float scale) {
            return new Circle(center, radius * scale);
        }
    }

    /***
     * 圆柱体
     */
    public static class Cylinder {
        public final Point center;
        public final float radius;
        public final float height;

        public Cylinder(Point center, float radius, float height) {
            this.center = center;
            this.radius = radius;
            this.height = height;
        }
    }

    /**
     * 球 = 球心点 + 半径
     */
    public static class Sphere {
        public final Point center;       //中心原点
        public final float radius;      //半径

        public Sphere(Point center, float radius) {
            this.center = center;
            this.radius = radius;
        }
    }

    /***
     * 矩形，中心点 + 高 + 宽
     */
    public static class Rectangle {

        public final Point point;
        public final float height;
        public final float width;

        public Rectangle(Point point, float height, float width) {
            this.point = point;
            this.height = height;
            this.width = width;
        }
    }

    /**
     * 方向 向量
     */
    public static class Vector {
        public final float x;
        public final float y;
        public final float z;

        public Vector(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        /****
         * 求标量长度值
         * @return
         */
        public float length() {
            return (float) Math.sqrt(x * x + y * y + z * z);
        }

        //差积,矩阵形式运算
        public Vector crossProduct(Vector other) {
            return new Vector(
                    (y * other.z) - (z * other.y),
                    (x * other.z) - (z * other.x),
                    (x * other.y) - (y * other.x)
            );
        }

        public float dotProduct(Vector other) {
            return x * other.x +
                    y * other.y +
                    z * other.z;
        }

        public Vector scale(float f) {
            return new Vector(x * f, y * f, z * f);
        }
    }

    /**
     * 射线 = 起点 + 方向向量
     */
    public static class Ray {
        public final Point point;
        public final Vector vector;

        public Ray(Point point, Vector vector) {
            this.point = point;
            this.vector = vector;
        }
    }

    /**
     * 平面 = 平面切点 + 法向量
     */
    public static class Plane {
        public final Point point;
        public final Vector normal;

        public Plane(Point point, Vector normal) {
            this.point = point;
            this.normal = normal;
        }
    }

    /**
     * 向量相减 A-B = BA
     *
     * @param from A
     * @param to   B
     * @return BA
     */
    public static Vector vectorBetween(Point from, Point to) {
        return new Vector(
                to.x - from.x,
                to.y - from.y,
                to.z - from.z);
    }


    public static boolean intersects(Ray ray, Sphere sphere) {
        return sphere.radius > distanceBetween(sphere.center, ray);
    }

    /******
     *
     * 求包围球中心点于射线的距离
     */
    public static float distanceBetween(Point centerPoint, Ray ray) {
        //第一个向量 开始点到球心
        Vector vStart2Center = vectorBetween(ray.point, centerPoint);
        //第二个向量 结束点到球心,结束点 = 开始点 + 向量
        Vector vEnd2Center = vectorBetween(
                ray.point.translate(ray.vector),
                centerPoint);
        //两个向量的叉积
        Vector crossProduct = vStart2Center.crossProduct(vEnd2Center);
        //两个向量叉积的值大小 = 三角形面积 *2
        float areaOf2 = crossProduct.length();
        //射线的长度 = 三角形的底边长
        float lengthOfRay = ray.vector.length();
        //高 = 面积*2/底边长度
        float distanceFromSphereCenterIfRay = areaOf2 / lengthOfRay;

        return distanceFromSphereCenterIfRay;
    }


    public static Point intersectionPoint(Ray ray, Plane plane) {
        //射线起点到平面视点的向量
        Vector rayToPlaneVector = vectorBetween(ray.point, plane.point);
        //射线起点到平面向量 与 法向量的点积 / 射线向量 与 法向量的电积 = 缩放因子
        float scaleFactor = rayToPlaneVector.dotProduct(plane.normal)
                / ray.vector.dotProduct(plane.normal);
        //根据缩放因子，缩放射线向量，再从射线起点开始 沿着 缩放后的射线向量，得出与平面的交点
        Point intersectionPoint = ray.point.translate(ray.vector.scale(scaleFactor));
        return intersectionPoint;
    }


}
