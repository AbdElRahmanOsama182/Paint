<template>
    <div>
        <div class="panel" v-bind:style="{ left: `${x}px`, top: `${y}px` }" @mousedown="startDrag" @mousemove="dragging"
            @mouseup="stopDrag" @mouseleave="stopDrag">
            <div class="commands">
                <button> load </button>
                <button @click="undo()"> undo </button>
                <button> save</button>
                <button @click="redo()"> redo </button>
                <button @click="deleteShape" :style="{backgroundColor: deleteColor}"> delete </button>
                <button @click="cloneShape()" :style="{backgroundColor: cloneColor}">clone</button>
                <button @click="toggleNewPanel">New</button>
            </div>
            <div class="shapes">
                <button @click="drawShape('Circle')"> ◯ </button>
                <button @click="drawShape('Ellipse')"> ⬯ </button>
                <button @click="drawShape('Rectangle')"> ▭ </button>
                <button @click="drawShape('Square')"> ◻</button>
                <button @click="drawShape('Triangle')"> △ </button>
                <button @click="drawShape('Line')"> / </button>
            </div>
            <div class="curr">
                <button class="customcolor" @click="changeShapeColor(currentColor)" @dblclick="showPicker" :style="{ backgroundColor: currentColor }"></button>

                <div id="PopUp" class="popup-content">
                    <v-color-picker @update:modelValue="changeColor" @mouseleave="closePopup" color="#e0dfdf">
                    </v-color-picker>
                </div>
            </div>

            <div class="colors">
                <button @click="changeShapeColor('black')"> </button>
                <button @click="changeShapeColor('white')"> </button>
                <button @click="changeShapeColor('cyan')"> </button>
                <button @click="changeShapeColor('blue')"> </button>
                <button @click="changeShapeColor('yellow')"> </button>
                <button @click="changeShapeColor('green')"> </button>
                <button @click="changeShapeColor('magenta')"> </button>
                <button @click="changeShapeColor('red')"> </button>
            </div>

        </div>

        <div class="wrapper">
            <div @dblclick="selectShape" @mousedown="Action" @mouseup="stopDrawing" class="canvas"
                :style="{ width: `${CW}px`, height: `${CH}px` }">
            </div>
        </div>
        <div v-if="showResizePanel" @click.self="toggleNewPanel" class="newWrapper">
            <div class="newPanel">
                <input type="width" v-model="inputX" />
                <input type="hight" v-model="inputY"/>
                <button @click="newCanvas"> Create </button>
            </div>
        </div>
    </div>
</template>

