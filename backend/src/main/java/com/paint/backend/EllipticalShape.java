package com.paint.backend;

import java.util.Map;
import java.awt.geom.Point2D;

public class EllipticalShape implements Shape {
    int id;
    String type;
    Point2D.Float center;
    float scaleX;
    float scaleY;
    float rotation;
    String color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Point2D.Float getCenter() {
        return center;
    }

    public void setCenter(Point2D.Float center) {
        this.center = center;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public Shape clone(int newId) {
        EllipticalShape clone = new EllipticalShape();
        clone.setCenter(getCenter());
        return clone;
    }

    @Override
    public void update(Map<String, Object> attributes) {

        return;
    }

    @Override
    public Map<String, Object> read() {
        return Map.of(
                "id", getId(),
                "center", getCenter());
    }
}
