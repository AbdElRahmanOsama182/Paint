package com.paint.backend;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Map;

public class Ellipse extends EllipticalShape {
    private float radiusX;
    private float radiusY;

    public Ellipse() {

    }

    public Ellipse(Map<String, Object> attributes) {

        update(attributes);

    }

    public float getRadiusX() {
        return radiusX;
    }

    public void setRadiusX(float radiusX) {
        this.radiusX = radiusX;
    }

    public void setRadiusY(float radiusY) {
        this.radiusY = radiusY;
    }

    public float getRadiusY() {
        return radiusY;
    }

    @Override
    public Shape clone(int newId) {
        Ellipse clone = new Ellipse(
                Map.of("center", getCenter(), "radiusX", getRadiusX(), "radiusY", getRadiusY(),
                        "rotation", getRotation(), "color", getColor(), "scaleX", getScaleX(),
                        "scaleY", getScaleY(),"id",newId));


        return clone;
    }

    @Override
    public void update(Map<String, Object> attributes) {
        update((int) attributes.get("id"),(Point2D.Float) attributes.get("center"), (float) attributes.get("radiusX"),

                (float) attributes.get("radiusY"), (float) attributes.get("rotation"),
                (String) attributes.get("color"), (float) attributes.get("scaleX"),
                (float) attributes.get("scaleY"));

    }

    public void update(int id,Point2D.Float center, float radiusX, float radiusY, float rotation, String color,
            float scaleX, float scaleY) {
        setCenter(center);
        setRadiusX(radiusX);
        setRadiusY(radiusY);
        setRotation(rotation);
        setColor(color);
        setScaleX(scaleX);
        setScaleY(scaleY);
        setId(id);


    }

    @Override
    public Map<String, Object> read() {
        return Map.of(
                "id", getId(),
                "center", getCenter(),
                "radiusX", getRadiusX(),
                "radiusY", getRadiusY(),
                "scaleX", getScaleX(),
                "scaleY", getScaleY(),
                "rotation", getRotation(),
                "color", getColor());
    }
}
