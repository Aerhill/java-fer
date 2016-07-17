package hr.fer.zemris.java.zi.prob1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Lister {

	private static long size = 0;
	private static int numberOfFound = 0;
	
	public static void main(String[] args) throws IOException {
	
		if(args.length != 1) {
			System.out.println("Očekivao 1 arg, stazu do direktorija");
			System.exit(1);
		}
		
		Path p = Paths.get(args[0]);
		
		Files.walk(p).forEach(file -> {
			if(file.getFileName().toString().endsWith(".txt")) {
				try {
					System.out.println(file.toAbsolutePath().toString());
					System.out.println(String.valueOf(Files.size(file)) + " bytes ");
					numberOfFound++;
					size += Files.size(file);
				} catch (IOException e) {}
			}
		});
		if(size != 0 && numberOfFound != 0) {
			System.out.println("Ukupna veličina " + size + ", ukupan broj " + numberOfFound);
		} else {
			System.out.println("Nisam pronašao niti jednu .txt datoteku u zadanoj putanji");
		}
	}
	
}
