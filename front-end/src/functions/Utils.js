import Konva from 'konva'
import { UpdateCircle, UpdateEllipse, UpdateLine, UpdatePolygon, UpdateRectangle } from "@/api/Updates";
import {CreateCircle,CreateEllipse,CreateLine,CreatePolygon,CreateRectangle} from '@/api/Creates'

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

export const createShape=(shape,id)=>{
    switch(getType(shape)){
        case 'circle':
            CreateCircle(shape,id);
            break;
        case 'rectangle':
            CreateRectangle(shape,id);
            break;
        case 'ellipse':
            CreateEllipse(shape,id);
            break;
        case 'polygon':
            CreatePolygon(shape,id);
            break;
        case 'line':
            CreateLine(shape,id);
            break;
        default:
            return;
    }
}

export const forceLoadImage=(image)=>{
    return new Promise((resolve, reject) => {
    
        image.onload = () => {
          // Image is loaded, resolve the promise
          
          resolve(image);
        };
    
        image.onerror = (error) => {
          // Image loading failed, reject the promise with an error
          reject(error);
        };
    
        // Set the src attribute to the Base64 data URL
      });
}