package com.paint.backend;

import java.util.Map;

public class Ellipse extends CenteredShape{
    private float mainRadius;
    private float secondaryRadius;
    public Ellipse(Object... attributes) {
        setType("ellipse");
        update(attributes);
        ShapeFactory.getInstance().addShape(this);
    }
    public float getMainRadius() {
        return mainRadius;
    }

    public void setMainRadius(float mainRadius) {
        this.mainRadius = mainRadius;
    }

    public void setSecondaryRadius(float secondaryRadius) {
        this.secondaryRadius = secondaryRadius;
    }
    public float getSecondaryRadius() {
        return secondaryRadius;
    }

    @Override
    public IShape clone() {
        Ellipse clone = new Ellipse();
        clone.setCenter(getCenter());
        clone.setMainRadius(getMainRadius());
        clone.setSecondaryRadius(getSecondaryRadius());
        return clone;
    }

    @Override
    public void update(Object... attributes) {
        if(attributes.length % 2 != 0){
            throw new IllegalArgumentException("Attributes must be in pairs");
        }
        for(int i = 0; i < attributes.length; i += 2){
            String key = (String) attributes[i];
            Object value = attributes[i + 1];
            switch(key){
                case "center":
                    setCenter((java.awt.geom.Point2D.Float) value);
                    break;
                case "mainRadius":
                    setMainRadius((float) value);
                    break;
                case "secondaryRadius":
                    setSecondaryRadius((float) value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid attribute: " + key);
            }
        }
    }

    @Override
    public Map<String, Object> read() {
        return Map.of(
            "id", getId(),
            "type", getType(),
            "center", getCenter(),
            "mainRadius", getMainRadius(),
            "secondaryRadius", getSecondaryRadius()
        );
    }
}
