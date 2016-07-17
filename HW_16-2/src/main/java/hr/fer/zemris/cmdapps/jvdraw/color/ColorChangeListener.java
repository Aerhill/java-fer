package hr.fer.zemris.cmdapps.jvdraw.color;

import java.awt.Color;

/**
 * Interface used for classes that have to listen to {@link IColorProvider} to act on it.
 * 
 * @author Ante Spajić
 * 
 */
public interface ColorChangeListener {

    /**
     * This method will be called when any {@link IColorProvider} that this {@link ColorChangeListener} is listening to
     * changes it's color.
     * 
     * @param source source that changed it's color
     * @param oldColor color that was previously source's color
     * @param newColor new color
     */
    public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);

}
