package com.paint.backend;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShapeFactory {
    private static ShapeFactory instance;
    private static Map<Integer, Shape> shapes = new HashMap<>();

    private ShapeFactory() {
    }

    public static synchronized ShapeFactory getInstance() {
        if (instance == null) {
            instance = new ShapeFactory();
        }
        return instance;
    }

    public static void addShape(Shape shape) {
        shapes.put(shape.getId(), shape);
    }

    public static Shape update(int id, Map<String, Object> attributes) {

        Shape shape = shapes.get(id);
        if (shape != null) {
            shape.update(attributes);
        }
        return shape;

    }

    public static void delete(int id) {
        shapes.remove(id);
    }

    public static Shape clone(int id) {
        Shape shape = shapes.get(id);
        Shape clone = null;
        if (shape != null) {
            clone = shape.clone();
        }
        ShapeFactory.getInstance().addShape(clone);
        return clone;
    }

    public static Map<String, Object> read(int id) {
        Shape shape = shapes.get(id);
        if (shape != null) {
            return shape.read();
        }
        return null;
    }

    public static void clear() {
        shapes.clear();
    }

    public static Map<Integer, Shape> getAllShapes() {
        return shapes;
    }

    public static Shape create(String shapeType, Map<String, Object> attributes) {

        if (shapeType == null) {
            return null;
        }
        Shape shape = null;
        if (shapeType.equalsIgnoreCase("LINE")) {
            shape = new Line(attributes);
        } else if (shapeType.equalsIgnoreCase("TRIANGLE")) {
            shape = new Triangle(attributes);
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            shape = new Rectangle(attributes);
        } else if (shapeType.equalsIgnoreCase("SQUARE")) {
            shape = new Square(attributes);
        } else if (shapeType.equalsIgnoreCase("CIRCLE")) {
            shape = new Circle(attributes);
        } else if (shapeType.equalsIgnoreCase("ELLIPSE")) {
            shape = new Ellipse(attributes);
        } else if (shapeType.equalsIgnoreCase("POLYGON")) {
            shape = new PolygonShape(attributes);
        }
        ShapeFactory.getInstance().addShape(shape);
        return shape;
    }
}
