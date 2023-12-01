package com.paint.backend;

import java.awt.geom.Point2D;
import java.util.Map;

public class Circle extends CenteredShape{
    private float radius;

    public Circle(Object... attributes) {
        setType("circle");
        update(attributes);
        ShapeFactory.getInstance().addShape(this);
    }
    public float getRadius() {
        return radius;
    }
    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public IShape clone() {
        super.clone();
        Circle clone = new Circle();
        clone.setCenter(getCenter());
        clone.setRadius(getRadius());
        return clone;
    }

    @Override
    public void update(Object... attributes) {
        super.update(attributes);
        if(attributes.length % 2 != 0){
            throw new IllegalArgumentException("Attributes must be in pairs");
        }
        for(int i = 0; i < attributes.length; i += 2){
            String key = (String) attributes[i];
            Object value = attributes[i + 1];
            switch(key){
                case "center":
                    setCenter((Point2D.Float) value);
                    break;
                case "radius":
                    setRadius((float) value);
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
            "radius", getRadius()
        );
    }
}
