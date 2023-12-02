package com.paint.backend;

import java.awt.Point;
import java.util.Map;

public class Circle extends EllipticalShape {
    private int radius;

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Circle(Object... attributes) {
        update(attributes);

    }

    @Override
    public Shape clone() {
        Circle clone = new Circle(getCenter(), getRadius());
        return clone;
    }

    @Override
    public void update(Object... attributes) {
        update((Point) attributes[0], (int) attributes[1]);
    }

    public void update(Point center, int radius) {
        setCenter(center);
        setRadius(radius);
    }

    @Override
    public Map<String, Object> read() {
        return Map.of(
                "id", getId(),
                "center", getCenter(),
                "radius", getRadius());
    }
}
