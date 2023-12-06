package com.paint.backend;

import java.util.Map;

public interface Shape extends Cloneable {
    int getId();

    void setId(int id);

    String getType();

    void setType(String type);

    void update(Map<String, Object> attributes);

    Map<String, Object> read();

    Shape clone(int newId);
}
