<template>
    <div>
        <div class="panel"
        v-bind:style="{ left: `${x}px`, top: `${y}px` }"
        @mousedown="startDrag"
        @mousemove="dragging"
        @mouseup="stopDrag"
        @mouseleave="stopDrag">
            <div class="commands">
                <button> load </button>
                <button> undo </button>
                <button> save</button>
                <button> redo </button>
            </div>
            <div class="shapes">
                <button @click="drawShape('Circle')"> ◯ </button>
                <button @click="drawShape('Ellipse')"> ⬯ </button>
                <button @click="drawShape('Rectangle')"> ▭ </button>
                <button @click="drawShape('Square')"> ◻</button>
                <button @click="drawShape('Triangle')"> △ </button>
                <button @click="drawShape('Line')"> / </button>
            </div>
            <div class="colors">
                <button> </button>
                <button> </button>
                <button> </button>
                <button> </button>
                <button> </button>
                <button> </button>
                <button> </button>
                <button> </button>
            </div>
            <div class="popup">
                    <button class="customcolor" @dblclick="showPicker" :style="{backgroundColor: currentColor}">
                        <p v-if="isPopupVisible">▿</p>
                        <p v-else>▵</p>
                    </button>
                    <div id="PopUp" class="popup-content">
                        <v-color-picker 
                            @update:modelValue="changeColor"
                            @mouseleave="closePopup" 
                            color="#e0dfdf"
                            >
                        </v-color-picker>
                    </div>
                    </div>         </div>

    <div class="wrapper">
        <div @dblclick="selectShape" @mousedown="Action" @mouseup="stopDrawing" class="canvas" :style="{ width: `${CW}px`, height: `${CH}px`}">
            
        </div>
    </div>

    </div>
</template>

