package hr.fer.zemris.java.tecaj_1;

import hr.fer.zemris.java.poruke.Einstein;

public class Citati {

    public static void main(String[] args) {
        System.out.printf("Raspolažemo s %d citata%n", Einstein.getQuotesCount());
        System.out.println();

        System.out.println("Slučajni citat: " + Einstein.getRandomQuote());
        System.out.println();

        for(int i = 0, n= Einstein.getQuotesCount(); i < n ; i++){
                System.out.println(Einstein.getQuote(i));
        }
        System.out.println();

        for(String e : Einstein.allQuotes()) {
            System.out.println(e);
        }
    }
}
