package com.paint.backend;

import java.util.Map;

public class Square extends Rectangle {

    public Square() {
    }

    public Square(Map<String, Object> attributes) {
        setType("square");
        super.update(attributes);
    }

    @Override
    public void update(Map<String, Object> attributes) {
        super.update(attributes);
    }

    @Override
    public Shape clone() {
        Square clone = new Square(Map.of("type", getType(),
                "height", getHeight(), "width", getWidth(), "x", getX(),
                "y", getY(), "scaleX", getScaleX(), "scaleY", getScaleY(), "rotation",
                getRotation(), "color", getColor()));
        return clone;
    }

    @Override
    public Map<String, Object> read() {
        return super.read();
    }
}
