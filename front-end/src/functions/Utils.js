import Konva from 'konva'
import { UpdateCircle, UpdateEllipse, UpdateLine, UpdatePolygon, UpdateRectangle, UpdateImage } from "@/api/Updates";
import {CreateCircle,CreateEllipse,CreateLine,CreatePolygon,CreateRectangle, CreateImage} from '@/api/Creates'

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
    else if (shape instanceof Konva.Image)
        return 'image';
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
        case 'image':
            UpdateImage(shape);
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
        case 'image':
            CreateImage(shape,id);
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

export const downloadImage=async(url, fileName)=> {
    try {
      const response = await fetch(url);
      const blob = await response.blob();
  
      // Create a link element
      const link = document.createElement('a');
  
      // Create a Blob URL for the downloaded image
      const blobUrl = URL.createObjectURL(blob);
  
      // Set the link attributes
      link.href = blobUrl;
      link.download = fileName || 'downloaded_image';
  
      // Append the link to the document
      document.body.appendChild(link);
  
      // Trigger a click event on the link
      link.click();
  
      // Remove the link from the document
      document.body.removeChild(link);
  
      // Revoke the Blob URL to free up resources
      URL.revokeObjectURL(blobUrl);
    } catch (error) {
      console.error('Error downloading image:', error);
    }
  }