package com.paint.backend;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Serializable;
import com.fasterxml.jackson.core.type.TypeReference;
import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ShapeManager {
    private static ShapeManager instance;
    private static Map<Integer, Shape> shapes = new HashMap<>();
    private static int nextHistory = -1;
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
        history.clear();
    }

    public static Map<Integer, Shape> getAllShapes() {
        return shapes;
    }

    public static Shape create(String shapeType, Map<String, Object> attributes) {
        return ShapeFactory.create(shapeType, attributes);
    }

    public static void undo() {
        System.out.println("undo");
        System.out.println(shapes);

        if (nextHistory > 0) {
            nextHistory--;
            shapes = history.get(nextHistory);
        }
    }

    public static void redo() {
        System.out.println("redo");
        System.out.println(shapes);
        if (nextHistory < history.size() - 1) {
            nextHistory++;
            shapes = history.get(nextHistory);
        }
    }

    public void saveRecord() {
        System.out.println("saveRecord");
        System.out.println(history);
        nextHistory++;
        history.add(new HashMap<>(shapes));
        if (nextHistory < history.size() - 1) {
            history.subList(nextHistory + 1, history.size()).clear();
        }
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
            loadJson(fileContents);
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
                Map<Integer, Shape> shapes = (Map<Integer, Shape>) ShapeManager.getInstance().getAllShapes();
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonShapes = objectMapper.writeValueAsString(shapes);
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

    public void loadJson(String fileContents) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(shapes);
            ShapeManager.getInstance().clear();
            Map<Integer, Shape> loaded = (Map<Integer, Shape>) objectMapper.readValue(new File("./ho.json"), Map.class);
            System.out.println("loadJsonFromFile");
            System.out.println(loaded);
            shapes = loaded;
            System.out.println(shapes);
            /*
             * TypeReference<Map<Integer, Shape>> typeReference = new
             * TypeReference<Map<Integer, Shape>>() {
             * };
             * Map<Integer, Shape> shapes = objectMapper.readValue(fileContents,
             * typeReference);
             * System.out.println(shapes);
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
