package hr.fer.zemris.java.gui.charts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * The Class that is used to demonstrate BarChart component.
 */
public class BarChartDemo extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5151434034409506855L;

	/**
	 * Instantiates a new bar chart window.
	 *
	 * @param lines
	 *            the lines from the loaded file with bar char description
	 * @param name
	 *            the name name of window
	 */
	public BarChartDemo(List<String> lines, String name) {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(name);
		setLocation(20, 20);
		setSize(500, 500);
		initGUI(lines);
	}

	/**
	 * Prepare xy values.
	 *
	 * @param string
	 *            the string
	 * @return the list
	 */
	private List<XYValue> prepareXYValues(String string) {
		String[] pairs = string.split("\\s+");
		List<XYValue> list = new ArrayList<>();
		for (int i = 0; i < pairs.length; i++) {
			String[] pair = pairs[i].split(",");
			int x = Integer.parseInt(pair[0]);
			int y = Integer.parseInt(pair[1]);
			list.add(new XYValue(x, y));
		}
		return list;
	}

	/**
	 * Initializes the graphical user interface with new BarChartComponent.
	 *
	 * @param lines
	 *            the lines
	 */
	private void initGUI(List<String> lines) {
		List<XYValue> values = null;
		String xAxisDesc = null;
		String yAxisDesc = null;
		int maxY = 0;
		int minY = 0;
		int step = 0;
		try {
			xAxisDesc = lines.get(0);
			yAxisDesc = lines.get(1);
			values = prepareXYValues(lines.get(2));
			minY = Integer.parseInt(lines.get(3));
			maxY = Integer.parseInt(lines.get(4));
			step = Integer.parseInt(lines.get(5));
		} catch (Exception e) {
			System.out.println("Invalid file format");
			System.exit(1);
		}
		BarChart chart = new BarChart(values, xAxisDesc, yAxisDesc, maxY, minY, step);
		add(new BarChartComponent(chart));
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Expected a single argument: path to file with information");
			System.exit(1);
		}
		String p = args[0].trim();
		if (p.isEmpty()) {
			System.out.println("Invalid filepath");
			System.exit(1);
		}
		Path path = Paths.get(p);
		if (Files.isDirectory(path)) {
			System.out.println("Expected a file not a directory");
			System.exit(1);
		}
		List<String> lines = Files.readAllLines(path);
		SwingUtilities.invokeLater(() -> new BarChartDemo(lines, path.toAbsolutePath().toString()).setVisible(true));
	}
}
