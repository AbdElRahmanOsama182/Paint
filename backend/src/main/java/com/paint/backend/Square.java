package com.paint.backend;

import java.util.Map;

public class Square extends CenteredShape{
    private float side;
    public Square(Object... attributes) {
        setType("square");
        update(attributes);
        ShapeManager.getInstance().addShape(this);
    }
    public float getSide() {
        return side;
    }

    public void setSide(float side) {
        this.side = side;
    }

    @Override
    public IShape clone() {
        super.clone();
        Square clone = new Square();
        clone.setCenter(getCenter());
        clone.setSide(getSide());
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
                    setCenter((java.awt.geom.Point2D.Float) value);
                    break;
                case "side":
                    setSide((float) value);
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
            "side", getSide()
        );
    }
}
