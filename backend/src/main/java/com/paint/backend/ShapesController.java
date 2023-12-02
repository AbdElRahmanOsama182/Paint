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

    @PostMapping("/shape")
    public Map<String,Object> create(@RequestBody Map<String,Object> payload) {

        return ShapeFactory.getInstance().create(payload.get("type"),5).read();
    }
    

    @GetMapping("/shape/{id}")
	public Map<String,Object> read(@PathVariable("id") String id) {
        return ShapeFactory.getInstance().read(Integer.parseInt(id));
	}

    @DeleteMapping("/shape/{id}")
	public void delete(@PathVariable("id") String id) {
        // test
        Shape c = ShapeFactory.getInstance().create("circle", new Point(0, 0), 5);

        ShapeFactory.getInstance().delete(Integer.parseInt(id));
        // ShapeFactory.getInstance().delete(Integer.parseInt(id));
	}
}
