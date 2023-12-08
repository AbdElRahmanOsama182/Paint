package com.paint.backend;

import java.util.Map;

public class Rectangle implements Shape {

    int id;
    String type;
    float height;
    float width;
    float x;
    float y;
    float scaleX;
    float scaleY;
    float rotation;
    String color;

    public Rectangle() {
    }

    public Rectangle(Map<String, Object> attributes) {
        setType("rectangle");
        update(attributes);

    }

    @Override
    public void update(Map<String, Object> attributes) {
        update((int) attributes.get("id"), (float) attributes.get("height"), (float) attributes.get("width"),

                (float) attributes.get("x"), (float) attributes.get("y"),
                (float) attributes.get("scaleX"), (float) attributes.get("scaleY"),
                (float) attributes.get("rotation"), (String) attributes.get("color"));

    }

    public void update(int id, float height, float width, float x, float y,

            float scaleX, float scaleY, float rotation, String color) {
        setHeight(height);
        setWidth(width);
        setX(x);
        setY(y);
        setScaleX(scaleX);
        setScaleY(scaleY);
        setRotation(rotation);
        setColor(color);
        setId(id);

    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Shape clone(int newId) {
        Rectangle clone = new Rectangle(Map.of("type", getType(),
                "height", getHeight(), "width", getWidth(), "x", getX(), "y", getY(),
                "scaleX", getScaleX(), "scaleY", getScaleY(), "rotation", getRotation(), "color", getColor(), "id",
                newId));

        return clone;
    }

    @Override
    public Map<String, Object> read() {
        return Map.of(
                "id", getId(),
                "type", "rectangle",
                "height", getHeight(),
                "width", getWidth(),
                "x", getX(),
                "y", getY(),
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
