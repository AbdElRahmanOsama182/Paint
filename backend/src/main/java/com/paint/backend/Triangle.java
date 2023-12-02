package com.paint.backend;

import java.util.Map;
import java.awt.Point;
import java.util.ArrayList;

public class Triangle extends PolygonShape {

    public Triangle(Object... attributes) {
        update(attributes);
    }

    @Override
    public void update(Object... attributes) {
        update((ArrayList<Point>) attributes[0]);
    }

    public void update(ArrayList<Point> points) {
        setPoints(points);
    }

    @Override
    public Shape clone() {
        Triangle clone = new Triangle(getPoints());
        return clone;
    }

    @Override
    public Map<String, Object> read() {
        return Map.of(
                "id", getId(),
                "points", getPoints());
    }

}
