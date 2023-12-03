package com.paint.backend;

import java.util.Map;
import java.awt.Point;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShapesController {

    
    @PostMapping("/circle")
    public Circle createCircle(@RequestBody Circle data) {
        return (Circle) ShapeFactory.getInstance().create("circle",data.read());
    }
    @PostMapping("/rectangle")
    public Rectangle createRectangle(@RequestBody Rectangle data) {
        return (Rectangle) ShapeFactory.getInstance().create("rectangle",data.read());
    }
    
    @PostMapping("/triangle")
    public Triangle createTriangle(@RequestBody Triangle data) {
        return (Triangle) ShapeFactory.getInstance().create("triangle",data.read());
    }
    @PostMapping("/square")
    public Square createSquare(@RequestBody Square data) {
        return (Square) ShapeFactory.getInstance().create("square",data.read());
    }
    @PostMapping("/line")
    public Line createLine(@RequestBody Line data) {
        return (Line) ShapeFactory.getInstance().create("line",data.read());
    }
    @PostMapping("/ellipse")
    public Ellipse createEllipse(@RequestBody Ellipse data) {
        return (Ellipse) ShapeFactory.getInstance().create("ellipse",data.read());
    }

    

    @GetMapping("/shape/{id}")
	public Map<String,Object> read(@PathVariable("id") String id) {
        // test
        System.out.println("Shapeeeeee");
        Shape c = ShapeFactory.getInstance().create("circle", Map.of("center", new Point(0, 0), "radius", 5));
        System.out.println("Shapeeeeee");
        System.out.println(c.getId());
        return ShapeFactory.getInstance().read(Integer.parseInt(id));
	}

    // @DeleteMapping("/shape/{id}")
	// public void delete(@PathVariable("id") String id) {
    //     // test
    //     Shape c = ShapeFactory.getInstance().create("circle", Map.of("center", new Point(0, 0), "radius", 5));

    //     ShapeFactory.getInstance().delete(Integer.parseInt(id));
    //     // ShapeFactory.getInstance().delete(Integer.parseInt(id));
	// }
}
