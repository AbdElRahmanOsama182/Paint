const HistoryFunctions = {

    undo() {
        if (this.historyIndex === 0) return;
        this.historyIndex -= 1;
        console.log(this.history)
        this.drawNewLayer()
    },
    redo() {
        if (this.historyIndex === this.history.length - 1) return;
        this.historyIndex += 1;
        this.drawNewLayer()
    },
    drawNewLayer() {
        // destroy old layer
        let newLayer = this.history[this.historyIndex]
        this.layer.destroyChildren()
        for (let child in newLayer.children) {
            console.log(newLayer.children[child])
            this.layer.add(newLayer.children[child].clone())
        }
        this.transformer = this.layer.children[0]
        this.selectionRectangle=this.layer.children[1]
        this.layer.draw()
        this.layer.batchDraw()
    },
    clearHistory() {
        this.history = this.history.slice(0, this.historyIndex + 1);
    },
    saveRecord() {
        this.clearHistory();
        let newLayer = this.layer.clone();
        this.history.push(newLayer);
        this.historyIndex += 1;
    },
}

export { HistoryFunctions }