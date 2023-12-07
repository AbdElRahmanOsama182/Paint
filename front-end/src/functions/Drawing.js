

const DrawingFunctions = {
    createFromJson(shape) {
        let newShape;
        switch (shape.type) {
            case 'circle':
                const center = shape.center;
                console.log(center);
                console.log(center.x);
                newShape = new Konva.Circle({
                    x: center.x,
                    y: center.y,
                    radius: shape.radius,
                    fill: shape.color,
                });
                break;
            case 'ellipse':
                newShape = new Konva.Ellipse({
                    x: shape.center.x,
                    y: shape.center.y,
                    radiusX: shape.radiusX,
                    radiusY: shape.radiusY,
                    fill: shape.color,
                });
                break;
            case 'rectangle':
            case 'square':
                newShape = new Konva.Rect({
                    x: shape.x,
                    y: shape.y,
                    width: shape.width,
                    height: shape.height,
                    fill: shape.color,
                });
                break;
            case 'polygon':
                newShape = new Konva.RegularPolygon({
                    x: shape.center.x,
                    y: shape.center.y,
                    sides: shape.sides,
                    radius: shape.radius,
                    fill: shape.color,
                });
                break;
            case 'line':
                newShape = new Konva.Line({
                    points: shape.points,
                    stroke: shape.color,
                    strokeWidth: 1.5,
                });
                break;
            default:
                return;
        }
        newShape.index = shape.id;
        newShape.scaleX(shape.scaleX);
        newShape.scaleY(shape.scaleY);
        newShape.rotation(shape.rotation);
        this.layer.add(newShape);
        this.toBackend(newShape, shape.type);
    },
    async toBackend(shape, type) {
        switch (type) {
            case 'circle':
                await fetch('http://localhost:8080/circle', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id: shape.index,
                        center: {
                            x: shape.x(),
                            y: shape.y(),
                        },
                        radius: shape.radius(),
                        color: shape.fill(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                    .then(data => {
                    });
                break;
            case 'ellipse':
                await fetch('http://localhost:8080/ellipse', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id: shape.index,
                        center: {
                            x: shape.x(),
                            y: shape.y(),
                        },
                        radiusX: shape.radiusX(),
                        radiusY: shape.radiusY(),
                        color: shape.fill(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                    .then(data => {
                    });
                break;
            case 'square':
                await fetch('http://localhost:8080/square', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id: shape.index,
                        x: shape.x(),
                        y: shape.y(),
                        width: shape.width(),
                        height: shape.height(),
                        color: shape.fill(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                    .then(data => {
                    });
                break;
            case 'rectangle':
                await fetch('http://localhost:8080/rectangle', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id: shape.index,
                        x: shape.x(),
                        y: shape.y(),
                        width: shape.width(),
                        height: shape.height(),
                        color: shape.fill(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                    .then(data => {
                    });
                break;
            case 'polygon':
                await fetch('http://localhost:8080/triangle', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id: shape.index,
                        center: {
                            x: shape.x(),
                            y: shape.y(),
                        },
                        radius: shape.radius(),
                        color: shape.fill(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                        sides: shape.sides(),
                    }),
                }).then(response => response.json())
                    .then(data => {
                    });
                break;
            case 'line':
                await fetch('http://localhost:8080/line', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id: shape.index,
                        points: shape.points(),
                        color: shape.stroke(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                    .then(data => {
                    });
                break;
        }
    },

    createShape(pos) {
        switch (this.drawingShape) {
            case 'Circle':
                return new Konva.Circle({
                    x: pos.x,
                    y: pos.y,
                    radius: 50,
                    fill: this.currentColor,
                });
            case 'Ellipse':
                return new Konva.Ellipse({
                    x: pos.x,
                    y: pos.y,
                    radiusX: 50,
                    radiusY: 20,
                    fill: this.currentColor,
                });
            case 'Square':
                return new Konva.Rect({
                    x: pos.x,
                    y: pos.y,
                    width: 50,
                    height: 50,
                    fill: this.currentColor,
                });
            case 'Rectangle':
                return new Konva.Rect({
                    x: pos.x,
                    y: pos.y,
                    width: 80,
                    height: 50,
                    fill: this.currentColor,
                });
            case 'Triangle':
                return new Konva.RegularPolygon({
                    x: pos.x,
                    y: pos.y,
                    sides: 3,
                    radius: 50,
                    fill: this.currentColor,
                });
            case 'Line':
                return new Konva.Line({
                    points: [pos.x, pos.y, pos.x + 10, pos.y + 10],
                    stroke: this.currentColor,
                    strokeWidth: 5,
                });
        }
    },
    async startDrawing(event) {
        this.transformer.nodes([]);
        this.isDrawing = true;
        const shape = this.createShape(this.stage.getPointerPosition());
        this.layer.add(shape);
        this.layer.draw();
        switch (this.drawingShape) {
            case 'Circle':
                await fetch('http://localhost:8080/circle', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id: this.layer.children[this.layer.children.length - 1].index,
                        center: {
                            x: shape.x(),
                            y: shape.y(),
                        },
                        radius: shape.radius(),
                        color: shape.fill(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                    .then(data => {
                    });
                break;

            case 'Ellipse':
                await fetch('http://localhost:8080/ellipse', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id: this.layer.children[this.layer.children.length - 1].index,
                        center: {
                            x: shape.x(),
                            y: shape.y(),
                        },
                        radiusX: shape.radiusX(),
                        radiusY: shape.radiusY(),
                        color: shape.fill(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                    .then(data => {
                    });
                break;

            case 'Square':
                await fetch('http://localhost:8080/square', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id: this.layer.children[this.layer.children.length - 1].index,
                        x: shape.x(),
                        y: shape.y(),
                        width: shape.width(),
                        height: shape.height(),
                        color: shape.fill(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                    .then(data => {
                    });
                break;

            case 'Rectangle':
                await fetch('http://localhost:8080/rectangle', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id: this.layer.children[this.layer.children.length - 1].index,
                        x: shape.x(),
                        y: shape.y(),
                        width: shape.width(),
                        height: shape.height(),
                        color: shape.fill(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                    .then(data => {
                    });
                break;

            case 'Triangle':
                await fetch('http://localhost:8080/triangle', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id: this.layer.children[this.layer.children.length - 1].index,
                        center: {
                            x: shape.x(),
                            y: shape.y(),
                        },
                        radius: shape.radius(),
                        color: shape.fill(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                    .then(data => {
                    });
                break;

            case 'Line':
                await fetch('http://localhost:8080/line', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id: this.layer.children[this.layer.children.length - 1].index,
                        points: shape.points(),
                        color: shape.stroke(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                    .then(data => {
                    });
                break;
        }
        this.stage.on('mousemove', this.drawing);
    },
    stopDrawing(event) {
        this.stage.off('mousemove', this.drawing);
        this.isDrawing = false;
        this.setResize(false);
        this.saveRecord();

    },
    async drawing(event) {
        if (!this.isDrawing && !this.isResizing) return;

        var index = this.layer.children.length - 1;
        if(this.isResizing){
            index = this.clickedShapeIndex;
        }
        const pos = this.stage.getPointerPosition();
        
        const shape = this.layer.children[index];
        switch (this.drawingShape) {
            case 'Circle':
                const radius = Math.sqrt(Math.pow(pos.x - shape.x(), 2) + Math.pow(pos.y - shape.y(), 2));
                shape.radius(radius);
                await fetch(`http://localhost:8080/circle/${shape.index}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id: shape.index,
                        center: {
                            x: shape.x(),
                            y: shape.y(),
                        },
                        radius: shape.radius(),
                        color: shape.fill(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                    .then(data => {
                    });
                break;
            case 'Ellipse':
                shape.radiusX(Math.abs(pos.x - shape.x()));
                shape.radiusY(Math.abs(pos.y - shape.y()));
                await fetch(`http://localhost:8080/ellipse/${shape.index}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id: shape.index,
                        center: {
                            x: shape.x(),
                            y: shape.y(),
                        },
                        radiusX: shape.radiusX(),
                        radiusY: shape.radiusY(),
                        color: shape.fill(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                    .then(data => {
                    });
                break;
            case 'Square':
                const sideLength = Math.max(Math.abs(pos.x - shape.x()), Math.abs(pos.y - shape.y()));
                shape.width(sideLength);
                shape.height(sideLength);
                await fetch(`http://localhost:8080/square/${shape.index}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id: shape.index,
                        x: shape.x(),
                        y: shape.y(),
                        width: shape.width(),
                        height: shape.height(),
                        color: shape.fill(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                    .then(data => {
                    });
                break;
            case 'Rectangle':
                shape.width(Math.abs(pos.x - shape.x()));
                shape.height(Math.abs(pos.y - shape.y()));
                await fetch(`http://localhost:8080/rectangle/${shape.index}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id: shape.index,

                        x: shape.x(),
                        y: shape.y(),

                        width: shape.width(),
                        height: shape.height(),
                        color: shape.fill(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                    .then(data => {
                    });
                break;
            case 'Triangle':
                shape.radius(Math.max(Math.abs(pos.x - shape.x()), Math.abs(pos.y - shape.y())));
                await fetch(`http://localhost:8080/triangle/${shape.index}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id: shape.index,
                        center: {
                            x: shape.x(),
                            y: shape.y(),
                        },
                        radius: shape.radius(),
                        color: shape.fill(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                        sides: shape.sides(),
                    }),
                }).then(response => response.json())
                    .then(data => {
                    });
                break;
            case 'Line':
                shape.points([shape.points()[0], shape.points()[1], pos.x, pos.y]);
                await fetch(`http://localhost:8080/line/${shape.index}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id: shape.index,
                        points: shape.points(),
                        color: shape.stroke(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                    .then(data => {
                    });
                break;
        }
        this.layer.batchDraw();
    },
}
export { DrawingFunctions };
