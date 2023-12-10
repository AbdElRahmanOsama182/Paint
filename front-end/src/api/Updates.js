export const UpdateCircle = async (shape) => {
    return await fetch(`http://localhost:8080/circle/${shape.index}`, {
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
        .then(data => { console.log("updated"); return data });
}

export const UpdateRectangle = async (shape) => {
    return await fetch(`http://localhost:8080/rectangle/${shape.index}`, {
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
        .then(data => { console.log("updated"); return data });
}

export const UpdateImage = async (shape) => {
    return await fetch(`http://localhost:8080/image/${shape.index}`, {
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
            src: shape.image().src,
            scaleX: shape.scaleX(),
            scaleY: shape.scaleY(),
            rotation: shape.rotation()
        })
    })
        .then(res => res.json())
        .then(data => { console.log("updated"); return data });
}
export const UpdateEllipse = async (shape) => {
    return await fetch(`http://localhost:8080/ellipse/${shape.index}`, {
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
        .then(data => { console.log("updated"); return data });
}

export const UpdatePolygon = async (shape) => {
    return await fetch(`http://localhost:8080/polygon/${shape.index}`, {
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
        .then(data => { console.log("updated"); return data });
}

export const UpdateLine = async (shape) => {
    return await fetch(`http://localhost:8080/line/${shape.index}`, {
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
        .then(data => { console.log("updated"); return data });
}