package com.paint.backend;


import java.awt.geom.Point2D;

public class CenteredShape extends Shape{
    private Point2D.Float center;

    public Point2D.Float getCenter() {
        return center;
    }

    public void setCenter(Point2D.Float center) {
        this.center = center;
    }

    @Override
    public  IShape clone() {
        CenteredShape clone = new CenteredShape();
        clone.setCenter(getCenter());
        return clone;
    }
}
