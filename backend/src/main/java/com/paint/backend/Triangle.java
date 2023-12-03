package com.paint.backend;

import java.util.Map;
import java.awt.Point;
import java.util.ArrayList;

public class Triangle extends PolygonShape {

    public Triangle() {
    }
    public Triangle(Map<String,Object> attributes) {
        super(attributes);
    }

    @Override
    public void update(Map<String,Object> attributes) {
        update((ArrayList<Point>) attributes.get("points"));
    }

    public void update(ArrayList<Point> points) {
        setPoints(points);
    }

    @Override
    public Shape clone() {
        Triangle clone = new Triangle(Map.of("points", getPoints()));
        return clone;
    }

    @Override
    public Map<String, Object> read() {
        return Map.of(
                "id", getId(),
                "points", getPoints());
    }

}
