package hr.fer.zemris.java.tecaj.p6;

import java.io.File;

public class Lister {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Dear user");
			return;
		}
		
		File parent = new File(args[0]);
		if (!parent.isDirectory()) {
			System.out.println("must be dir");
			return;
		}
		System.out.println(parent.getAbsolutePath());
		listFiles(parent, 0);
	}

	private static void listFiles(File parent, int indentation) {
		File[] children = parent.listFiles();
		if (children != null) {
			for (File file : children) {
				System.out.printf("%"+(indentation)+"s%s%n", "", file.getName());
				if(file.isDirectory()) {
					System.out.print("  ");
					listFiles(file, indentation + 2);
				}
			}
		}
	}
}
