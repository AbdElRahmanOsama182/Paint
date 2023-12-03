package com.paint.backend;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;

public class PolygonShape implements Shape {
    int id;
    ArrayList<Point> points;

    public PolygonShape() {

    }
    public PolygonShape(Map<String,Object> attributes) {

        update(attributes);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    @Override
    public Shape clone() {
        PolygonShape clone = new PolygonShape(Map.of("points", getPoints()));

        return clone;
    }

    @Override
    public void update(Map<String,Object> attributes) {
        update((ArrayList<Point>) attributes.get("points"));

    }

    public void update(ArrayList<Point> points) {
        setPoints(points);
    }

    @Override
    public Map<String, Object> read() {
        return Map.of(
                "id", getId(),
                "points", getPoints());
    }

    // public static void main(String[] args) {
    //     Shape c = ShapeFactory.getInstance().create("circle", new Point(0, 0), 5);
    //     ShapeFactory.getInstance().clone(0);
    //     ArrayList<Shape> shapes = ShapeFactory.getInstance().getAllShapes();
    //     for (Shape s : shapes) {
    //         System.out.println(ShapeFactory.getInstance().read(s.getId()));
    //     }
    // }

}
