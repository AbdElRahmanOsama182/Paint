package com.paint.backend;

import java.util.Map;

public abstract class Shape implements IShape{
    private int id;
    private String type;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public abstract IShape clone();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
                case "id":
                    setId((int) value);
                    break;
                case "type":
                    setType((String) value);
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
            "type", getType()
        );
    }
}
