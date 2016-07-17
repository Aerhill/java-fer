package hr.fer.zemris.cmdapps.jvdraw.models;

/**
 * Listeners to {@link DrawingModel}.
 * 
 * @author Ante Spajić
 * 
 */
public interface DrawingModelListener {

    /**
     * Called when there were objects added on some interval
     * 
     * @param source source on which objects were added
     * @param index0 index0
     * @param index1 index1
     */
    public void objectsAdded(DrawingModel source, int index0, int index1);

    /**
     * Called when there were objects removed on somme interval
     * 
     * @param source source on which objects were removed
     * @param index0 index0
     * @param index1 index1
     */
    public void objectsRemoved(DrawingModel source, int index0, int index1);

    /**
     * Called when there were objects changed on some intervalF
     * 
     * @param source source on which objects were changed
     * @param index0 index0
     * @param index1 index1
     */
    public void objectsChanged(DrawingModel source, int index0, int index1);

}
