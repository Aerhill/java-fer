package hr.fer.zemris.java.tecaj.hw1;

public class HofstadterQ {

	public static void main(String[] args) {
		if(args.length != 1){
			System.err.println("Invalid number of arguments");
			return;
		}
		int broj = Integer.parseInt(args[0]);
		if( broj > 0 ) {
			System.out.println("You requested calculation of "+ broj + ". "
					+ "number of Hofstadter's Q-sequence. The requested number is " + Hofstadter(broj) + ".");
		} else {
			System.err.println("Wrong arguments. The argument (number n) must be an postive integer.");
			return;
		}
	}

	private static long Hofstadter(int n) {
		long Q[] = new long[n+1];
		Q[1] = Q[2] = 1;
		for(int i = 3; i <= n; i++) {
			Q[i] = Q[(int) (i - Q[i - 1])] + Q[(int) (i - Q[i - 2])];
		}
		return Q[n];
	}

}