<script>
import Konva from 'konva';
import { DrawingFunctions } from '../functions/Drawing.js';
import { HistoryFunctions } from '../functions/History.js';
export default {
    data() {
        return {
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
            showResizePanel: false,
            currentColor: 'grey',
            history: [],
            historyIndex: 0,
            clonedshape: null,
            isDeletable: false,
            isClonable: false,
            cloneColor: 'white',
            deleteColor: 'white',
            inputX: 1200,
            inputY: 600,
        };
    },
    mounted() {
        this.CW = localStorage.getItem('canvasWidth') || 1200;
        this.CH = localStorage.getItem('canvasHeight') || 600;
        localStorage.setItem('canvasWidth', 1200);
        localStorage.setItem('canvasHeight', 600);

        this.stage = new Konva.Stage({
            container: '.canvas',
            width: this.CW,
            height: this.CH,
        });
        this.layer = new Konva.Layer();
        this.stage.add(this.layer);
        this.transformer = new Konva.Transformer(
            {
                keepRatio: false,
                anchorSize: 10,
                borderStroke: 'rgba(0,0,255,0.5)',
                borderStrokeWidth: 1,
                borderDash: [3, 3],
                ignoreStroke: true,
                padding: 5,
                shouldOverdrawWholeArea: true,
            }
        );
        this.transformer.on("transformend", function (e) {
            console.log(e);
            e.target.setAttrs({ ...e.target.attrs, strokeWidth: 1 })
        })
        this.layer.add(this.transformer);
        this.selectionRectangle = new Konva.Rect({
            fill: 'rgba(0,149,255,0.25)',
            stroke: 'rgba(0,149,255)',
            visible: false,
        });
        this.layer.add(this.selectionRectangle);
        this.history.push(this.layer.clone());
        document.addEventListener('keyup', this.delKey);
        document.addEventListener('keyup', this.cloneKey);
        document.addEventListener('keyup', this.undoKey);
        document.addEventListener('keyup', this.redoKey);
    },

    beforeDestroy() {
        document.removeEventListener('keyup', this.delKey);
    },

    methods: {
        Action() {
            if (this.isDrawing) this.startDrawing();
            else this.selectWindow();
        },
        selectWindow() {
            this.stage.off('mousemove');
            this.stage.off('mouseup');
            const pos = this.stage.getPointerPosition();
            const shape = this.stage.getIntersection(pos);
            if (shape) return;
            this.emptyTransformer();
            let x1, y1, x2, y2;
            x1 = this.stage.getPointerPosition().x;
            y1 = this.stage.getPointerPosition().y;
            x2 = this.stage.getPointerPosition().x;
            y2 = this.stage.getPointerPosition().y;
            this.selectionRectangle.visible(true);
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
                let shapes = this.stage.find('Shape');
                let box = this.selectionRectangle.getClientRect();

                let selected = shapes.filter((shape) =>
                    shape !== this.selectionRectangle &&
                    Konva.Util.haveIntersection(box, shape.getClientRect())
                );
                if (selected.length > 0) this.transformer.nodes(selected);
                this.moveSelectedShapes();
                if(this.isDeletable){
                    this.deleteSelectedShapes();
                }
                else if(this.isClonable){
                    this.cloneSelectedShapes();
                }
            });
        },
        selectShape(event) {
            const pos = this.stage.getPointerPosition();
            const shape = this.stage.getIntersection(pos);
            if (shape && shape.index !== 0 && shape.index !== 1) {
                this.slectedShapeIndex = shape.index;
                console.log("selecred shape", this.slectedShapeIndex, this.layer.children[this.slectedShapeIndex]);
                this.transformer.nodes([this.layer.children[this.slectedShapeIndex]]);
                console.log(`Shape selected: ${this.layer.children[this.slectedShapeIndex].name()}`);
                this.moveSelectedShapes();
                if(this.isDeletable){
                    this.deleteSelectedShapes();
                }
                else if(this.isClonable){
                    this.cloneSelectedShapes();
                }
            }
            else {
                this.emptyTransformer();
                console.log('No shape selected');
            }
        },
        drawShape(shape) {
            this.drawingShape = shape;
            this.isDrawing = true;
            console.log(this.drawingShape);
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
        showPicker() {
            document.getElementById("PopUp").classList.toggle("showColorPicker");
            this.isPopupVisible = !this.isPopupVisible;
        },
        changeColor(event) {
            this.currentColor = event;
        },
        closePopup() {
            if (this.isPopupVisible) {
                this.showPicker();
            }
        },
        toggleNewPanel() {
            this.showResizePanel = !this.showResizePanel;
        },
        newCanvas() {
            if (this.inputX > 100 && this.inputY > 100 && this.inputX < 2000 && this.inputY < 2000){
                localStorage.setItem('canvasWidth', this.inputX);
                localStorage.setItem('canvasHeight', this.inputY);
                location.reload();
            }
            else{
                alert("Please enter a valid number between 100 and 2000");
                this.inputX = this.CW;
                this.inputY = this.CH;
            }
            this.toggleNewPanel();
        },
        cloneShape(){
            this.isClonable = !this.isClonable;
            if(this.isDeletable){
                this.isDeletable = !this.isDeletable;
                this.deleteColor='white';
            }
            if(this.isClonable) this.cloneColor='green';
            else this.cloneColor='white';
        },
        deleteShape(){
            this.isDeletable = !this.isDeletable;
            if(this.isClonable){
                this.isClonable = !this.isClonable;
                this.cloneColor='white';
            }
            if (this.isDeletable) this.deleteColor='red';
            else this.deleteColor='white';
        },
        emptyTransformer(){
            if(this.transformer){
                this.transformer.nodes().forEach((shape) => shape.draggable(false));
                this.transformer.nodes([]);
            }
        },
        deleteSelectedShapes(){
            if(this.transformer){
                this.transformer.nodes().forEach((shape) => shape.destroy());
                this.emptyTransformer();
            }
        },
        cloneSelectedShapes(){
            if(this.transformer){
                this.transformer.nodes().forEach((shape) =>
                this.layer.add(shape.clone().offsetX(100).offsetY(100)));
                this.emptyTransformer();
            }
        },
        moveSelectedShapes(){
            if(this.transformer){
                this.transformer.nodes().forEach((shape) => shape.draggable(true));
            }
        },
        changeShapeColor(color) {
            this.currentColor = color;
            if (this.transformer.nodes().length > 0) {
                this.transformer.nodes().forEach((shape) => {
                    if(shape instanceof Konva.Line) {
                        shape.stroke(color);
                    } else {
                        shape.fill(color);
                    }
                        });
                this.layer.draw();
            }
        },
        cloneSelected() {
            if (this.transformer.nodes().length > 0) {
                this.transformer.nodes().forEach((shape) => {
                    shape.fill(color);
                });
                this.layer.draw();
            }
        },
        cloneKey(event){
            if(event.shiftKey && event.keyCode == 68){
                this.cloneSelectedShapes();
                console.log("cloned");
            }
        },
        delKey(event){
            if(event.keyCode == 46){
                this.deleteSelectedShapes();
                console.log("deleted");
            }
        },
        undoKey(event) {
            if (event.ctrlKey && event.keyCode == 90) {
                this.undo();
                console.log("undone");
            }
        },

        redoKey(event) {
            if (event.ctrlKey && event.shiftKey && event.keyCode == 90) {
                this.redo();
                console.log("redone");
            }
        },

        ...HistoryFunctions,
        ...DrawingFunctions,
    },
}
</script>

<style scoped src="./PaintApp.css" lang="css"></style>
