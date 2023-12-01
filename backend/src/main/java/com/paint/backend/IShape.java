package com.paint.backend;

import java.util.Map;

public interface IShape extends Cloneable{
    int getId();
    void setId(int id);
    String getType();
    void setType(String type);
    IShape clone();
    void update(Object... attributes);
    Map<String, Object> read();
}