<script>
import Konva from 'konva';
import { Stage, Layer, Rect, Circle, Ellipse, Line } from 'konva';
export default {
    data(){
        return{
            CW: 1200,
            CH: 600,
            isDragging: false,
            x: 450,
            y: 550,
            startX: 0,
            startY: 0,
            drawingShape: null,
            isDrawing: false,
            stage: null,
            layer: null,
            slectedShapeIndex: null,
            transformer: null,
            selectionRectangle: null,
            isPopupVisible: false,
            currentColor: 0
        };
    },
    mounted(){
        this.stage = new Konva.Stage({
            container: '.canvas',
            width: this.CW,
            height: this.CH,
        });
        this.layer = new Konva.Layer();
        this.stage.add(this.layer);
        this.transformer = new Konva.Transformer();
        this.layer.add(this.transformer);
        this.selectionRectangle = new Konva.Rect({
            fill: 'rgba(0,0,255,0.5)',
            visible: false,
        });
        this.layer.add(this.selectionRectangle);
        console.log('mounted');
    },
    methods: {
        Action(){
            if(this.isDrawing) this.startDrawing();
            else this.selectWindow();
        },
        selectWindow(){
            this.stage.off('mousemove');
            this.stage.off('mouseup');
            const pos = this.stage.getPointerPosition();
            const shape = this.stage.getIntersection(pos);
            if (shape) return;
            this.transformer.nodes([]);
            var x1, y1, x2, y2;
            x1 = this.stage.getPointerPosition().x;
            y1 = this.stage.getPointerPosition().y;
            x2 = this.stage.getPointerPosition().x;
            y2 = this.stage.getPointerPosition().y;
            this.selectionRectangle.visible(true);
            console.log(this.selectionRectangle.visible());
            this.selectionRectangle.width(0);
            this.selectionRectangle.height(0);
            this.stage.on('mousemove', (e) => {
                if (!this.selectionRectangle.visible()) return;
                e.evt.preventDefault();
                x2 = this.stage.getPointerPosition().x;
                y2 = this.stage.getPointerPosition().y;
                this.selectionRectangle.setAttrs({
                    x: Math.min(x1, x2),
                    y: Math.min(y1, y2),
                    width: Math.abs(x2 - x1),
                    height: Math.abs(y2 - y1),
                });
            });
            this.stage.on('mouseup', (e) => {
                if (!this.selectionRectangle.visible()) return;
                e.evt.preventDefault();
                setTimeout(() => {
                    this.selectionRectangle.visible(false);
                });
                var shapes = this.stage.find('Shape');
                var box = this.selectionRectangle.getClientRect();
                
                var selected = shapes.filter((shape) =>
                    shape !== this.selectionRectangle &&
                  Konva.Util.haveIntersection(box, shape.getClientRect())
                );
                console.log(selected);
                if (selected.length > 0) this.transformer.nodes(selected);
            });
        },
        drawShape(shape){
            this.drawingShape = shape;
            this.isDrawing = true;
            console.log(this.drawingShape);
        },
        createShape(pos){
            switch (this.drawingShape) {
                case 'Circle':
                    return new Konva.Circle({
                        x: pos.x,
                        y: pos.y,
                        radius: 50,
                        fill: 'transparent',
                        stroke: 'black',
                        strokeWidth: 2,
                    });
                case 'Ellipse':
                    return new Konva.Ellipse({
                        x: pos.x,
                        y: pos.y,
                        radiusX: 50,
                        radiusY: 20,
                        fill: 'transparent',
                        stroke: 'black',
                        strokeWidth: 2,
                    });
                case 'Square':
                    console.log("square");
                    return new Konva.Rect({
                        x: pos.x,
                        y: pos.y,
                        width: 50,
                        height: 50,
                        fill: 'transparent',
                        stroke: 'black',
                        strokeWidth: 2,
                    });
                case 'Rectangle':
                    return new Konva.Rect({
                        x: pos.x,
                        y: pos.y,
                        width: 80,
                        height: 50,
                        fill: 'transparent',
                        stroke: 'black',
                        strokeWidth: 2,
                    });
                case 'Triangle':
                    return new Konva.RegularPolygon({
                        x: pos.x,
                        y: pos.y,
                        sides: 3,
                        radius: 50,
                        fill: 'transparent',
                        stroke: 'black',
                        strokeWidth: 2,
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
            console.log("start drawing");
            this.transformer.nodes([]);
            this.isDrawing = true;
            const shape = this.createShape(this.stage.getPointerPosition());
            this.layer.add(shape);
            this.layer.draw();
            this.stage.on('mousemove', this.drawing);
        },
        stopDrawing(event){
            console.log("stop drawing");
            this.stage.off('mousemove', this.drawing);
            this.isDrawing = false;
        },
        drawing(event) {
            console.log("drawing");
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
        selectShape(event) {
            const pos = this.stage.getPointerPosition();
            const shape = this.stage.getIntersection(pos);
            if (shape) {
                this.slectedShapeIndex = shape.index;
                console.log(this.slectedShapeIndex);
                this.transformer.nodes([this.layer.children[this.slectedShapeIndex]]);
                console.log(`Shape selected: ${this.layer.children[this.slectedShapeIndex].name()}`);
            }
            else {
                this.transformer.nodes([]);
                console.log('No shape selected');
            }
        },
        startDrag(event) {
            this.isDragging = true;
            this.startX = event.clientX - this.x;
            this.startY = event.clientY - this.y;
        },
        dragging(event) {
            if (this.isDragging && !this.isPopupVisible) {
                this.x = event.clientX - this.startX;
                this.y = event.clientY - this.startY;
        }
        },
        stopDrag() {
            this.isDragging = false;
        },
        showPicker(){
            document.getElementById("PopUp").classList.toggle("showColorPicker");
            this.isPopupVisible = !this.isPopupVisible;
        },
        changeColor(event){
            this.currentColor = event;
        },
        closePopup(){
            if(this.isPopupVisible){
                this.showPicker();
            }
        }
    },
}
</script>

<style scoped>
    .wrapper{
        position: absolute;
        height: 100vh;
        width: 100vw;
        background: lightblue;
        display: flex;
        justify-content: center;
        align-items: center;
        top: 0px;left: 0px;bottom: 0px;right: 0px;
    }
    .canvas{
        background-color: white;
        border-radius: 10px;
        border: 0px solid;
        box-shadow: 3px 3px 10px 0px rgba(0, 0, 0, 0.1);
    }
    .panel{
        position: absolute;
        display: flex;
        z-index: 5;
        padding: 15px;
        padding-right: 50px;
        background: #80808041;
        gap: 20px;
        border-radius: 5px;
        cursor: move;
        backdrop-filter: blur(5px);
    }

    button{
        cursor: pointer;
        border: 0px;
        background: white;
        border-radius: 5px;
        transition: all 100ms ease-out;
    }
    button:hover{
        opacity: 70%;
    }
    button:active{
        scale: 110%;
    }
    .shapes {
        display: grid;
        grid-template-rows: repeat(2, 1fr);
        gap: 5px;
        grid-auto-flow: column;
        
    }
    .colors {
        display: grid;
        grid-template-rows: repeat(2, 1fr);
        gap: 5px;
        grid-auto-flow: column;
    }


    .commands {
        display: grid;
        grid-template-rows: repeat(2, 1fr);
        gap: 5px;
        grid-auto-flow: column;
    }

    button{
        width: 50px;
        height: 30px;
    }

.colors button {
    width: 30px;
    border-radius: 50px;
    border: 0;
    box-shadow: inset 2px 2px 0px rgba(0, 0, 0, 0.2);
}
.colors button:nth-child(1) {background-color: black;}
.colors button:nth-child(2) {background-color: white;}
.colors button:nth-child(3) {background-color: cyan;}
.colors button:nth-child(4) {background-color: blue;}
.colors button:nth-child(5) {background-color: yellow;}
.colors button:nth-child(6) {background-color: green;}
.colors button:nth-child(7) {background-color: magenta;}
.colors button:nth-child(8) {background-color: red;}
.popup{
    grid-column: span 2;
    grid-row: span 2;
    width: 60px;
    height: auto;
    position: relative;
}

.customcolor{
    width: 100%;
    height: 100%;
}
.popup-content {
  display: none;
  position: absolute;
  bottom: 50px;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
  cursor: pointer;

}

.showColorPicker {
  display: block;
  margin-bottom: 20px;
}

</style>
