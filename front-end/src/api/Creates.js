export const CreateCircle = async (shape, id) => {
    console.log(id)
    return await fetch(`http://localhost:8080/circle`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            id: id ? id : shape.index,
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
        .then(data => { console.log("created"); return data });
}

export const CreateRectangle = async (shape, id) => {
    return await fetch(`http://localhost:8080/rectangle`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            id: id ? id : shape.index,

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
        .then(data => { console.log("created"); return data });
}

export const CreateEllipse = async (shape, id) => {
    return await fetch(`http://localhost:8080/ellipse`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            id: id ? id : shape.index,

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
        .then(data => { console.log("created"); return data });
}

export const CreateLine = async (shape, id) => {
    return await fetch(`http://localhost:8080/line`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            id: id ? id : shape.index,

            points: shape.points(),
            color: shape.stroke(),
            scaleX: shape.scaleX(),
            scaleY: shape.scaleY(),
            rotation: shape.rotation()
        })
    })
        .then(res => res.json())
        .then(data => { console.log("created"); return data });
}

export const CreatePolygon = async (shape, id) => {
    return await fetch(`http://localhost:8080/polygon`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            id: id ? id : shape.index,

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
        .then(data => { console.log("created"); return data });
}

export const CreateTriangle = async (shape, id) => {
    return await fetch(`http://localhost:8080/triangle`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            id: id ? id : shape.index,

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
        .then(data => { console.log("created"); return data });
}
export const CreateSquare = async (shape, id) => {
    return await fetch(`http://localhost:8080/square`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            id: id ? id : shape.index,

            x: shape.x(),
            y: shape.y(),
            width: shape.width(),
            height: shape.height(),
            color: shape.fill(),
            scaleX: shape.scaleX(),
            scaleY: shape.scaleY(),
            rotation: shape.rotation(),
        })
    })
        .then(res => res.json())
        .then(data => { console.log("created"); return data });
}