import { API_ROOT } from "@/config";
export const CreateCircle = async (shape, id) => {
    console.log(id)
    return await fetch(API_ROOT+`/circle`, {
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
    return await fetch(API_ROOT+`/rectangle`, {
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

export const CreateImage = async (shape, id) => {
    return await fetch(API_ROOT+`/image`, {
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
            src: shape.image().src,
            scaleX: shape.scaleX(),
            scaleY: shape.scaleY(),
            rotation: shape.rotation()
        })
    })
        .then(res => res.json())
        .then(data => { console.log("created",data); return data });
}

export const CreateEllipse = async (shape, id) => {
    return await fetch(API_ROOT+`/ellipse`, {
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
    return await fetch(API_ROOT+`/line`, {
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
    return await fetch(API_ROOT+`/polygon`, {
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
    return await fetch(API_ROOT+`/triangle`, {
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
    return await fetch(API_ROOT+`/square`, {
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