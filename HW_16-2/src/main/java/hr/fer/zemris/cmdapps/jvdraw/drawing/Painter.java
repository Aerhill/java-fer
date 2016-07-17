package hr.fer.zemris.cmdapps.jvdraw.drawing;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import hr.fer.zemris.cmdapps.jvdraw.color.ColorChangeListener;
import hr.fer.zemris.cmdapps.jvdraw.color.IColorProvider;
import hr.fer.zemris.cmdapps.jvdraw.color.JColorArea;
import hr.fer.zemris.cmdapps.jvdraw.drawing.objects.Circle;
import hr.fer.zemris.cmdapps.jvdraw.drawing.objects.FCircle;
import hr.fer.zemris.cmdapps.jvdraw.drawing.objects.GeometricalObject;
import hr.fer.zemris.cmdapps.jvdraw.drawing.objects.Line;
import hr.fer.zemris.cmdapps.jvdraw.models.DrawingModel;


/**
 * This class is used for drawing objects on {@link JDrawingCanvas}.
 * 
 * @author Ante Spajić
 * 
 */
public class Painter implements ColorChangeListener {

    public static final int LINE = 0;
    public static final int CIRCLE = 1;
    public static final int FCIRCLE = 2;

    private int status;
    private boolean fc;
    private JComponent parent;
    private DrawingModel model;
    private GeometricalObject obj;
    private Color[] colors = new Color[2];
    private boolean changed;

    /** coordinates of the first point */
    private Point start;

    /**
     * Creates a new drawer.
     * 
     * @param parent component on which drawer will add mouse event listeners
     * @param model model to which drawer will add drawn objects
     * @param foreground {@link JColorArea} that is responsible for foreground color
     * @param background {@link JColorArea} that is responsible for background color
     */
    public Painter(JComponent parent, DrawingModel model, JColorArea foreground,
            JColorArea background) {

        this.parent = parent;
        this.parent.addMouseListener(mouseClick);
        this.model = model;

        colors[0] = foreground.getCurrentColor();
        colors[1] = background.getCurrentColor();
        foreground.addColorChangeListener(this);
        background.addColorChangeListener(this);

        status = LINE;
        fc = true;
        changed = false;
    }

    /**
     * Returns if the drawing has changed since last save / at all.
     * 
     * @return <code>true</code> if it has changed
     */
    public boolean hasChanged() {
        return changed;
    }

    /**
     * Used to set change to false, when save was made.
     */
    public void fileSaved() {
        changed = false;
    }

    /**
     * Changes the status of the drawer.
     * 
     * @param status new status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Mouse listener that is added to the drawing canvas and is used to know when user clicks on the canvas.
     */
    private MouseListener mouseClick = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            if (fc) {
                fc = false;
                start = e.getPoint();
                setObjectToDraw();
                Painter.this.parent.addMouseMotionListener(mouseMove);
            } else {
                fc = true;
                Painter.this.parent.removeMouseMotionListener(mouseMove);
                changed = true;
                model.add(obj);
            }
        };
    };

    /**
     * Added to the canvas after a first click has been made, removed when second click is made.
     */
    private MouseMotionListener mouseMove = new MouseMotionAdapter() {
        public void mouseMoved(MouseEvent e) {
            obj.changeEndPoint(e.getPoint());
            parent.repaint();
            obj.paintComponent(parent.getGraphics());
        }
    };

    /**
     * Sets new object to draw depending on the <code>status</code>.
     */
    private void setObjectToDraw() {
        switch (status) {
            case LINE:
                obj = new Line(start.x, start.y, start.x, start.y, colors[0]);
                break;
            case CIRCLE:
                obj = new Circle(start.x, start.y, 0, colors[0]);
                break;
            default:
                obj = new FCircle(start.x, start.y, 0, colors[0], colors[1]);
        }
    };

    @Override
    public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
        colors[source.isForegroundColor() ? 0 : 1] = newColor;
    }

}
