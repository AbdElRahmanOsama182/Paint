package com.paint.backend;

import java.awt.Point;
import java.util.Map;

public class Ellipse extends EllipticalShape {
    private int mainRadius;
    private int secondaryRadius;

    public Ellipse() {

    }

    public Ellipse(Map<String,Object> attributes) {

        update(attributes);

    }

    public int getMainRadius() {
        return mainRadius;
    }

    public void setMainRadius(int mainRadius) {
        this.mainRadius = mainRadius;
    }

    public void setSecondaryRadius(int secondaryRadius) {
        this.secondaryRadius = secondaryRadius;
    }

    public int getSecondaryRadius() {
        return secondaryRadius;
    }

    @Override
    public Shape clone() {
        Ellipse clone = new Ellipse(Map.of("center", getCenter(), "mainRadius", getMainRadius(), "secondaryRadius", getSecondaryRadius()));

        return clone;
    }

    @Override
    public void update(Map<String,Object> attributes) {
        update((Point) attributes.get("center") , (int) attributes.get("mainRadius"), (int) attributes.get("secondaryRadius"));

    }

    public void update(Point center, int mainRadius, int secondaryRadius) {
        setCenter(center);
        setMainRadius(mainRadius);
        setSecondaryRadius(secondaryRadius);
    }

    @Override
    public Map<String, Object> read() {
        return Map.of(
                "id", getId(),
                "center", getCenter(),
                "mainRadius", getMainRadius(),
                "secondaryRadius", getSecondaryRadius());
    }
}
