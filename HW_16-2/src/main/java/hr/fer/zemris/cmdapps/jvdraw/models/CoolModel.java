package hr.fer.zemris.cmdapps.jvdraw.models;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.cmdapps.jvdraw.JVDraw;
import hr.fer.zemris.cmdapps.jvdraw.drawing.objects.GeometricalObject;


/**
 * Implementation of {@link DrawingModel} needed for {@link JVDraw}
 * 
 * @author Ante Spajić
 * 
 */
public class CoolModel implements DrawingModel {

    private List<GeometricalObject> objects = new ArrayList<>();
    private List<DrawingModelListener> listeners = new ArrayList<>();

    @Override
    public int getSize() {
        return objects.size();
    }

    @Override
    public void add(GeometricalObject obj) {
        int index = objects.size();
        objects.add(obj);
        for (DrawingModelListener l : listeners) {
            l.objectsAdded(this, index, index);
        }
    }

    @Override
    public void remove(GeometricalObject obj) {
        int index = objects.indexOf(obj);
        if (index == -1) return;
        objects.remove(index);
        for (DrawingModelListener l : listeners) {
            l.objectsRemoved(this, index, index);
        }
    }

    @Override
    public GeometricalObject getObject(int index) {
        return objects.get(index);
    }

    @Override
    public void clear() {
        for (DrawingModelListener l : listeners) {
            l.objectsRemoved(this, 0, objects.size());
        }
        objects = new ArrayList<>();
    }

    @Override
    public void addDrawingModelListener(DrawingModelListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    @Override
    public void removeDrawingModelListener(DrawingModelListener l) {
        listeners.remove(l);
    }

    @Override
    public Rectangle getBoundingBox() {
        int left = Integer.MAX_VALUE;
        int top = Integer.MAX_VALUE;
        int right = 0;
        int bottom = 0;

        for (GeometricalObject o : objects) {
            Rectangle r = o.getBounds();
            left = (left > r.x ? r.x : left);
            right = (right < r.x + r.width ? r.x + r.width : right);
            top = (top > r.y ? r.y : top);
            bottom = (bottom < r.y + r.height ? r.y + r.height : bottom);
        }

        return new Rectangle(left, top, right - left, bottom - top);
    }
}
