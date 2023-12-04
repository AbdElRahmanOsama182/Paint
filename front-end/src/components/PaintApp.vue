<template>
    <div>
        <div class="panel" v-bind:style="{ left: `${x}px`, top: `${y}px` }" @mousedown="startDrag" @mousemove="dragging"
            @mouseup="stopDrag" @mouseleave="stopDrag">
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
                <button class="customcolor" @dblclick="showPicker" :style="{ backgroundColor: currentColor }">
                    <p v-if="isPopupVisible">▿</p>
                    <p v-else>▵</p>
                </button>
                <div id="PopUp" class="popup-content">
                    <v-color-picker @update:modelValue="changeColor" @mouseleave="closePopup" color="#e0dfdf">
                    </v-color-picker>
                </div>
            </div>
        </div>

        <div class="wrapper">
            <div @dblclick="selectShape" @mousedown="Action" @mouseup="stopDrawing" class="canvas"
                :style="{ width: `${CW}px`, height: `${CH}px` }">

            </div>
        </div>

    </div>
</template>

<script>
import Konva from 'konva';
import { Stage, Layer, Rect, Circle, Ellipse, Line } from 'konva';
import { DrawingFunctions } from '../functions/Drawing.js';
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
            currentColor: 0
        };
    },
    mounted() {
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
        ...DrawingFunctions,
    },
}
</script>

<style scoped src="./PaintApp.css" lang="css"></style>
