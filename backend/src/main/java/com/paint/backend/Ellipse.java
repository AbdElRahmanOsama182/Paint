package com.paint.backend;

import java.awt.Point;
import java.util.Map;

public class Ellipse extends EllipticalShape {
    private int mainRadius;
    private int secondaryRadius;

    public Ellipse(Object... attributes) {
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
        Ellipse clone = new Ellipse(getCenter(), getMainRadius(), getSecondaryRadius());
        return clone;
    }

    @Override
    public void update(Object... attributes) {
        update((Point) attributes[0], (int) attributes[1], (int) attributes[2]);
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
