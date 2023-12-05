package com.paint.backend;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShapeManager {
    private static ShapeManager instance;
    private static Map<Integer, Shape> shapes = new HashMap<>();

    private ShapeManager() {
    }

    public static synchronized ShapeManager getInstance() {
        if (instance == null) {
            instance = new ShapeManager();
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
        ShapeManager.getInstance().addShape(clone);
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
        return ShapeFactory.create(shapeType, attributes);
    }
}
