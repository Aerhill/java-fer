package hr.fer.zemris.java.tecaj_7.dretve1;

import java.util.Random;

public class PIUtil {

	public static int testNumberOfPointsInCircle(int numberOfSamples, Random rnd){
		int nin = 0;
		for(int i = 0; i < numberOfSamples; i++){
			double x = rnd.nextDouble()*2 - 1;
			double y = rnd.nextDouble()*2 - 1;
			if(x*x + y*y <= 1.0) nin++;
		}
		
		return nin;
	}
}
