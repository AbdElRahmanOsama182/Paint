package com.paint.backend;

import java.util.Map;
import java.awt.Point;
import java.util.ArrayList;

public class Line extends PolygonShape {

    public Line() {
    }

    public Line(Map<String,Object> attributes) {
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
        Line clone = new Line(Map.of("points", getPoints()));

        return clone;
    }

    @Override
    public Map<String, Object> read() {
        return Map.of(
                "id", getId(),
                "points", getPoints());
    }

}