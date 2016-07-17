package hr.fer.zemris.cmdapps.jvdraw.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.cmdapps.jvdraw.JVDraw;


/**
 * Used for color changing in {@link JVDraw}.
 * 
 * @author Ante Spajić
 * 
 */
public class JColorArea extends JComponent implements IColorProvider {
	/**
	 * generated serial version uid
	 */
	private static final long serialVersionUID = -1078710408232931006L;
	
	/** Default size of this component is 20x20 */
    private static final Dimension DIM = new Dimension(20, 20);
    private static final int DEF_SIZE = 30;

    /**
     * Color that this component represents.
     */
    private Color color;

    /**
     * This is true if this color are is responsible for foreground color.
     */
    private boolean isForeground;

    /**
     * A list of listeners (observers).
     */
    private List<ColorChangeListener> listeners = new ArrayList<>();

    /**
     * When there's a click on this component, a {@link JColorChooser} is opened to choose a new color.
     */
    private MouseAdapter myMouse = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            Color c = JColorChooser.showDialog(JColorArea.this, "Choose a color", color);
            if (c != null) {
                setNewColor(c);
            }
        };
    };

    /**
     * Creates a new {@link JColorArea} with given color.
     * 
     * @param color initial color
     */
    public JColorArea(Color color, boolean isFgColor) {
        this.color = color;
        this.isForeground = isFgColor;
        addMouseListener(myMouse);
    }

    @Override
    public boolean isForegroundColor() {
        return isForeground;
    }

    /**
     * Used for color changing.
     * 
     * @param c new color to set
     */
    private void setNewColor(Color c) {
        for (ColorChangeListener l : listeners) {
            l.newColorSelected(this, color, c);
        }
        color = c;
        repaint();
    }

    @Override
    public Color getCurrentColor() {
        return color;
    }

    /**
     * Adds the given listener to the listeners list.
     * 
     * @param l listener to add
     */
    public void addColorChangeListener(ColorChangeListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    /**
     * Removes the given listener from listeners list.
     * 
     * @param l listener to remove
     */
    public void removeColorChangeListener(ColorChangeListener l) {
        listeners.remove(l);
    }

    /**
     * Paints this component in the center of it's 'space'.
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(color);
        Insets ins = getInsets();
        Dimension size = getSize();
        g.fillRect(ins.left + (size.width - (DIM.width)) / 2, ins.top + (size.height - (DIM.height)) / 2, DIM.width,
                DIM.height);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEF_SIZE, DEF_SIZE);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

}
