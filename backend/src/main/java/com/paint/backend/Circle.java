package com.paint.backend;

import java.awt.geom.Point2D;
import java.util.Map;

public class Circle extends EllipticalShape {
    private float radius;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Circle() {

    }

    public Circle(Map<String, Object> attributes) {
        setType("circle");
        update(attributes);
    }

    @Override
    public Shape clone(int newId) {
        Circle clone = new Circle(
                Map.of("type", "circle", "center", getCenter(), "radius", getRadius(), "rotation", getRotation(),
                        "color", getColor(), "scaleX", getScaleX(), "scaleY", getScaleY(), "id", newId));

        return clone;
    }

    @Override
    public void update(Map<String, Object> attributes) {
        update((int) attributes.get("id"), (String) attributes.get("type"),
                (Point2D.Float) attributes.get("center"), (float) attributes.get("radius"),
                (float) attributes.get("rotation"), (String) attributes.get("color"),
                (float) attributes.get("scaleX"), (float) attributes.get("scaleY"));

    }

    public void update(int id, String type, Point2D.Float center, float radius, float rotation, String color,
            float scaleX,
            float scaleY) {
        setType(type);
        setCenter(center);
        setRadius(radius);
        setRotation(rotation);
        setColor(color);
        setScaleX(scaleX);
        setScaleY(scaleY);
        setId(id);

    }

    @Override
    public Map<String, Object> read() {
        return Map.of(
                "id", getId(),
                "type", "circle",
                "center", getCenter(),
                "radius", getRadius(),
                "scaleX", getScaleX(),
                "scaleY", getScaleY(),
                "rotation", getRotation(),
                "color", getColor());
    }
}
