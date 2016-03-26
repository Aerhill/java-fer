package hr.fer.zemris.java.tecaj.hw1;

/**
 * 
 * @author Ante Spajic
 *
 */
public class HofstadterQ {

    /**
     * Metoda koja se poziva prilikom pokretanja programa. Argument komandne
     * linije je broj iz hofstadterovog reda.
     * 
     * @param args
     *            Element hofstadterovog reda
     */
    public static void main(String[] args) {
	if (args.length != 1) {
	    System.err.println("Invalid number of arguments");
	    return;
	}
	long broj = Long.parseLong(args[0]);
	if (broj > 0) {
	    System.out
		    .println("You requested calculation of "
			    + broj
			    + ". "
			    + "number of Hofstadter's Q-sequence. The requested number is "
			    + Hofstadter(broj) + ".");
	} else {
	    System.err
		    .println("Wrong arguments. The argument (number n) must be an postive integer.");
	    return;
	}
    }

    /**
     * Metoda koja racuna n-ti element Hofstadterovog reda
     * 
     * @param n
     *            Redni broj elementa koji zelimo
     * @return N-ti element hofstadterovog reda
     */
    private static long Hofstadter(long n) {
	if (n == 1 || n == 2) {
	    return 1;
	}
	return Hofstadter((n - Hofstadter(n - 1)))
		+ Hofstadter((n - Hofstadter(n - 2)));
    }
}
