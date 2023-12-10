package com.paint.backend;

import java.util.Map;

public class ShapeFactory {
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
        }else if (shapeType.equalsIgnoreCase("IMAGE")) {
            shape = new ImageShape(attributes);
        }
        ShapeManager.getInstance().addShape(shape);
        return shape;
    }

}
