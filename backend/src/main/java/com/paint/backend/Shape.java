package com.paint.backend;

import java.util.Map;

public interface Shape extends Cloneable {
    int getId();

    void setId(int id);

    void update(Map<String,Object> attributes);


    Map<String, Object> read();

    Shape clone(int newId);
}
