package hr.fer.zemris.java.lir.prob1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Racunalo {

	public static void main(String[] args) throws IOException {

		Path podaci = Paths.get(args[0]);
		Path odrediste = Paths.get(args[1]);

		List<MyFile> allFiles = new ArrayList<>();
		Files.walk(podaci).forEach(file -> {
			if (Files.isRegularFile(file)) {
				allFiles.add(calculate(file));
			}
		});

		if (!Files.exists(odrediste)) {
			Files.createDirectory(odrediste);
		}
		for (MyFile myFile : allFiles) {
			myFile.path = myFile.path.substring(podaci.toString().length()+1);
			Path p = Paths.get(odrediste.toString(), myFile.path);
			Path parent = p.getParent();
			if(!Files.exists(parent)) {
				try {
					Files.createDirectories(parent);
					System.out.println("stvorio sam parent");
				} catch (IOException e) {
					System.out.println("Ajde govno");
				}
				
			}
			if (!Files.exists(p)) {
				try {
					Files.createFile(p);
					DecimalFormat df = new DecimalFormat("#.###");
					String string = "C = " + df.format(myFile.ukC) + "\nR = " + df.format(myFile.ukR) + "\nT = "
							+ df.format(myFile.ukT);
					Files.write(p, string.getBytes());
				} catch (IOException e) {
					System.out.println("Ne ide ni vamo stvaranje");
				}
			}
		}

	}

	
	
	public static MyFile calculate(Path file) {
		List<String> lines;
		MyFile f = new MyFile();
		f.path = file.toFile().toString();
		//System.out.println(f.path);
		try {
			lines = Files.readAllLines(file);
			for (String s : lines) {
				String[] triple = s.split("\\s");
				switch (triple[0].toUpperCase()) {
				case "R":
					int h = Integer.parseInt(triple[2]);
					int w = Integer.parseInt(triple[1]);
					Rectangle r = new Rectangle(h, w);
					f.ukR += r.getPovrsina();
					break;
				case "C":
					int rad = Integer.parseInt(triple[1]);
					Circle c = new Circle(rad);
					f.ukC += c.getPovrsina();
					break;
				case "T":
					int hypo = Integer.parseInt(triple[1]);
					int kat = Integer.parseInt(triple[2]);
					Triangle t = new Triangle(hypo, kat);
					f.ukT += t.getPovrsina();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}



	public static class MyFile {

		private String path;
		private double ukC;
		private double ukR;
		private double ukT;
		
		
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public double getUkC() {
			return ukC;
		}
		public void setUkC(double ukC) {
			this.ukC = ukC;
		}
		public double getUkR() {
			return ukR;
		}
		public void setUkR(double ukR) {
			this.ukR = ukR;
		}
		public double getUkT() {
			return ukT;
		}
		public void setUkT(double ukT) {
			this.ukT = ukT;
		}
		
		public double ukupna() {
			return ukT + ukC + ukR;
		}
	}

}
