package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * The Class BarChart that is a model for {@link BarChartComponent} used to
 * represent bar charts and hold their data..
 *
 * @author Ante Spajic
 */
public class BarChart {

	/** 'Bars' of this chart. */
	private List<XYValue> values;

	/** Description of values on x Axis. */
	private String xAxisDesc;

	/** Description of values on y Axis. */
	private String yAxisDesc;

	/** Highest value on Y axis. */
	private int maxY;

	/** Lowest value on Y axis. */
	private int minY;

	/** Y axis step value. */
	private int step;

	/**
	 * Instantiates a new bar chart.
	 *
	 * @param values
	 *            {@link #values}
	 * @param xAxisDesc
	 *            {@link #xAxisDesc}
	 * @param yAxisDesc
	 *            {@link #yAxisDesc}
	 * @param maxY
	 *            {@link #maxY}
	 * @param minY
	 *            {@link #minY}
	 * @param step
	 *            {@link #step}
	 */
	public BarChart(List<XYValue> values, String xAxisDesc, String yAxisDesc, int maxY, int minY, int step) {
		super();
		this.values = values;
		this.xAxisDesc = xAxisDesc;
		this.yAxisDesc = yAxisDesc;
		this.maxY = maxY;
		this.minY = minY;
		this.step = step;
	}

	/**
	 * Gets the values.
	 *
	 * @return the values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Gets the x axis desc.
	 *
	 * @return the x axis desc
	 */
	public String getxAxisDesc() {
		return xAxisDesc;
	}

	/**
	 * Gets the y axis desc.
	 *
	 * @return the y axis desc
	 */
	public String getyAxisDesc() {
		return yAxisDesc;
	}

	/**
	 * Gets the max y.
	 *
	 * @return the max y
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Gets the min y.
	 *
	 * @return the min y
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Gets the step.
	 *
	 * @return the step
	 */
	public int getStep() {
		return step;
	}

}
