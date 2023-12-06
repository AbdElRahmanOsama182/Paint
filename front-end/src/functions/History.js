const HistoryFunctions = {

    undo() {
        if (this.historyIndex === 0) return;
        this.historyIndex -= 1;
        console.log("to copy",this.history)
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
        this.selectionRectangle.visible(false)
        this.layer.draw()
        console.log("new layer", this.layer)
        this.layer.batchDraw()
    },
    clearHistory() {
        this.history = this.history.slice(0, this.historyIndex + 1);
    },
    saveRecord() {
        if (this.history.length > 0) {
            const historyPop = this.history[this.historyIndex];
            if (this.layer.children.length === historyPop.children.length) {
                const isEqual = this.layer.children.every((child, index) => {
                    if (child.index === 0 || child.index === 1) {
                        return true;
                    }
                    return (
                        child.x() === historyPop.children[index].x() &&
                        child.y() === historyPop.children[index].y() &&
                        child.width() === historyPop.children[index].width() &&
                        child.height() === historyPop.children[index].height() &&
                        child.fill() === historyPop.children[index].fill() &&
                        true
                    );
                });
                console.log(`Are they equal? ${isEqual}`);
                if (isEqual) {
                    return;
                }
            }
        }
        this.clearHistory();
        const newLayer = this.layer.clone();
        this.history.push(newLayer);
        this.historyIndex += 1;
        console.log(this.historyIndex);
    },
}

export { HistoryFunctions };
