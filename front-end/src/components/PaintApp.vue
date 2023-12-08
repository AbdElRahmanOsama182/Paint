<template>
    <div>
        <div class="panel" v-bind:style="{ left: `${x}px`, top: `${y}px` }" @mousedown="startDrag" @mousemove="dragging"
            @mouseup="stopDrag" @mouseleave="stopDrag">
            <div class="commands">
                <button @click="showLoadOptions">Load</button>
                <div v-if="showLoadDropdown" class="dropdown">
                    <button @click="loadFile('json')">JSON</button>
                    <button @click="loadFile('xml')">XML</button>
                </div>
                <button @click="undo()">undo</button>
                <button @click="showSaveOptions">Save as</button>
                <div v-if="showSaveDropdown" class="dropdown">
                    <button @click="saveFile('json')">JSON</button>
                    <button @click="saveFile('xml')">XML</button>
                    <button @click="saveFile('png')">Image</button>
                </div>
                <button @click="redo()">redo</button>
                <button @click="deleteShape" :style="{ backgroundColor: deleteColor }">delete</button>
                <button @click="cloneShape()" :style="{ backgroundColor: cloneColor }">clone</button>
                <button @click="resizeShape" :style="{ backgroundColor: resizeColor }">resize</button>
                <button @click="clearAll">clear</button>
                <button class="alien" @dblclick="clearAll">👽</button>
            </div>
            <div class="shapes">
                <button @click="drawShape('Circle')">◯</button>
                <button @click="drawShape('Ellipse')">⬯</button>
                <button @click="drawShape('Rectangle')">▭</button>
                <button @click="drawShape('Square')">◻</button>
                <button @click="drawShape('Triangle')">△</button>
                <button @click="drawShape('5')">⬠</button>
                <button @click="drawShape('6')">⬡</button>
                <button @click="drawShape('Line')">/</button>
            </div>
            <div class="curr">
                <button class="customcolor" @click="changeShapeColor(currentColor)" @dblclick="showPicker"
                    :style="{ backgroundColor: currentColor }"></button>

                <div id="PopUp" class="popup-content">
                    <v-color-picker @update:modelValue="changeColor" @mouseleave="closePopup"
                        color="#e0dfdf"></v-color-picker>
                </div>
            </div>

            <div class="colors">
                <button @click="changeShapeColor('black')"></button>
                <button @click="changeShapeColor('white')"></button>
                <button @click="changeShapeColor('cyan')"></button>
                <button @click="changeShapeColor('blue')"></button>
                <button @click="changeShapeColor('yellow')"></button>
                <button @click="changeShapeColor('green')"></button>
                <button @click="changeShapeColor('magenta')"></button>
                <button @click="changeShapeColor('red')"></button>
            </div>
        </div>
        <div class="wrapper">
            <div @dblclick="selectShape" @mousedown="Action" @mouseup="stopDrawing" class="canvas"
                :style="{ width: `${CW}px`, height: `${CH}px` }"></div>
        </div>
    </div>
</template>

