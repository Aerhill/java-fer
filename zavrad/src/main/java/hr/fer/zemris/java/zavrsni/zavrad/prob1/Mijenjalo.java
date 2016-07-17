package hr.fer.zemris.java.zavrsni.zavrad.prob1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mijenjalo {

	private static List<String> textovi = new ArrayList<>();
	
	private static Map<String,String> txt2 = new HashMap<>();
	
	public static void main(String[] args) throws IOException {
		
		if (args.length != 3) {
			System.out.println("Nije 3 arg");
			System.exit(1);
		}
		
		Path p1 = Paths.get(args[0]);
		Path p2 = Paths.get(args[1]);
		Path p3 = Paths.get(args[2]);
		
		Map<String,String> kljucevi = new HashMap<>();
		
		List<String> k = Files.readAllLines(p3);
		
		for (String string : k) {
			String[] pair = string.split("=");
			if (pair.length != 2) continue;
			kljucevi.put(pair[0].trim(), pair[1].trim());
		}
		
		Files.walk(p1).forEach(file -> {
			if(Files.isReadable(file)) {
				try {
					String data = new String(Files.readAllBytes(file));
					textovi.add(data);
					txt2.put(file.getFileName().toString(), data);
				} catch (Exception e) {}
			}
		});
		
		Map<String,String> novaMapa = new HashMap<>();
		
		for (Map.Entry<String, String> string : txt2.entrySet()) {
			String novi = string.getValue();
			for (Map.Entry<String, String> pair : kljucevi.entrySet()) {
				novi = novi.replaceAll("\\#\\$\\{.*"+ pair.getKey() +"\\s*\\}\\$\\#", pair.getValue());
				novi = novi.replaceAll("\\#\\$\\{.*"+ pair.getKey() +"\\s*\\n", pair.getValue() + "\n");
			}
			novi = novi.replaceAll("\\#\\$\\{.*\\}\\$\\#", "???");
			novi = novi.replaceAll("\\#\\$\\{.*\\n", "???\n");
			novaMapa.put(string.getKey(), novi);
		}
		
		if(!Files.exists(p2)) {
			try {
				Files.createDirectory(p2);
			} catch (IOException  e) {
				System.out.println("Ne ide stvaranje");
			}
		}
		
		for (Map.Entry<String, String> string : novaMapa.entrySet()) {
			Path p = Paths.get(p2.toString(), string.getKey());
			if (!Files.exists(p)) {
				try {
					Files.createFile(p);
					System.out.println("Hocu ispisat " + string.getValue());
					Files.write(p, string.getValue().getBytes());
				} catch (IOException e) {
					System.out.println("Ne ide ni vamo stvaranje");
				}
			}
		}
	}
}
