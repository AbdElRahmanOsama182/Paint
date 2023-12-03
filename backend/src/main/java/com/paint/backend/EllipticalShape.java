package com.paint.backend;

import java.util.Map;
import java.awt.Point;

public class EllipticalShape implements Shape {
    int id;
    Point center;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    @Override
    public Shape clone() {
        EllipticalShape clone = new EllipticalShape();
        clone.setCenter(getCenter());
        return clone;
    }

    @Override
    public void update(Map<String,Object> attributes) {

        return;
    }

    @Override
    public Map<String, Object> read() {
        return Map.of(
                "id", getId(),
                "center", getCenter());
    }
}
