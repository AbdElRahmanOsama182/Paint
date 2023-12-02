package com.paint.backend;

import java.util.Map;
import java.awt.Point;
import java.util.ArrayList;

public class Square extends PolygonShape {

    public Square(Object... attributes) {
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
        Square clone = new Square(getPoints());
        return clone;
    }

    @Override
    public Map<String, Object> read() {
        return Map.of(
                "id", getId(),
                "points", getPoints());
    }

}
