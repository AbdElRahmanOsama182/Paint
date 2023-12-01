package com.paint.backend;
import java.util.Map;
public class Rectangle extends CenteredShape {
    private float width;
    private float height;
    public Rectangle(Object... attributes) {
        setType("rectangle");
        update(attributes);
        ShapeFactory.getInstance().addShape(this);
    }
    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public IShape clone() {
        Rectangle clone = new Rectangle();
        clone.setCenter(getCenter());
        clone.setWidth(getWidth());
        clone.setHeight(getHeight());
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
                case "width":
                    setWidth((float) value);
                    break;
                case "height":
                    setHeight((float) value);
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
            "width", getWidth(),
            "height", getHeight()
        );
    }
}
