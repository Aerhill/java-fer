package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Collections;

import javax.swing.JComponent;

/**
 * Component used to view content stored in {@link BarChart} data model.
 * 
 * @author Ante Spajic
 *
 */
public class BarChartComponent extends JComponent {

	/** The Constant INSET_RIGHT used for right inset. */
	private static final int INSET_RIGHT = 12;

	/** The Constant INSET_TOP used for top inset. */
	private static final int INSET_TOP = 12;

	/** The Constant DENTS used for dents on axes. */
	private static final int DENTS = 6;

	/** The Constant GRID_COLOR. */
	private static final Color GRID_COLOR = Color.decode("#d3d3d3");

	/** The Constant BAR_COLOR. */
	private static final Color BAR_COLOR = Color.decode("#F47747");

	/** generated UID. */
	private static final long serialVersionUID = -2290688286165650352L;

	/** chart that this component displays. */
	private BarChart chart;

	/** distance from edge of window. */
	private int leftGap;

	/** distance from bottom of the window. */
	private int bottomGap;

	/**
	 * Constructor for this component that takes in a BarChart instance to be
	 * drawn in this component.
	 * 
	 * @param chart
	 *            {@link BarChart} instance to be drawn
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		Dimension d = getSize();

		Collections.sort(chart.getValues());

		int actualWidth = d.width - INSET_RIGHT;
		int actualHeight = d.height - INSET_TOP;

		int cols = chart.getValues().size();
		int rows = Double.valueOf(Math.ceil((chart.getMaxY() - chart.getMinY()) / (1.0 * chart.getStep()))).intValue();

		leftGap = actualWidth / cols;
		bottomGap = actualHeight / rows;

		int columnWidth = (actualWidth - leftGap - INSET_RIGHT) / cols;
		int rowHeight = (actualHeight - bottomGap - INSET_TOP) / rows;

		int maxVertical = actualHeight - bottomGap - rows * rowHeight;
		int maxHorizontal = leftGap + cols * columnWidth;

		graphics.setFont(new Font("Helvetica", Font.BOLD, 15));
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, d.width, d.height);

		drawGrid(graphics, maxVertical, maxHorizontal, columnWidth, cols, rows, rowHeight, actualHeight);
		drawAxis(graphics, leftGap - DENTS, actualHeight - bottomGap, actualWidth + INSET_RIGHT,
				actualHeight - bottomGap);
		drawAxis(graphics, leftGap, actualHeight - bottomGap + DENTS, leftGap, 0);
		drawBars(graphics, actualHeight, rowHeight, columnWidth, cols);
		writeAxisDescription(graphics, cols, columnWidth, actualHeight);

	}

	/**
	 * Draws the grid of the chart.
	 *
	 * @param graphics
	 *            the graphics
	 * @param maxVertical
	 *            the max vertical
	 * @param maxHorizontal
	 *            the max horizontal
	 * @param columnWidth
	 *            the column width
	 * @param cols
	 *            the cols
	 * @param rows
	 *            the rows
	 * @param rowHeight
	 *            the row height
	 * @param actualHeight
	 *            the actual height
	 */
	private void drawGrid(Graphics2D graphics, int maxVertical, int maxHorizontal, int columnWidth, int cols, int rows,
			int rowHeight, int actualHeight) {
		// Drawing horizontal lines and appropriate values along Y axis.
		int minValue = chart.getMinY();
		int step = chart.getStep();

		for (int j = actualHeight - bottomGap, z = 0; z <= rows; j -= rowHeight, z++) {
			graphics.setColor(Color.DARK_GRAY);
			graphics.drawLine(leftGap - DENTS, j, leftGap, j);
			graphics.setColor(GRID_COLOR);
			graphics.drawLine(leftGap, j, maxHorizontal + DENTS, j);
			String number = String.valueOf(minValue++ * step);
			graphics.setColor(Color.BLACK);
			graphics.drawString(number, leftGap - DENTS - leftGap / 3, j + DENTS);
		}

		// Drawing vertical lines and appropriate values along X axis.
		for (int i = leftGap, z = 0; z < cols; i += columnWidth, z++) {
			graphics.setColor(Color.DARK_GRAY);
			graphics.drawLine(i, actualHeight - bottomGap, i, actualHeight - bottomGap + DENTS);
			graphics.setColor(GRID_COLOR);
			graphics.drawLine(i, maxVertical - DENTS, i, actualHeight - bottomGap);
			graphics.setColor(Color.BLACK);
			String number = String.valueOf(chart.getValues().get(z).getX());
			graphics.drawString(number, i + (columnWidth / 2) - number.length() / 2,
					actualHeight - 2 * (bottomGap / 3));
		}

	}