<script>
import Konva from "konva";
import { DrawingFunctions } from "../functions/Drawing.js";
import { HistoryFunctions } from "../functions/History.js";
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
            currentColor: "grey",
            history: [],
            historyIndex: 0,
            clonedshape: null,
            isDeletable: false,
            isClonable: false,
            cloneColor: "white",
            deleteColor: "white",
            showLoadDropdown: false,
            showSaveDropdown: false,
            isResizing: false,
            resizeColor: 'white',
            clickedShapeIndex: null,
        };
    },
    mounted() {
        this.stage = new Konva.Stage({
            container: ".canvas",
            width: this.CW,
            height: this.CH
        });
        this.layer = new Konva.Layer();
        this.stage.add(this.layer);
        this.transformer = new Konva.Transformer({
            keepRatio: false,
            anchorSize: 10,
            borderStroke: "rgba(0,0,255,0.5)",
            borderStrokeWidth: 1,
            borderDash: [3, 3],
            ignoreStroke: true,
            padding: 5,
            shouldOverdrawWholeArea: true
        });
        this.transformer.on("transformend", async function (e) {
            console.log(e.currentTarget.nodes());
            for (let key in e.currentTarget.nodes()) {
                let shape = e.currentTarget.nodes()[key];
                console.log(shape.className);
                switch (shape.className) {
                    case "Circle":
                        await fetch(`http://localhost:8080/circle/${shape.index}`, {
                            method: "PUT",
                            headers: {
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify({
                                id: shape.index,
                                center: {
                                    x: shape.x(),
                                    y: shape.y()
                                },
                                radius: shape.radius(),
                                color: shape.fill(),
                                scaleX: shape.scaleX(),
                                scaleY: shape.scaleY(),
                                rotation: shape.rotation()
                            })
                        })
                            .then(res => res.json())
                            .then(data => console.log("success", data));
                        break;
                    case "Ellipse":
                        await fetch(`http://localhost:8080/ellipse/${shape.index}`, {
                            method: "PUT",
                            headers: {
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify({
                                id: shape.index,
                                center: {
                                    x: shape.x(),
                                    y: shape.y()
                                },
                                radiusX: shape.radiusX(),
                                radiusY: shape.radiusY(),
                                color: shape.fill(),
                                scaleX: shape.scaleX(),
                                scaleY: shape.scaleY(),
                                rotation: shape.rotation()
                            })
                        })
                            .then(res => res.json())
                            .then(data => console.log("success", data));
                        break;
                    case "Rect":
                        await fetch(`http://localhost:8080/rectangle/${shape.index}`, {
                            method: "PUT",
                            headers: {
                                "Content-Type": "application/json"
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
                                rotation: shape.rotation()
                            })
                        })
                            .then(res => res.json())
                            .then(data => console.log("success", data));
                        break;
                    case "RegularPolygon":
                        await fetch(`http://localhost:8080/triangle/${shape.index}`, {
                            method: "PUT",
                            headers: {
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify({
                                id: shape.index,
                                center: {
                                    x: shape.x(),
                                    y: shape.y()
                                },
                                radius: shape.radius(),
                                color: shape.fill(),
                                sides: shape.sides(),
                                scaleX: shape.scaleX(),
                                scaleY: shape.scaleY(),
                                rotation: shape.rotation()
                            })
                        })
                            .then(res => res.json())
                            .then(data => console.log("success", data));
                        break;
                    case "Line":
                        await fetch(`http://localhost:8080/line/${shape.index}`, {
                            method: "PUT",
                            headers: {
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify({
                                id: shape.index,
                                points: shape.points(),
                                color: shape.stroke(),
                                scaleX: shape.scaleX(),
                                scaleY: shape.scaleY(),
                                rotation: shape.rotation()
                            })
                        })
                            .then(res => res.json())
                            .then(data => console.log("success", data));
                        break;
                }
            }
        });
        this.layer.add(this.transformer);
        this.selectionRectangle = new Konva.Rect({
            fill: "rgba(0,149,255,0.25)",
            stroke: "rgba(0,149,255)",
            visible: false
        });
        this.layer.add(this.selectionRectangle);
        this.history.push(this.layer.clone());
        document.addEventListener("keyup", this.delKey);
        document.addEventListener("keyup", this.cloneKey);
        document.addEventListener("keyup", this.undoKey);
        document.addEventListener("keyup", this.redoKey);
    },
    beforeDestroy() {
        document.removeEventListener("keyup", this.delKey);
    },
    methods: {
        showSaveOptions() {
            if (!this.showSaveDropdown) {
                this.resetButtons();
                this.showSaveDropdown = true;
            }
            else {
                this.resetButtons();
                this.showSaveDropdown = false;
            }
        },
        async saveFile(extension) {
            this.emptyTransformer();
            try {
                let options;
                if (extension === 'json') {
                    options = {
                        types: [
                            {
                                description: 'JSON Files',
                                accept: { 'application/json': ['.json'] },
                            },
                        ],
                    };
                } else if (extension === 'png') {
                    options = {
                        types: [
                            {
                                description: 'PNG Files',
                                accept: { 'image/png': ['.png'] },
                            },
                        ],
                    };
                } else  {
                    options = {
                        types: [
                            {
                                description: 'XML Files',
                                accept: { 'application/xml': ['.xml'] },
                            },
                        ],
                    };
                };
                const fileHandle = await window.showSaveFilePicker(options);
                const fileName = fileHandle.name.endsWith(extension)
                    ? fileHandle.name
                    : fileHandle.name + extension;
                if (extension === 'png') {
                    const dataURL = this.stage.toDataURL({ pixelRatio: 3});
                    const blob = await (await fetch(dataURL)).blob();
                    const writable = await fileHandle.createWritable();
                    await writable.write(blob);
                    await writable.close();
                    console.log('File saved successfully!');
                    this.showSaveDropdown = false;
                    return;
                }
                const response = await fetch(`http://localhost:8080/save/${extension}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json', // Set the content type
                    },
                });

                // Get the file content from the response
                const fileContent = await response.blob();
                console.log(fileContent);
                // Write the file content to the user-selected file
                const writable = await fileHandle.createWritable();
                await writable.write(fileContent);
                await writable.close();

                console.log('File saved successfully!');
                this.showSaveDropdown = false;
            } catch (error) {
                this.showSaveDropdown = false;
                console.error('Error saving file:', error);
            }
        },
        showLoadOptions() {
            if (!this.showLoadDropdown) {
                this.resetButtons();
                this.showLoadDropdown = true;
            }
            else {
                this.resetButtons();
                this.showLoadDropdown = false;
            }
        },
        async loadFile(extension) {
            try {
                let options;
                if (extension === "json") {
                    options = {
                        types: [
                            {
                                description: "JSON Files",
                                accept: { "application/json": [".json"] }
                            }
                        ]
                    };
                } else {
                    options = {
                        types: [
                            {
                                description: "XML Files",
                                accept: { "application/xml": [".xml"] }
                            }
                        ]
                    };
                }
                const [fileHandle] = await window.showOpenFilePicker(options);
                const file = await fileHandle.getFile();
                const contents = await file.text();
                if (!file.name.endsWith(extension)) {
                    console.error("Invalid file format selected.");
                    this.showLoadDropdown = false;
                    return;
                }
                console.log('File loaded successfully:', contents);
                const response = await fetch(`http://localhost:8080/load/${extension}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': extension === 'json' ? 'application/json' : 'application/xml',
                    },
                    body: contents ,
                });
                if (!response.ok) {
                    console.error('Error loading file:', response);
                    this.showLoadDropdown = false;
                    return;
                }
                let newShapes;
                if (extension==='json') newShapes = JSON.parse(contents);
                else newShapes = await response.json();
                const shapesArray = Object.values(newShapes);
                console.log(shapesArray);
                this.clearAll();
                shapesArray.forEach((shape) => {
                    this.createFromJson(shape);
                });
                console.log(this.layer.children);
                this.layer.draw();
                this.saveRecord();
                this.showLoadDropdown = false;
            } catch (error) {
                this.showLoadDropdown = false;
                console.error("Error loading file:", error);
            }
        },
        Action() {
            this.showLoadDropdown = false;
            this.showSaveDropdown = false;
            if (this.isResizing) this.checkShape();
            else if (this.isDrawing) this.startDrawing();
            else this.selectWindow();
        },
        clearAll() {
            this.resetButtons();
            this.emptyTransformer();
            while (this.layer.children.length > 2) {
                this.layer.children[2].destroy();
            }
            this.layer.draw();
            this.saveRecord();
        },
        selectWindow() {
            this.stage.off("mousemove");
            this.stage.off("mouseup");
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
            this.stage.on("mousemove", e => {
                if (!this.selectionRectangle.visible()) return;
                e.evt.preventDefault();
                x2 = this.stage.getPointerPosition().x;
                y2 = this.stage.getPointerPosition().y;
                this.selectionRectangle.setAttrs({
                    x: Math.min(x1, x2),
                    y: Math.min(y1, y2),
                    width: Math.abs(x2 - x1),
                    height: Math.abs(y2 - y1)
                });
            });
            this.stage.on("mouseup", e => {
                if (!this.selectionRectangle.visible()) return;
                e.evt.preventDefault();
                setTimeout(() => {
                    this.selectionRectangle.visible(false);
                });
                let shapes = this.stage.find("Shape");
                let box = this.selectionRectangle.getClientRect();

                let selected = shapes.filter(
                    shape =>
                        shape !== this.selectionRectangle &&
                        Konva.Util.haveIntersection(box, shape.getClientRect())
                );
                console.log(selected);
                if (selected.length > 0) this.transformer.nodes(selected);
                this.moveSelectedShapes();
                if (this.isDeletable) {
                    this.deleteSelectedShapes();
                }
                else if (this.isClonable) {
                    this.cloneSelectedShapes();
                }
            });
        },
        selectShape(event) {
            const pos = this.stage.getPointerPosition();
            const shape = this.stage.getIntersection(pos);
            if (shape && shape.index !== 0 && shape.index !== 1) {
                this.slectedShapeIndex = shape.index;
                console.log(
                    "selecred shape",
                    this.slectedShapeIndex,
                    this.layer.children[this.slectedShapeIndex]
                );
                this.transformer.nodes([this.layer.children[this.slectedShapeIndex]]);
                console.log(
                    `Shape selected: ${this.layer.children[
                        this.slectedShapeIndex
                    ].name()}`
                );
                this.moveSelectedShapes();
                if (this.isDeletable) {
                    this.deleteSelectedShapes();
                } else if (this.isClonable) {
                    this.cloneSelectedShapes();
                }
                else if (this.isClonable) {
                    this.cloneSelectedShapes();
                }
            }
            else {
                this.emptyTransformer();
                console.log("No shape selected");
            }
        },
        resetButtons() {
            this.isDrawing = false;
            this.isDragging = false;
            this.isDeletable = false;
            this.isClonable = false;
            this.isResizing = false;
            this.showLoadDropdown = false;
            this.showSaveDropdown = false;
            this.deleteColor = "white";
            this.cloneColor = "white";
            this.resizeColor = "white";
        },
        drawShape(shape) {
            this.emptyTransformer();
            this.resetButtons();
            this.setResize(false);
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
        resizeShape(){
            this.emptyTransformer();
            this.setDelete(0);
            this.setClone(0);
            this.setResize(!this.isResizing);  
            this.showLoadDropdown = false;
            this.showSaveDropdown = false;
        },
        cloneShape() {
            this.isClonable = !this.isClonable;
            if (this.isDeletable) {
                this.isDeletable = !this.isDeletable;
                this.deleteColor = "white";
            }
            if (this.isResizing) {
                this.isResizing = !this.isResizing;
                this.resizeColor = "white";
            }
            this.showLoadDropdown = false;
            this.showSaveDropdown = false;
            this.emptyTransformer();
            if (this.isClonable) this.cloneColor = "green";
            else this.cloneColor = "white";
        },
        deleteShape() {
            this.emptyTransformer();
            this.isDeletable = !this.isDeletable;
            if (this.isClonable) {
                this.isClonable = !this.isClonable;
                this.cloneColor = "white";
            }
            if (this.isResizing) {
                this.isResizing = !this.isResizing;
                this.resizeColor = "white";
            }
            this.showLoadDropdown = false;
            this.showSaveDropdown = false;
            if (this.isDeletable) this.deleteColor = "red";
            else this.deleteColor = "white";
        },
        activeColorfn(bool) {
            if (bool) {
                return 'rgba(35,209,20,1)';
            }
            else {
                return 'white';
            }
        },
        emptyTransformer() {
            if (this.transformer) {
                this.transformer.nodes().forEach(shape => shape.draggable(false));
                this.transformer.nodes([]);
            }
        },
        async deleteSelectedShapes() {
            if (this.transformer) {
                this.transformer.nodes().forEach(async (shape) => {
                    await fetch(`http://localhost:8080/shape/${shape.index}`, {
                        method: "DELETE",
                    });
                    shape.destroy();
                });
                this.transformer.nodes([]);
                this.saveRecord();
            }
        },
        async cloneSelectedShapes() {
            if (this.transformer) {
                let shapes = this.transformer.nodes();
                let clonedShapes = [];
                this.emptyTransformer();
                shapes.forEach((shape) => {
                    clonedShapes.push(shape.clone().offsetX(50).offsetY(50));
                });
                clonedShapes.forEach((shape) => {
                    this.layer.add(shape);
                });
                this.transformer.nodes(clonedShapes);
                this.moveSelectedShapes();
            }
        },
        moveSelectedShapes() {
            if (this.transformer) {
                this.transformer.nodes().forEach(shape => shape.draggable(true));
            }
        },
        changeShapeColor(color) {
            this.currentColor = color;
            if (this.transformer.nodes().length > 0) {
                this.transformer.nodes().forEach(shape => {
                    if (shape instanceof Konva.Line) {
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
                this.transformer.nodes().forEach(shape => {
                    shape.fill(color);
                });
                this.layer.draw();
            }
        },
        cloneKey(event) {
            if (event.shiftKey && event.keyCode == 68) {
                this.cloneSelectedShapes();
                console.log("cloned");
            }
        },
        delKey(event) {
            if (event.keyCode == 46) {
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
        checkShape(){
            if(this.isResizing){
                const pos = this.stage.getPointerPosition();
                const shape = this.stage.getIntersection(pos);
                console.log(shape);
                if(shape) {
                    this.drawingShape = this.shapeType(shape);
                    this.clickedShapeIndex = shape.index;
                }
            }
            this.stage.on('mousemove', this.drawing);
        },
        setResize(value){
            this.isResizing = value;
            this.resizeColor = this.activeColorfn(this.isResizing);
            this.drawingShape = null;
            this.clickedShapeIndex = null;
        },
        setDelete(value){
            this.isDeletable = value;
            this.deleteColor = this.activeColorfn(this.isDeletable);
        },
        setClone(value){
            this.isClonable = value;
            this.cloneColor = this.activeColorfn(this.isClonable);
        },
        shapeType(shape){
            var name = shape.getClassName();
            if(name == "RegularPolygon"){
                name = "Triangle";
            }
            else if(name == "Rect"){
                if(shape.getAttr('height') == shape.getAttr('width')){
                    name = "Square";
                }
                else{
                    name = "Rectangle";
                }
            }
            return name;
        },
        ...HistoryFunctions,
        ...DrawingFunctions
    }
};
</script>

<style scoped src="./PaintApp.css" lang="css"></style>
