package hr.fer.zemris.cmdapps.jvdraw.models;

import java.awt.Rectangle;

import hr.fer.zemris.cmdapps.jvdraw.drawing.objects.GeometricalObject;


/**
 * Models that provide objects.
 * 
 * @author Ante Spajić
 * 
 */
public interface DrawingModel {

    /**
     * Number of objects.
     * 
     * @return number of objects
     */
    public int getSize();

    /**
     * Returns the object at given index.
     * 
     * @param index object's index
     * @return object at given index
     */
    public GeometricalObject getObject(int index);

    /**
     * Adds object to a list of objects.
     * 
     * @param obj object to add
     */
    public void add(GeometricalObject obj);

    /**
     * Removes the object from the object list
     * 
     * @param obj object to remove
     */
    public void remove(GeometricalObject obj);

    /**
     * Clears the model.
     */
    public void clear();

    /**
     * Adds the listener to the listeners list.
     * 
     * @param l listener to add
     */
    public void addDrawingModelListener(DrawingModelListener l);

    /**
     * Removes the listener from the listeners list.
     * 
     * @param l listener to remove
     */
    public void removeDrawingModelListener(DrawingModelListener l);

    /**
     * Returns a bounding box of this model. It is the smallest rectangle in which all components can be drawn.
     * 
     * @return bounding box of this model
     */
    public Rectangle getBoundingBox();

}
