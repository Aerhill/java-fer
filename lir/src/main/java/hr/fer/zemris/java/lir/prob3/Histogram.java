package hr.fer.zemris.java.lir.prob3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;


public class Histogram {

	private static final int INSET_RIGHT = 12;

	private static final int INSET_TOP = 12;

	private static final int DENTS = 6;

	private static final Color GRID_COLOR = Color.decode("#d3d3d3");

	private static final Color BAR_COLOR = Color.decode("#F47747");


	private int leftGap;

	private int bottomGap;
	
	private double omC;
	private double omR;
	private double omT;
	
	
	public Histogram(double omC, double omR, double omT) {
		this.omR = omR;
		this.omC = omC;
		this.omT = omT;
	
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		Dimension d = new Dimension(300,300);

		//Collections.sort(chart.getValues());

		int actualWidth = d.width - INSET_RIGHT;
		int actualHeight = d.height - INSET_TOP;

		int cols = 3;
		int rows = 10;

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

	
	private void drawGrid(Graphics2D graphics, int maxVertical, int maxHorizontal, int columnWidth, int cols, int rows,
			int rowHeight, int actualHeight) {
		// Drawing horizontal lines and appropriate values along Y axis.
		int minValue = 0;
		int step = 10;

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
			String str = "";
			if (z == 0) {
				str = "C";
			} else if (z== 1) {
				str = "R";
			} else {
				str = "T";
			}
			graphics.drawString(str, i + (columnWidth / 2) - str.length() / 2,
					actualHeight - 2 * (bottomGap / 4));
		}

	}


	private void writeAxisDescription(Graphics2D graphics, int cols, int columnWidth, int actualHeight) {
		graphics.setColor(Color.BLACK);
		// Drawing X axis description.
		graphics.setFont(new Font("Helvetica", Font.PLAIN, 13));

		// Drawing Y axis description.
		Graphics2D graphics2 = (Graphics2D) graphics.create();
		AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
		graphics2.setTransform(at);
		graphics2.drawString("UDIO", -actualHeight / 2, leftGap / 3);
	}

	
	private void drawBars(Graphics2D graphics, int actualHeight, int rowHeight, int columnWidth, int cols) {
		for (int i = 0, z = leftGap + 1; i < cols; i++, z += columnWidth) {
			double value = 0;
			if (i == 0) {
				value = omC * 100;
			} else if (i == 1) {
				value = omR * 100;
			} else {
				value = omT * 100;
			}
			
			int height = new Double(value / 10 * (double)rowHeight).intValue();
			graphics.setColor(BAR_COLOR);
			graphics.fillRect(z, actualHeight - bottomGap - height, columnWidth, height);

			graphics.setColor(Color.WHITE);
			graphics.drawLine(z, actualHeight - bottomGap - height, z + columnWidth, actualHeight - bottomGap - height);
			graphics.drawLine(z + columnWidth - 1, actualHeight - bottomGap - height, z + columnWidth - 1,
					actualHeight - bottomGap);
		}
	}

	private static final int ARROW_SIZE = 4;

	
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
