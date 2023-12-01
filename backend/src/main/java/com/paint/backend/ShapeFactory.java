package com.paint.backend;

import java.util.HashMap;
import java.util.Map;

public class ShapeFactory {
    private static ShapeFactory instance;
    private static Map<Integer, IShape> shapeMap = new HashMap<>();
    private static int nextId = 1;

    private ShapeFactory() {
    }
    public static synchronized ShapeFactory getInstance() {
        if (instance == null) {
            instance = new ShapeFactory();
        }
        return instance;
    }

    public static void addShape(IShape shape) {
        int shapeId = nextId++;
        shape.setId(shapeId);
        shapeMap.put(shapeId, shape);
    }

    public static IShape getShape(int id) {
        return shapeMap.get(id);
    }

    public static void updateShape(int id, Object... attributes) {
        IShape shape = shapeMap.get(id);
        if (shape != null) {
            shape.update(attributes);
        }
    }

    public static void deleteShape(int id) {
        shapeMap.remove(id);
    }

    public static IShape cloneShape(int id) {
        IShape shape = shapeMap.get(id);
        if (shape != null) {
            return shape.clone();
        }
        return null;
    }

    public static Map<String, Object> readShape(int id) {
        IShape shape = shapeMap.get(id);
        if (shape != null) {
            return shape.read();
        }
        return null;
    }

    public static void clear() {
        shapeMap.clear();
    }
    public static Map<Integer, IShape> getAllShapes() {
        return new HashMap<>(shapeMap);
    }
    public static IShape create(String shapeType, Object... attributes) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("LINE")) {
            return new Line(attributes);
        } else if (shapeType.equalsIgnoreCase("TRIANGLE")) {
            return new Triangle(attributes);
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle(attributes);
        } else if (shapeType.equalsIgnoreCase("SQUARE")) {
            return new Square(attributes);
        } else if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle(attributes);
        } else if (shapeType.equalsIgnoreCase("ELLIPSE")) {
            return new Ellipse(attributes);
        } else if (shapeType.equalsIgnoreCase("POLYGON")) {
            return new Polygon(attributes);
        }
        return null;
    }
}
