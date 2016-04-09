package hr.fer.zemris.java.tecaj.pred4;

import java.util.Iterator;

public class Main {
	public static void main(String[] args) {
		SlijedParnihBrojeva spb = new SlijedParnihBrojeva(2, 10);
		Iterator<Integer> it = spb.iterator();
		while(it.hasNext()) {
			Integer element = it.next();
			System.out.println(element);
		}
		
		for(Integer element : spb) {
			System.out.println(element);
		}
		
		Iterator<Integer> it2 = spb.iterator();
		while(it2.hasNext()) {
			Integer element = it2.next();
			Iterator<Integer> it3 = spb.iterator();
			while(it3.hasNext()) {
				Integer element3 = it3.next();
				System.out.println(element + " - " + element3);
			}
		}
		
		
		Iterator<Integer> it4 = spb.iterator();
		it4.next();
		it4.forEachRemaining(System.out::println);
		
	}
}
