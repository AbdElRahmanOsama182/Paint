package com.paint.backend;

import java.util.HashMap;
import java.util.Map;

public class ShapeManager {
    private static ShapeManager instance;
    private static Map<Integer, IShape> shapeMap = new HashMap<>();
    private static int nextId = 1;

    private ShapeManager() {
    }
    public static synchronized ShapeManager getInstance() {
        if (instance == null) {
            instance = new ShapeManager();
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


}
