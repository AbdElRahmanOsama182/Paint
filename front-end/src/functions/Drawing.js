import { CreateCircle, CreateEllipse, CreateLine, CreatePolygon, CreateRectangle, CreateSquare, CreateTriangle } from "@/api/Creates";
import { UpdateCircle, UpdateEllipse, UpdateLine, UpdatePolygon, UpdateRectangle } from "@/api/Updates";
import Konva from "konva";


const DrawingFunctions = {
    createFromJson(shape) {
        console.log("creating:",shape)
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
            case 'triangle':
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
                    strokeWidth: 5,
                });
                break;
            default:
                return;
        }
        newShape.index = shape.id;
        newShape.scaleX(shape.scaleX);
        newShape.scaleY(shape.scaleY);
        newShape.rotation(shape.rotation);
        console.log("new",newShape)
        this.layer.add(newShape);
        this.toBackend(newShape, shape.type);
    },
    async toBackend(shape, type) {
        switch (type) {
            case 'circle':
                await CreateCircle(shape);
                break;
            case 'ellipse':
                await CreateEllipse(shape);
                break;
            case 'square':
                await CreateSquare(shape);
                break;
            case 'rectangle':
                await CreateRectangle(shape);
                break;
            case 'polygon':
                await CreatePolygon(shape);
                break;
            case 'line':
                await CreateLine(shape);
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
            case 'Brush':
                return new Konva.Line({
                    points: [pos.x, pos.y, pos.x, pos.y],
                    stroke: this.currentColor,
                    strokeWidth: 5,
                });
            default:
                return new Konva.RegularPolygon({
                    x: pos.x,
                    y: pos.y,
                    sides: Number(this.drawingShape),
                    radius: 50,
                    fill: this.currentColor,
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

                await CreateCircle(shape, this.layer.children[this.layer.children.length - 1].index);
                break;

            case 'Ellipse':

                await CreateEllipse(shape, this.layer.children[this.layer.children.length - 1].index);
                break;

            case 'Square':


                await CreateSquare(shape, this.layer.children[this.layer.children.length - 1].index);
                break;

            case 'Rectangle':


                await CreateRectangle(shape, this.layer.children[this.layer.children.length - 1].index);
                break;

            case 'Triangle':


                await CreateTriangle(shape, this.layer.children[this.layer.children.length - 1].index);
                break;

            case 'Line':
            case 'Brush':


                await CreateLine(shape, this.layer.children[this.layer.children.length - 1].index);
                break;
            default:

                await CreatePolygon(shape, this.layer.children[this.layer.children.length - 1].index);
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
        if (this.isResizing) {
            if (this.clickedShapeIndex !== null) {
                index = this.clickedShapeIndex;
            }
            else return;
        }
        const pos = this.stage.getPointerPosition();

        const shape = this.layer.children[index];
        switch (this.drawingShape) {
            case 'Circle':
                const radius = Math.sqrt(Math.pow(pos.x - shape.x(), 2) + Math.pow(pos.y - shape.y(), 2));
                shape.radius(radius);


                await UpdateCircle(shape);
                break;
            case 'Ellipse':
                shape.radiusX(Math.abs(pos.x - shape.x()));
                shape.radiusY(Math.abs(pos.y - shape.y()));


                await UpdateEllipse(shape);
                break;
            case 'Square':
                const sideLength = Math.max(Math.abs(pos.x - shape.x()), Math.abs(pos.y - shape.y()));
                shape.width(sideLength);
                shape.height(sideLength);

                await UpdateSquare(shape);
                break;
            case 'Rectangle':
                shape.width(Math.abs(pos.x - shape.x()));
                shape.height(Math.abs(pos.y - shape.y()));


                await UpdateRectangle(shape);
                break;
            case 'Triangle':
                shape.radius(Math.max(Math.abs(pos.x - shape.x()), Math.abs(pos.y - shape.y())));


                await UpdatePolygon(shape);
                break;
            case 'Line':
                shape.points([shape.points()[0], shape.points()[1], pos.x, pos.y]);


                await UpdateLine(shape);

                break;
            case 'Brush':
                let newPoints = shape.points().concat([pos.x, pos.y]);
                shape.points(newPoints);


                await UpdateLine(shape);
                break;
            default:
                shape.radius(Math.max(Math.abs(pos.x - shape.x()), Math.abs(pos.y - shape.y())));

                await UpdatePolygon(shape);
                break;
        }
        this.layer.batchDraw();
    },
}
export { DrawingFunctions };
