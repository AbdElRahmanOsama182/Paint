const DrawingFunctions = {
    createShape(pos){
        switch (this.drawingShape) {
            case 'Circle':
                return new Konva.Circle({
                    x: pos.x,
                    y: pos.y,
                    radius: 50,
                    fill: 'black',
                });
            case 'Ellipse':
                return new Konva.Ellipse({
                    x: pos.x,
                    y: pos.y,
                    radiusX: 50,
                    radiusY: 20,
                    fill: 'black',
                });
            case 'Square':
                console.log("square");
                return new Konva.Rect({
                    x: pos.x,
                    y: pos.y,
                    width: 50,
                    height: 50,
                    fill: 'black',
                });
            case 'Rectangle':
                return new Konva.Rect({
                    x: pos.x,
                    y: pos.y,
                    width: 80,
                    height: 50,
                    fill: 'black',
                });
            case 'Triangle':
                return new Konva.RegularPolygon({
                    x: pos.x,
                    y: pos.y,
                    sides: 3,
                    radius: 50,
                    fill: 'black',
                });
            case 'Line':
                return new Konva.Line({
                    points: [pos.x, pos.y, pos.x, pos.y],
                    stroke: 'black',
                    strokeWidth: 2,
                });
        }
    },
    startDrawing(event){
        this.transformer.nodes([]);
        this.isDrawing = true;
        const shape = this.createShape(this.stage.getPointerPosition());
        this.layer.add(shape);
        this.layer.draw();
        this.stage.on('mousemove', this.drawing);
    },
    stopDrawing(event){
        this.stage.off('mousemove', this.drawing);
        this.isDrawing = false;
        this.saveRecord();

    },
    drawing(event) {
        if (!this.isDrawing) return;

        const pos = this.stage.getPointerPosition();
        const shape = this.layer.children[this.layer.children.length - 1];

        switch (this.drawingShape) {
            case 'Circle':
                const radius = Math.max(Math.abs(pos.x - shape.x()), Math.abs(pos.y - shape.y()));
                shape.radius(radius);
                break;
            case 'Ellipse':
                shape.radiusX(Math.abs(pos.x - shape.x()));
                shape.radiusY(Math.abs(pos.y - shape.y()));
                break;
            case 'Square':
                const sideLength = Math.max(Math.abs(pos.x - shape.x()), Math.abs(pos.y - shape.y()));
                shape.width(sideLength);
                shape.height(sideLength);
                break;
            case 'Rectangle':
                shape.width(Math.abs(pos.x - shape.x()));
                shape.height(Math.abs(pos.y - shape.y()));
                break;
            case 'Triangle':
                shape.radius(Math.max(Math.abs(pos.x - shape.x()), Math.abs(pos.y - shape.y())));
                break;
            case 'Line':
                shape.points([shape.points()[0], shape.points()[1], pos.x, pos.y]);
                break;
        }
        this.layer.batchDraw();
    },
}
export { DrawingFunctions};