package hr.fer.zemris.cmdapps.jvdraw.color;

import java.awt.Color;

/**
 * Classes that implement this interface must have methods for providing it's color. Color is provided to their
 * listeners.
 * 
 * @author Ante Spajić
 * 
 */
public interface IColorProvider {

    /**
     * Returns the current color of this {@link IColorProvider}.
     * 
     * @return current color
     */
    public Color getCurrentColor();

    /**
     * If the {@link IColorProvider} provides <i>foreground</i> color.
     * 
     * @return <code>true</code> if it provides foreground color
     */
    public boolean isForegroundColor();

}
