package com.paint.backend;

import java.awt.geom.Point2D;
import java.util.Map;

public class Polygon extends Shape {
    private int sidesNumber;
    private Point2D.Float[] points;

    public Polygon(Object... attributes) {
        setType("polygon");
        update(attributes);
        ShapeFactory.getInstance().addShape(this);
    }

    public Polygon(String type, Object... attributes) {
        setType(type);
        update(attributes);
        ShapeFactory.getInstance().addShape(this);
    }
    public int getSidesNumber() {
        return sidesNumber;
    }

    public void setSidesNumber(int sidesNumber) {
        this.sidesNumber = sidesNumber;
    }

    public Point2D.Float[] getPoints() {
        return points;
    }

    public void setPoints(Point2D.Float[] points) {
        this.points = points;
    }

    @Override
    public IShape clone() {
        Polygon clone;
        if (getType().equalsIgnoreCase("line")) {
            clone = new Polygon("line");
        } else if (getType().equalsIgnoreCase("triangle")) {
            clone = new Polygon("triangle");
        } else {
            clone = new Polygon();
        }
        clone.setSidesNumber(getSidesNumber());
        clone.setPoints(getPoints());
        return clone;
    }

    @Override
    public void update(Object... attributes) {
        if (attributes.length % 2 != 0) {
            throw new IllegalArgumentException("Attributes must be in pairs");
        }
        for (int i = 0; i < attributes.length; i += 2) {
            String key = (String) attributes[i];
            Object value = attributes[i + 1];
            switch (key) {
                case "sidesNumber":
                    setSidesNumber((int) value);
                    break;
                case "points":
                    setPoints((Point2D.Float[]) value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid attribute: " + key);
            }
        }
    }

    @Override
    public Map<String, Object> read() {
        return Map.of(
                "id", getId(),
                "type", getType(),
                "sidesNumber", getSidesNumber(),
                "points", getPoints()
        );
    }
}
