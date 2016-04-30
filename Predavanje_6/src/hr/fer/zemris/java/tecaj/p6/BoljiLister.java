package hr.fer.zemris.java.tecaj.p6;

import java.io.File;

public class BoljiLister {

	private static class Ispis implements ObradaStabla {
		private int level;

		@Override
		public void ulazimUDirektorij(File dir) {
			ispisi(dir);
			level++;
		}

		@Override
		public void izlazimIzDirektorija(File dir) {
			level--;
		}

		@Override
		public void gledamDatoteku(File f) {
			ispisi(f);
		}
		
		private void ispisi(File file) {
			if (level == 0) {
				System.out.println(file.getAbsolutePath());
			} else {
				System.out.printf("%"+(2*level)+"s%s%n","", file.getName());
			}
		}
	}
	
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
		DirectoryTreeUtil.processDirectoryTree(parent, new Ispis());
	}

}
