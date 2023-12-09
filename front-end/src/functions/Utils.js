import Konva from 'konva'
import { UpdateCircle, UpdateEllipse, UpdateLine, UpdatePolygon, UpdateRectangle } from "@/api/Updates";


export const getType=(shape)=>{
    if(shape instanceof Konva.Circle)
        return 'circle';
    else if(shape instanceof Konva.Rect)
        return 'rectangle';
    else if(shape instanceof Konva.Ellipse)
        return 'ellipse';
    else if(shape instanceof Konva.RegularPolygon)
        return 'polygon';
    else if(shape instanceof Konva.Line)
        return 'line';
    else
        return 'unknown';

}

export const updateShape=(shape)=>{
    switch(getType(shape)){
        case 'circle':
            UpdateCircle(shape);
            break;
        case 'rectangle':
            UpdateRectangle(shape);
            break;
        case 'ellipse':
            UpdateEllipse(shape);
            break;
        case 'polygon':
            UpdatePolygon(shape);
            break;
        case 'line':
            UpdateLine(shape);
            break;
        default:
            return;
    }
}
