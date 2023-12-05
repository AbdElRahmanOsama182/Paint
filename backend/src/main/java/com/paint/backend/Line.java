package com.paint.backend;

import java.util.Map;
import java.awt.Point;
import java.util.ArrayList;

public class Line implements Shape {
    int id;
    ArrayList<Float> points;
    String color;
    float scaleX;
    float scaleY;
    float rotation;

    public Line(ArrayList<Float> points, String color, float scaleX, float scaleY, float rotation) {
        this.points = points;
        this.color = color;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.rotation = rotation;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Line() {
    }

    public Line(Map<String, Object> attributes) {
        update(attributes);
    }

    public ArrayList<Float> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Float> points) {
        this.points = points;
    }

    @Override
    public void update(Map<String, Object> attributes) {
        update((ArrayList<Float>) attributes.get("points"), (String) attributes.get("color"),
                (float) attributes.get("scaleX"), (float) attributes.get("scaleY"),
                (float) attributes.get("rotation"));

    }

    public void update(ArrayList<Float> points, String color, float scaleX, float scaleY, float rotation) {
        setPoints(points);
        setColor(color);
        setScaleX(scaleX);
        setScaleY(scaleY);
        setRotation(rotation);

    }

    @Override
    public Shape clone() {
        Line clone = new Line(Map.of("points", getPoints(), "color", getColor(), "scaleX", getScaleX(),
                "scaleY", getScaleY(), "rotation", getRotation()));

        return clone;
    }

    @Override
    public Map<String, Object> read() {
        return Map.of(
                "id", getId(),
                "points", getPoints(),
                "scaleX", getScaleX(),
                "scaleY", getScaleY(),
                "rotation", getRotation(),
                "color", getColor());
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

}