	/**
	 * Writes the appropriate axis description.
	 *
	 * @param graphics
	 *            the graphics
	 * @param cols
	 *            the cols
	 * @param columnWidth
	 *            the column width
	 * @param actualHeight
	 *            the actual height
	 */
	private void writeAxisDescription(Graphics2D graphics, int cols, int columnWidth, int actualHeight) {
		graphics.setColor(Color.BLACK);
		// Drawing X axis description.
		graphics.setFont(new Font("Helvetica", Font.PLAIN, 13));
		graphics.drawString(chart.getxAxisDesc(), cols * columnWidth / 2, actualHeight - (bottomGap / 5));

		// Drawing Y axis description.
		Graphics2D graphics2 = (Graphics2D) graphics.create();
		AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
		graphics2.setTransform(at);
		graphics2.drawString(chart.getyAxisDesc(), -actualHeight / 2, leftGap / 3);
	}

	/**
	 * Draws the bars to the chart.
	 *
	 * @param graphics
	 *            the graphics
	 * @param actualHeight
	 *            the actual height
	 * @param rowHeight
	 *            the row height
	 * @param columnWidth
	 *            the column width
	 * @param cols
	 *            the cols
	 */
	private void drawBars(Graphics2D graphics, int actualHeight, int rowHeight, int columnWidth, int cols) {
		for (int i = 0, z = leftGap + 1; i < cols; i++, z += columnWidth) {
			int value = chart.getValues().get(i).getY();
			if (value > chart.getMaxY()) {
				value = chart.getMaxY();
			} else if (value < chart.getMinY()) {
				value = chart.getMinY();
			}
			int height = (value - chart.getMinY()) / chart.getStep() * rowHeight;
			graphics.setColor(BAR_COLOR);
			graphics.fillRect(z, actualHeight - bottomGap - height, columnWidth, height);

			graphics.setColor(Color.WHITE);
			graphics.drawLine(z, actualHeight - bottomGap - height, z + columnWidth, actualHeight - bottomGap - height);
			graphics.drawLine(z + columnWidth - 1, actualHeight - bottomGap - height, z + columnWidth - 1,
					actualHeight - bottomGap);
		}
	}

	/** Size of arrows point. */
	private static final int ARROW_SIZE = 4;

	/**
	 * Draws pointed arrow from first to second point.
	 * 
	 * @param g1
	 *            graphics used for draw/paint job
	 * @param x1
	 *            x coordinate of first point
	 * @param y1
	 *            y coordinate of first point
	 * @param x2
	 *            x coordinate of second point
	 * @param y2
	 *            y coordinate of second point
	 */
	private void drawAxis(Graphics g1, int x1, int y1, int x2, int y2) {
		Graphics2D g = (Graphics2D) g1.create();
		g.setColor(GRID_COLOR);
		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);
		g.drawLine(0, 0, len, 0);
		g.fillPolygon(new int[] { len, len - ARROW_SIZE, len - ARROW_SIZE, len },
				new int[] { 0, -ARROW_SIZE, ARROW_SIZE, 0 }, 4);
	}
}
