package hr.fer.zemris.JIR2015.prob1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ImageFinder {

	public static void main(String[] args) throws IOException {
		Path p1 = Paths.get(args[0]);
		
		Set<Path> slike = new TreeSet<Path>(new MojKomparator().reversed());
		
		Files.walk(p1).forEach(file -> {
			if(file.toString().endsWith("png") || file.toString().endsWith("gif") || file.toString().endsWith("jpg")) {
				slike.add(file);
			}
		});
	
		List<Path> temp = new ArrayList<>(slike);
		
		temp = temp.subList(0,3);
		
		temp.forEach(slika -> {
			try {
				System.out.println(Files.size(slika));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		
	}

	private static class MojKomparator implements Comparator<Path> {

		public int compare(Path o1, Path o2) {
			try {
				long f1 = Files.size(o1);
				long f2 = Files.size(o2);
				return Long.compare(f1, f2);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 0;
		}
		
	}
	
}
