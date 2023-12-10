package com.paint.backend;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;

public class ShapeManager {
    private static ShapeManager instance;
    private static Map<Integer, Shape> shapes = new HashMap<>();
    private static int nextHistory = 0;
    private static ArrayList<Map<Integer, Shape>> history = new ArrayList<>();

    private ShapeManager() {
    }

    public static synchronized ShapeManager getInstance() {
        if (instance == null) {
            instance = new ShapeManager();
            instance.history.add(new HashMap<>());
            instance.nextHistory++;
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
        System.out.println("delete");
        System.out.println(id);
        System.out.println(shapes);
    }

    public static Shape clone(int id, int newId) {
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
        shapes = new HashMap<>();
        // nextHistory = -1;
        // instance = null;
        // history.clear();
    }

    public static Map<Integer, Shape> getAllShapes() {
        return shapes;
    }

    public static Shape create(String shapeType, Map<String, Object> attributes) {
        Shape shape =ShapeFactory.create(shapeType, attributes);
        System.out.println("created");
        System.out.println(shapes);
        return shape;
    }

    public static void undo() {
        System.out.println("undo");
        System.out.println("old");

        System.out.println(shapes);
        System.out.println(nextHistory);

        if (nextHistory >1) {
            nextHistory--;
            shapes = history.get(nextHistory-1);
            System.out.println("history");
            System.out.println(history);
        }
        System.out.println("new");
        System.out.println(shapes);
    }

    public static void redo() {
        System.out.println("redo");
        System.out.println(shapes);
        if (nextHistory < history.size() ) {
            shapes = history.get(nextHistory);
            nextHistory++;
        }
    }

    public void saveRecord() {
        nextHistory++;
        System.out.println(nextHistory);
        history.add(new HashMap<>());
        history.get(history.size()-1).putAll(shapes);
        if (nextHistory < history.size() ) {
            history.subList(nextHistory , history.size()).clear();
        }
        System.out.println("saveRecord");
        System.out.println(history);
    }

    public void clearHistory() {
        history.clear();
        shapes.clear();
        shapes = new HashMap<>();
        history.add(new HashMap<>());
        nextHistory = 1;
        System.out.println(history);
        System.out.println(shapes);
    }

    public ResponseEntity<String> save(String extension) {
        if (extension.equals("xml")) {
            return saveXml();
        } else {
            return saveJson();
        }
    }

    public ResponseEntity<String> saveXml() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            XMLEncoder encoder = new XMLEncoder(bos);
            encoder.writeObject(ShapeManager.getInstance().getAllShapes());
            encoder.close();
            bos.close();
            String xmlContent = bos.toString("UTF-8");
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_XML)
                    .body(xmlContent);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving XML");
        }
    }

    public ResponseEntity<String> saveJson() {
        try {
            Map<Integer, Shape> shapes = (Map<Integer, Shape>) ShapeManager.getInstance().getAllShapes();
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonShapes = objectMapper.writeValueAsString(shapes);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jsonShapes);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(500).body("Error processing JSON");
        }
    }

    public ResponseEntity<String> load(String extension, String fileContents) {
        if (extension.equals("xml")) {
            return loadXml(fileContents);
        } else {
            loadJson();
        }
        return null;
    }

    public ResponseEntity<String> loadXml(String fileContents) {
        try {
            ShapeManager.getInstance().clear();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContents.getBytes());
            XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(inputStream));
            Map<Integer, Shape> loaded = (Map<Integer, Shape>) decoder.readObject();
            decoder.close();

            System.out.println(loaded);
            shapes = loaded;
            System.out.println("loadXml");
            System.out.println(shapes);
            Map<Integer, Shape> sh = (Map<Integer, Shape>) ShapeManager.getInstance().getAllShapes();
            for (Map.Entry<Integer, Shape> entry : sh.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue().read());
            }
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonShapes = objectMapper.writeValueAsString(loaded);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(jsonShapes);
            } catch (JsonProcessingException e) {
                return ResponseEntity.status(500).body("Error processing JSON");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void loadJson() {
        ShapeManager.getInstance().clear();
    }
}
