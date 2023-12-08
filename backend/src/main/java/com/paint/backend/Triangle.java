package com.paint.backend;

import java.util.Map;

public class Triangle extends PolygonShape {

    public Triangle() {
    }

    public Triangle(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public void update(Map<String, Object> attributes) {
        super.update(attributes);
    }

    @Override
    public Shape clone() {
        Triangle clone = new Triangle(Map.of("center", getCenter(), "radius", getRadius(),
                "rotation", getRotation(), "color", getColor(), "scaleX", getScaleX(),
                "scaleY", getScaleY(), "sides", getSides()));

        return clone;
    }

    @Override
    public Map<String, Object> read() {
        return Map.of(
                "id", getId(),
                "type", getType(),
                "center", getCenter(),
                "radius", getRadius(),
                "scaleX", getScaleX(),
                "scaleY", getScaleY(),
                "rotation", getRotation(),
                "color", getColor(),
                "sides", getSides());
    }

}
