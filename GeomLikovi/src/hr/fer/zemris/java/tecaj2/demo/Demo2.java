package hr.fer.zemris.java.tecaj2.demo;

import hr.fer.zemris.java.tecaj2.GeometrijskiLik;
import hr.fer.zemris.java.tecaj2.Pravokutnik;

public class Demo2 {

	public static void main(String[] args) {
		Pravokutnik p1 = new Pravokutnik("ime1", 1, 1, 5, 5);
		Pravokutnik p2 = p1;
		GeometrijskiLik l3 = p1;
		
		System.out.println("Povrsina = " + p1.getPovrsina());
		System.out.println("Povrsina = " + p2.getPovrsina());
		System.out.println("Povrsina = " + l3.getPovrsina());
		
		System.out.println("Ime = " + l3.getIme());
		System.out.println("Ime = " + p1.getIme());
		
	}

}
