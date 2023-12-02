package com.paint.backend;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShapesController {
    @GetMapping("/shape/{id}")
	public Map<String,Object> index(@PathVariable("id") String id) {
        return ShapeFactory.readShape(Integer.parseInt(id));
	}
}
