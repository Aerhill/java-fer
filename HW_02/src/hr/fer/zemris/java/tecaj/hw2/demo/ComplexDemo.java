package hr.fer.zemris.java.tecaj.hw2.demo;

import hr.fer.zemris.java.tecaj.hw2.ComplexNumber;

public class ComplexDemo {

    public static void main(String[] args) {

	ComplexNumber c1 = new ComplexNumber(2, 3);
	ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
	ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
	.div(c2).power(3).root(2)[1];
	System.out.println(c3);
	
	System.out.println("C1: "+ c1.toString()  + " Angle " + c1.getAngle() + " magnitude: " + c1.getMagnitude());
	System.out.println("C2: "+ c2.toString()  + " Angle " + c2.getAngle() + " magnitude: " + c2.getMagnitude());
	System.out.println("C3: "+ c3.toString()  + " Angle " + c3.getAngle() + " magnitude: " + c3.getMagnitude());
    }

}
