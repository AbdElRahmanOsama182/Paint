package com.paint.backend;

public class ShapeFactory {
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
