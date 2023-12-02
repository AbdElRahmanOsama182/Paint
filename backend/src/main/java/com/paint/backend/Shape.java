package com.paint.backend;

import java.util.Map;

public interface Shape extends Cloneable {
    int getId();

    void setId(int id);

    void update(Object... attributes);

    Map<String, Object> read();

    Shape clone();
}
