package hr.fer.zemris.cmdapps.jvdraw.drawing.objects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.cmdapps.jvdraw.JVDraw;
import hr.fer.zemris.cmdapps.jvdraw.util.Colors;


/**
 * Used to draw lines in {@link JVDraw}.
 * 
 * @author Ante Spajić
 * 
 */
public class Line extends GeometricalObject {

	private static final long serialVersionUID = -3773143819931472198L;
	private int sxCor;
    private int syCor;
    private int exCor;
    private int eyCor;
    private Color color;
    
    private JTextField sxCorTF;
    private JTextField syCorTF;
    private JTextField exCorTF;
    private JTextField eyCorTF;
    private JTextField colorTF;
    
    /**
     * Creates a new drawable line
     * 
     * @param sxCor starting x-coor
     * @param syCor starting y-coor
     * @param exCor ending x-coor
     * @param eyCor ending y-coor
     * @param color color of the line
     */
    public Line(int sxCor, int syCor, int exCor, int eyCor, Color color) {
        this.sxCor = sxCor;
        this.syCor = syCor;
        this.exCor = exCor;
        this.eyCor = eyCor;
        this.color = color;
    }

    /**
     * Reads from text fields to update the line.
     */
    @Override
    public void updateObject(JComponent comp) {
        sxCor = Integer.parseInt(sxCorTF.getText());
        syCor = Integer.parseInt(syCorTF.getText());
        exCor = Integer.parseInt(exCorTF.getText());
        eyCor = Integer.parseInt(eyCorTF.getText());
        color = Colors.getColor(colorTF.getText());
        comp.repaint();
    }

    /**
     * Changes the endpoint of the line.
     */
    @Override
    public void changeEndPoint(Point ending) {
        exCor = ending.x;
        eyCor = ending.y;
    }

    /**
     * Returns the bounds of this line.
     */
    @Override
    public Rectangle getBounds() {
        Point p = new Point(Math.min(sxCor, exCor), Math.min(syCor, eyCor));
        Dimension d = new Dimension(Math.abs(sxCor - exCor), Math.abs(syCor - eyCor));
        return new Rectangle(p, d);
    }

    @Override
    public void drawShifted(Graphics g, int x, int y) {
        drawLine(g, color, sxCor - x, syCor - y, exCor - x, eyCor - y);
    }

    /**
     * Draws a colored line.
     */
    @Override
    public void paintComponent(Graphics g) {
        drawLine(g, color, sxCor, syCor, exCor, eyCor);

    }

    /**
     * Draws a line in given graphics with given color.
     * 
     * @param g graphics used
     * @param c color of the line
     * @param sx starting x
     * @param sy starting y
     * @param ex ending x
     * @param ey ending y
     */
    private void drawLine(Graphics g, Color c, int sx, int sy, int ex, int ey) {
        g.setColor(c);
        g.drawLine(sx, sy, ex, ey);
    }

    @Override
    public JPanel createMessage() {

        JPanel main = new JPanel(new BorderLayout());
        JPanel left = new JPanel(new GridLayout(5, 1));
        JPanel right = new JPanel(new GridLayout(5, 1));

        main.add(left, BorderLayout.LINE_START);
        main.add(right, BorderLayout.CENTER);

        left.add(new JLabel("Start x-coor: "));
        left.add(new JLabel("Start y-coor: "));
        left.add(new JLabel("End x-coor: "));
        left.add(new JLabel("End y-coor: "));
        left.add(new JLabel("Color (r,g,b): "));

        sxCorTF = new JTextField();
        syCorTF = new JTextField();
        exCorTF = new JTextField();
        eyCorTF = new JTextField();
        colorTF = new JTextField();

        right.add(sxCorTF);
        right.add(syCorTF);
        right.add(exCorTF);
        right.add(eyCorTF);
        right.add(colorTF);

        return main;
    }

    @Override
    public String toString() {
        return "LINE " + sxCor + " " + syCor + " " + exCor + " " + eyCor + " " + color.getRed() + " "
                + color.getGreen() + " " + color.getBlue();
    }
}
