<template>
    <div>
        <div class="panel" v-bind:style="{ left: `${x}px`, top: `${y}px` }" @mousedown="startDrag" @mousemove="dragging"
            @mouseup="stopDrag" @mouseleave="stopDrag">
            <div class="commands">
                <button> load </button>
                <button @click="undo()"> undo </button>
                <button> save</button>
                <button @click="redo()"> redo </button>
                <button @click="deleteShape" :style="{ backgroundColor: deleteColor }"> delete </button>
                <button @click="cloneShape()" :style="{ backgroundColor: cloneColor }">clone</button>
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
                <button class="customcolor" @dblclick="showPicker" :style="{ backgroundColor: currentColor }"></button>

                <div id="PopUp" class="popup-content">
                    <v-color-picker @update:modelValue="changeColor" @mouseleave="closePopup" color="#e0dfdf">
                    </v-color-picker>
                </div>
            </div>

            <div class="colors">
                <button @click="currentColor = 'black'"> </button>
                <button @click="currentColor = 'white'"> </button>
                <button @click="currentColor = 'cyan'"> </button>
                <button @click="currentColor = 'blue'"> </button>
                <button @click="currentColor = 'yellow'"> </button>
                <button @click="currentColor = 'green'"> </button>
                <button @click="currentColor = 'magenta'"> </button>
                <button @click="currentColor = 'red'"> </button>
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
            currentColor: 'grey',
            history: [],
            historyIndex: 0,
            isDeletable: false,
            isClonable: false,
            cloneColor: 'white',
            deleteColor: 'white',
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
        this.transformer.on("transformend", async function (e) {
            console.log(e.currentTarget.nodes());
            for (let key in e.currentTarget.nodes()) {
                let shape = e.currentTarget.nodes()[key];
                console.log(shape.className)
                switch (shape.className) {
                    case 'Circle':
                        await fetch(
                            `http://localhost:8080/circle/${shape.index}`,
                            {
                                method: "PUT",
                                headers: {
                                    "Content-Type": "application/json",
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
                            }
                        ).then((res) => res.json())
                            .then((data) => console.log("success", data));
                        break;
                    case 'Ellipse':
                        await fetch(
                            `http://localhost:8080/ellipse/${shape.index}`,
                            {
                                method: "PUT",
                                headers: {
                                    "Content-Type": "application/json",
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
                            }
                        ).then((res) => res.json())
                            .then((data) => console.log("success", data));
                        break;
                    case 'Rect':
                        await fetch(
                            `http://localhost:8080/rectangle/${shape.index}`,
                            {
                                method: "PUT",
                                headers: {
                                    "Content-Type": "application/json",
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
                            }
                        ).then((res) => res.json())
                            .then((data) => console.log("success", data));
                        break;

                    case 'RegularPolygon':
                        await fetch(
                            `http://localhost:8080/triangle/${shape.index}`,
                            {
                                method: "PUT",
                                headers: {
                                    "Content-Type": "application/json",
                                },
                                body: JSON.stringify({

                                    id: shape.index,
                                    center: {
                                        x: shape.x(),
                                        y: shape.y(),
                                    },
                                    radius: shape.radius(),
                                    color: shape.fill(),
                                    sides: shape.sides(),
                                    scaleX: shape.scaleX(),
                                    scaleY: shape.scaleY(),
                                    rotation: shape.rotation(),
                                }),
                            }
                        ).then((res) => res.json())
                            .then((data) => console.log("success", data));
                        break;

                    case 'Line':
                        await fetch(
                            `http://localhost:8080/line/${shape.index}`,
                            {
                                method: "PUT",
                                headers: {
                                    "Content-Type": "application/json",
                                },
                                body: JSON.stringify({

                                    id: shape.index,
                                    points: shape.points(),
                                    color: shape.stroke(),
                                    scaleX: shape.scaleX(),
                                    scaleY: shape.scaleY(),
                                    rotation: shape.rotation(),
                                }),
                            }
                        ).then((res) => res.json())
                            .then((data) => console.log("success", data));
                        break;


                }
            }

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
                console.log(selected)
                if (selected.length > 0) this.transformer.nodes(selected);
                this.moveSelectedShapes();
                if (this.isDeletable) {
                    this.deleteSelectedShapes();
                }
                if (this.isClonable) {
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
                if (this.isDeletable) {
                    this.deleteSelectedShapes();
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
        cloneShape() {
            this.isClonable = !this.isClonable;
            if (this.isDeletable) {
                this.isDeletable = !this.isDeletable;
                this.deleteColor = this.activeColorfn(this.isDeletable);
            }
            this.cloneColor = this.activeColorfn(this.isClonable);
        },
        deleteShape() {
            this.isDeletable = !this.isDeletable;
            if (this.isClonable) {
                this.isClonable = !this.isClonable;
                this.cloneColor = this.activeColorfn(this.isClonable);
            }
            this.deleteColor = this.activeColorfn(this.isDeletable);
        },
        activeColorfn(bool) {
            if (bool) {
                return 'blue';
            }
            else {
                return 'white';
            }
        },
        emptyTransformer() {
            if (this.transformer) {
                this.transformer.nodes().forEach((shape) => shape.draggable(false));
                this.transformer.nodes([]);
            }
        },
        deleteSelectedShapes() {
            if (this.transformer) {
                this.transformer.nodes().forEach((shape) => shape.destroy());
                this.transformer.nodes([]);
            }
        },
        cloneSelectedShapes() {
            if (this.transformer) {
                this.transformer.nodes().forEach((shape) =>
                    this.layer.add(shape.clone().offsetX(100).offsetY(100)),

                    this.transformer.nodes([]));
            }
        },
        moveSelectedShapes() {
            if (this.transformer) {
                this.transformer.nodes().forEach((shape) => shape.draggable(true));
            }
        },
        delKey(event) {
            if (event.keyCode == 46) {
                this.deleteSelectedShapes();
            }
        },
        ...HistoryFunctions,
        ...DrawingFunctions,
    },
}
</script>

<style scoped src="./PaintApp.css" lang="css"></style>
