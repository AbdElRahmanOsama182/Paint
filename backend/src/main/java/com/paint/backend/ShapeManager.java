package com.paint.backend;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShapeManager {
    private static ShapeManager instance;
    private static Map<Integer, Shape> shapes = new HashMap<>();
    private static int nextHistory=-1;
    private static ArrayList<Map<Integer, Shape>> history = new ArrayList<>();

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

    public static Shape clone(int id,int newId) {
        Shape shape = shapes.get(id);
        Shape clone = null;
        if (shape != null) {
            clone = shape.clone(newId);
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

    public static void undo() {
        if (nextHistory > 0) {
            nextHistory--;
            shapes = history.get(nextHistory);
        }
    }
    public static void redo() {
        if (nextHistory < history.size()-1) {
            nextHistory++;
            shapes = history.get(nextHistory);
        }
    }

    public void saveRecord() {
        nextHistory++;
        history.add(new HashMap<>(shapes));
        if (nextHistory < history.size()-1) {
            history.subList(nextHistory+1, history.size()).clear();
        }
    }
}
