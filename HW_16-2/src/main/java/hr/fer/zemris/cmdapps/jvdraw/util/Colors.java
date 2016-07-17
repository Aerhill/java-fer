package hr.fer.zemris.cmdapps.jvdraw.util;

import java.awt.Color;

/**
 * Color parser class that has 1 method that parses the argument string and
 * returns colors, very useful.
 * 
 * @author Ante Spajic
 *
 */
public class Colors {

	/**
	 * Returns a color that is parsed from a string. String should look like
	 * (r,g,b)
	 * 
	 * @param arg string to parse
	 * @return color represented by this string
	 */
	public static Color getColor(String arg) {
		String[] args = arg.substring(1, arg.length() - 1).split(",");
		if (args.length != 3) {
			return Color.BLACK;
		}
		int r = Integer.parseInt(args[0]);
		int g = Integer.parseInt(args[1]);
		int b = Integer.parseInt(args[2]);

		return new Color(r, g, b);
	}

}
