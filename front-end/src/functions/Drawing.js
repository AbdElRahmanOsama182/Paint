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
                    points: [pos.x, pos.y, pos.x+10, pos.y+10],
                    stroke: 'black',
                    strokeWidth: 1.5,
                });
        }
    },
    async startDrawing(event){
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
                        id:this.layer.children[this.layer.children.length - 1].index,
                        center:{
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
                    console.log('Success:', data);
                });
                break;

            case 'Ellipse':
                await fetch('http://localhost:8080/ellipse', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id:this.layer.children[this.layer.children.length - 1].index,
                        center:{
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
                    console.log('Success:', data);
                });
                break;
            
            case 'Square':
                await fetch('http://localhost:8080/square', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id:this.layer.children[this.layer.children.length - 1].index,
                        center:{
                            x: shape.x(),
                            y: shape.y(),
                        },
                        width: shape.width(),
                        height: shape.height(),
                        color: shape.fill(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                .then(data => {
                    console.log('Success:', data);
                });
                break;
            
            case 'Rectangle':
                await fetch('http://localhost:8080/rectangle', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id:this.layer.children[this.layer.children.length - 1].index,
                        center:{
                            x: shape.x(),
                            y: shape.y(),
                        },
                        width: shape.width(),
                        height: shape.height(),
                        color: shape.fill(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                .then(data => {
                    console.log('Success:', data);
                });
                break;
            
            case 'Triangle':
                await fetch('http://localhost:8080/triangle', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id:this.layer.children[this.layer.children.length - 1].index,
                        center:{
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
                    console.log('Success:', data);
                });
                break;
            
            case 'Line':
                await fetch('http://localhost:8080/line', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        id:this.layer.children[this.layer.children.length - 1].index,
                        points: shape.points(),
                        color: shape.stroke(),
                        scaleX: shape.scaleX(),
                        scaleY: shape.scaleY(),
                        rotation: shape.rotation(),
                    }),
                }).then(response => response.json())
                .then(data => {
                    console.log('Success:', data);
                });
                break;
        }
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
                const radius = Math.sqrt(Math.pow(pos.x - shape.x(), 2) + Math.pow(pos.y - shape.y(), 2));
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