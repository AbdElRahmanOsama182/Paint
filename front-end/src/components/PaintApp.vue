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
                <button @click="undo()">Undo</button>
                <button @click="showSaveOptions">Save as</button>
                <div v-if="showSaveDropdown" class="dropdown">
                    <button @click="saveFile('json')">JSON</button>
                    <button @click="saveFile('xml')">XML</button>
                    <button @click="saveFile('png')">Image</button>
                </div>
                <button @click="redo()">Redo</button>
                <button @click="deleteShape" :style="{ backgroundColor: deleteColor }">Delete</button>
                <button @click="cloneShape()" :style="{ backgroundColor: cloneColor }">Clone</button>
                <button @click="resizeShape" :style="{ backgroundColor: resizeColor }">Resize</button>
                <button @click="clearAll">Clear</button>
                <button class="alien" @click="addImage">👽</button>
                <button class="alien" @click="generateImage">🤖</button>
                <input v-if="displayPrompt" type="text" v-model="prompt" :style="{ border: '2px solid white', borderRadius: 3 }"
                    placeholder="Draw Something" />

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
                <button @click="drawShape('Brush')">🖌</button>
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
import { UpdateCircle, UpdateEllipse, UpdateLine, UpdatePolygon, UpdateRectangle } from "../api/Updates.js";
import { CreateImage } from "../api/Creates.js";
import { updateShape, createShape } from "../functions/Utils.js";
import * as fal from "@fal-ai/serverless-client";
import { downloadImage } from "../functions/Utils.js";


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
            prompt: "",
            displayPrompt: false
        };
    },
    async mounted() {
        await fetch("http://localhost:8080/layer/clear", {
            method: "DELETE",
        });
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
                console.log(shape, shape.className);
                switch (shape.className) {
                    case "Circle":
                        await UpdateCircle(shape);
                        break;
                    case "Ellipse":
                        await UpdateEllipse(shape);
                        break;
                    case "Rect":
                        await UpdateRectangle(shape);
                        break;
                    case "RegularPolygon":
                        await UpdatePolygon(shape);
                        break;
                    case "Line":
                        await UpdateLine(shape);
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
                } else {
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
                    const dataURL = this.stage.toDataURL({ pixelRatio: 3 });
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
                    body: contents,
                });
                if (!response.ok) {
                    console.error('Error loading file:', response);
                    this.showLoadDropdown = false;
                    return;
                }
                let newShapes;
                if (extension === 'json') newShapes = JSON.parse(contents);
                else newShapes = await response.json();
                const shapesArray = Object.values(newShapes);
                console.log(shapesArray);
                this.clearAll();
                // shapesArray.forEach(async (shape) => {
                //     await this.createFromJson(shape);
                // });
                for (let shape in shapesArray) {
                    console.log(shape)
                    await this.createFromJson(shapesArray[shape]);



                }
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
        async clearAll() {
            this.resetButtons();
            this.emptyTransformer();
            while (this.layer.children.length > 2) {
                await fetch(`http://localhost:8080/shape/${this.layer.children[this.layer.children.length - 1].index}`, {
                    method: "DELETE",
                });
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
            this.displayPrompt = false;
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
        resizeShape() {
            this.emptyTransformer();
            this.setDelete(0);
            this.setClone(0);
            this.setResize(!this.isResizing);
            this.showLoadDropdown = false;
            this.showSaveDropdown = false;
            this.displayPrompt = false;
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
            this.displayPrompt = false;
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
            this.displayPrompt = false;
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
                this.transformer.nodes().forEach(shape => { shape.draggable(false); updateShape(shape) });
                this.saveRecord();
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
                    clonedShapes.push(shape.clone());
                });
                console.log(this.layer)
                clonedShapes.forEach((shape) => {
                    console.log(shape)
                    this.layer.add(shape);
                    shape.index = this.layer.children.length - 1;
                    createShape(shape);
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
                    updateShape(shape);
                });
                this.layer.draw();
                this.saveRecord();
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
        checkShape() {
            if (this.isResizing) {
                const pos = this.stage.getPointerPosition();
                const shape = this.stage.getIntersection(pos);
                console.log(shape);
                if (shape) {
                    this.drawingShape = this.shapeType(shape);
                    this.clickedShapeIndex = shape.index;
                }
            }
            this.stage.on('mousemove', this.drawing);
        },
        setResize(value) {
            this.isResizing = value;
            this.resizeColor = this.activeColorfn(this.isResizing);
            this.drawingShape = null;
            this.clickedShapeIndex = null;
        },
        setDelete(value) {
            this.isDeletable = value;
            this.deleteColor = this.activeColorfn(this.isDeletable);
        },
        setClone(value) {
            this.isClonable = value;
            this.cloneColor = this.activeColorfn(this.isClonable);
        },
        shapeType(shape) {
            var name = shape.getClassName();
            if (name == "RegularPolygon") {
                name = "Triangle";
            }
            else if (name == "Rect") {
                if (shape.getAttr('height') == shape.getAttr('width')) {
                    name = "Square";
                }
                else {
                    name = "Rectangle";
                }
            }
            return name;
        },
        async addImage() {
            this.emptyTransformer();
            if (this.isDeletable) {
                this.isDeletable = !this.isDeletable;
                this.deleteColor = "white";
            }
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
            this.displayPrompt = false;
            try {
                const [fileHandle] = await window.showOpenFilePicker({
                    types: [
                        {
                            description: 'Images',
                            accept: {
                                'image/*': ['.png'],
                            },
                        },
                    ],
                });

                const file = await fileHandle.getFile();
                const imageUrl = URL.createObjectURL(file);
                const image = new Image();
                image.src = imageUrl;
                image.onload = () => {


                    // Get base64 code
                    const canvas = document.createElement('canvas');
                    const context = canvas.getContext('2d');
                    canvas.width = image.width;
                    canvas.height = image.height;

                    context.drawImage(image, 0, 0);

                    const base64String = canvas.toDataURL('image/png');
                    console.log(base64String);

                    // add image
                    const tempImage = new Image();
                    tempImage.src = base64String;
                    tempImage.onload = () => {
                        const img = new Konva.Image({
                            x: 500,
                            y: 200,
                            image: tempImage,
                            draggable: false,
                            width: tempImage.width / 2,
                            height: tempImage.height / 2,
                        });
                        this.layer.add(img);
                        CreateImage(img);
                        // img.index = this.layer.children.length - 1;
                        // createShape(img);
                        this.layer.draw();


                        console.log(this.layer.children)

                    }


                    // this.saveRecord();
                };
                // Now you can use the imageUrl as the source for an <img> element or perform other actions.
                console.log('Selected Image URL:', imageUrl);
            } catch (error) {
                console.error('Error selecting image:', error);
            }
        },
        async generateImage() {
            this.emptyTransformer();
            if (this.isDeletable) {
                this.isDeletable = !this.isDeletable;
                this.deleteColor = "white";
            }
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
            if (this.displayPrompt) {
                fal.config({
                    // Can also be auto-configured using environment variables:
                    // Either a single FAL_KEY or a combination of FAL_KEY_ID and FAL_KEY_SECRET
                    credentials: '8b5d65b5-4e5c-4c83-bdf8-c0ed9708169d:3cdc6ea7bbade340ac9ee6a0df89b894',
                });
                const result = await fal.subscribe("110602490-lora", {
                    input: {
                        model_name: "stabilityai/stable-diffusion-xl-base-1.0",
                        prompt: this.prompt,
                    },
                    logs: true,
                    onQueueUpdate: (update) => {
                        if (update.status === "IN_PROGRESS") {
                            update.logs.map((log) => log.message).forEach(console.log);
                        }
                    },
                });
                // const result = {
                //     "images": [
                //         {
                //             "url": "https://storage.googleapis.com/fal_file_storage/59eba1c100d44b68b5c81c0da9c6157b.png?X-Goog-Algorithm=GOOG4-RSA-SHA256&X-Goog-Credential=distributed-worker%40isolate-dev-hot-rooster.iam.gserviceaccount.com%2F20231210%2Fauto%2Fstorage%2Fgoog4_request&X-Goog-Date=20231210T012841Z&X-Goog-Expires=172800&X-Goog-SignedHeaders=host&X-Goog-Signature=29b9d571ff4587b507ae92ea654da98950993f8c30ddf123d0dd71a4d9fae4b3089abdb035c3c1ea9e2f4036c7219624ce39baba5d02ff0f582129356954272f24a6fb387950c345aad5a2ad950d70c2c4f1d5d86626107457382d4f82bebb1814dddcdf38b2caf860fde0b8bdd8d370d4884378c124dbb30492bdab75aa61d20570f9596e8ca3f47a37d7b78b922b10d051bd0a9b508493a05f5e168e2172acb91fcf3b2588b1041e4be7809e0af5fc2b9be6798975eba512a5358dc978fe0f73eb4385c484f9f7b1be5a09e9a1b62a94609ac10a10403aaecddd2f00af5e5eb21ca09d7c167ebb717681b651717a90d7ae3d98326af798222703b77a9f8d5e",
                //             "content_type": "image/png",
                //             "file_name": "59eba1c100d44b68b5c81c0da9c6157b.png",
                //             "file_size": 939190,
                //             "width": 544,
                //             "height": 960
                //         }
                //     ],
                //     "seed": 3088576288
                // }
                console.log(this.prompt)
                const image = new Image();
                image.src = result.images[0].url;
                image.onload = () => {

                    console.log(image)
                    // add image
                    const img = new Konva.Image({
                        x: 500,
                        y: 200,
                        image: image,
                        draggable: false,
                        width: image.width / 2,
                        height: image.height / 2,
                    });
                    console.log("here")
                    this.layer.add(img);
                    console.log("There")
                    CreateImage(img);
                    // img.index = this.layer.children.length - 1;
                    // createShape(img);
                    this.layer.draw();
                    console.log(this.layer.children)

                    console.log(this.layer.children)

                    // this.saveRecord();
                    this.displayPrompt = false;
                };

            } else {
                this.displayPrompt = true;
            }

        },


        ...HistoryFunctions,
        ...DrawingFunctions
    }
};
</script>

<style scoped src="./PaintApp.css" lang="css"></style>
