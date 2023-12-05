package com.paint.backend;

import java.util.Map;
import java.awt.Point;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShapesController {

    // Post mapping

    @PostMapping("/circle")
    public Circle createCircle(@RequestBody Circle data) {
        return (Circle) ShapeManager.getInstance().create("circle", data.read());
    }

    @PostMapping("/rectangle")
    public Rectangle createRectangle(@RequestBody Rectangle data) {
        return (Rectangle) ShapeManager.getInstance().create("rectangle", data.read());
    }

    @PostMapping("/triangle")
    public Triangle createTriangle(@RequestBody Triangle data) {
        return (Triangle) ShapeManager.getInstance().create("triangle", data.read());
    }

    @PostMapping("/square")
    public Square createSquare(@RequestBody Square data) {
        return (Square) ShapeManager.getInstance().create("square", data.read());
    }

    @PostMapping("/line")
    public Line createLine(@RequestBody Line data) {
        return (Line) ShapeManager.getInstance().create("line", data.read());
    }

    @PostMapping("/ellipse")
    public Ellipse createEllipse(@RequestBody Ellipse data) {
        return (Ellipse) ShapeManager.getInstance().create("ellipse", data.read());
    }

    // Put mapping

    @PutMapping("/circle/{id}")
    public Circle updateCircle(@PathVariable("id") String id, @RequestBody Circle data) {
        return (Circle) ShapeManager.getInstance().update(Integer.parseInt(id), data.read());
    }

    @PutMapping("/rectangle/{id}")
    public Rectangle updateRectangle(@PathVariable("id") String id, @RequestBody Rectangle data) {
        return (Rectangle) ShapeManager.getInstance().update(Integer.parseInt(id), data.read());
    }

    @PutMapping("/triangle/{id}")
    public Triangle updateTriangle(@PathVariable("id") String id, @RequestBody Triangle data) {
        return (Triangle) ShapeManager.getInstance().update(Integer.parseInt(id), data.read());
    }

    @PutMapping("/square/{id}")
    public Square updateSquare(@PathVariable("id") String id, @RequestBody Square data) {
        return (Square) ShapeManager.getInstance().update(Integer.parseInt(id), data.read());
    }

    @PutMapping("/line/{id}")
    public Line updateLine(@PathVariable("id") String id, @RequestBody Line data) {
        return (Line) ShapeManager.getInstance().update(Integer.parseInt(id), data.read());
    }

    @PutMapping("/ellipse/{id}")
    public Ellipse updateEllipse(@PathVariable("id") String id, @RequestBody Ellipse data) {
        return (Ellipse) ShapeManager.getInstance().update(Integer.parseInt(id), data.read());
    }

    @GetMapping("/shape/{id}")
    public Map<String, Object> read(@PathVariable("id") String id) {
        return ShapeManager.getInstance().read(Integer.parseInt(id));
    }

    @DeleteMapping("/shape/{id}")
    public void delete(@PathVariable("id") String id) {
        ShapeManager.getInstance().delete(Integer.parseInt(id));
    }

    @PostMapping("/shape/{id}/copy")
    public Map<String, Object> copy(@PathVariable("id") String id, @RequestBody Map<String,Integer> payload) {
        return ShapeManager.getInstance().clone(Integer.parseInt(id),(Integer)payload.get("newId")).read();
    }

    @PostMapping("/layer/record")
    public void saveRecord() {
        ShapeManager.getInstance().saveRecord();
    }

    @PostMapping("/layer/undo")
    public void undo() {
        ShapeManager.getInstance().undo();
    }

    @PostMapping("/layer/redo")
    public void redo() {
        ShapeManager.getInstance().redo();
        System.out.println(ShapeManager.getInstance().getAllShapes());
    }

    
}
