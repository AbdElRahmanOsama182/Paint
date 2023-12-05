package com.paint.backend;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Map;

public class PolygonShape implements Shape {
    int id;
    Point2D.Float center;
    float radius;
    float scaleX;
    float scaleY;
    float rotation;
    String color;
    int sides;

    public PolygonShape() {

    }

    public PolygonShape(Map<String, Object> attributes) {

        update(attributes);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Point2D.Float getCenter() {
        return center;
    }

    public void setCenter(Point2D.Float center) {
        this.center = center;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public int getSides() {
        return sides;
    }

    public void setSides(int sides) {
        this.sides = sides;

    }

    @Override
    public Shape clone(int newId) {
        PolygonShape clone = new PolygonShape(Map.of("center", getCenter(), "radius", getRadius(), "sides", getSides(),
                "color", getColor(), "scaleX", getScaleX(), "scaleY", getScaleY(), "rotation", getRotation(), "id", newId));

        return clone;
    }

    @Override
    public void update(Map<String, Object> attributes) {
        update((int) attributes.get("id") ,(Point2D.Float) attributes.get("center"), (float) attributes.get("radius"),
                (int) attributes.get("sides"),
                (String) attributes.get("color"), (float) attributes.get("scaleX"), (float) attributes.get("scaleY"),
                (float) attributes.get("rotation"));

    }

    public void update(int id,Point2D.Float center, float radius, int sides, String color, float scaleX, float scaleY,
            float rotation) {
        setCenter(center);
        setRadius(radius);
        setSides(sides);
        setColor(color);
        setScaleX(scaleX);
        setScaleY(scaleY);
        setRotation(rotation);
        setId(id);
    }

    @Override
    public Map<String, Object> read() {
        return Map.of(
                "id", getId(),
                "center", getCenter(),
                "radius", getRadius(),
                "scaleX", getScaleX(),
                "scaleY", getScaleY(),
                "rotation", getRotation(),
                "color", getColor(),
                "sides", getSides());
    }

    // public static void main(String[] args) {
    // Shape c = ShapeManager.getInstance().create("circle", new Point(0, 0), 5);
    // ShapeManager.getInstance().clone(0);
    // ArrayList<Shape> shapes = ShapeManager.getInstance().getAllShapes();
    // for (Shape s : shapes) {
    // System.out.println(ShapeManager.getInstance().read(s.getId()));
    // }
    // }

}
