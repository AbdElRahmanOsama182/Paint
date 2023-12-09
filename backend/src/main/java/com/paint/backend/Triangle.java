package com.paint.backend;

import java.util.Map;

public class Triangle extends PolygonShape {
    String type = "triangle";

    public Triangle() {
    }

    public Triangle(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getType() {
        return type;
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
        // print data
        System.out.println("Triangle data:");
        System.out.println("id: " + getId());
        System.out.println("type: " + getType());
        System.out.println("center: " + getCenter());
        System.out.println("radius: " + getRadius());
        System.out.println("scaleX: " + getScaleX());
        System.out.println("scaleY: " + getScaleY());
        System.out.println("rotation: " + getRotation());
        System.out.println("color: " + getColor());
        System.out.println("sides: " + getSides());


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
