package com.paint.backend;

import java.util.Map;

public class ImageShape implements Shape {

    int id;
    String type;
    float height;
    float width;
    float x;
    float y;
    float scaleX;
    float scaleY;
    float rotation;
    String src;

    public ImageShape() {
    }

    public ImageShape(Map<String, Object> attributes) {
        setType("image");
        update(attributes);

    }

    @Override
    public void update(Map<String, Object> attributes) {
        update((int) attributes.get("id"), (float) attributes.get("height"), (float) attributes.get("width"),
                (float) attributes.get("x"), (float) attributes.get("y"),
                (float) attributes.get("scaleX"), (float) attributes.get("scaleY"),
                (float) attributes.get("rotation"), (String) attributes.get("src"));

    }

    public void update(int id, float height, float width, float x, float y,

            float scaleX, float scaleY, float rotation, String src) {
        setHeight(height);
        setWidth(width);
        setX(x);
        setY(y);
        setScaleX(scaleX);
        setScaleY(scaleY);
        setRotation(rotation);
        setSrc(src);
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

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
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
                "scaleX", getScaleX(), "scaleY", getScaleY(), "rotation", getRotation(), "src", getSrc(), "id",
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
                "src", getSrc());
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
