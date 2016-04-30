package hr.fer.zemris.java.tecaj.p6;

import java.io.File;

public class DirectoryTreeUtil {

	public static void processDirectoryTree(File dir, ObradaStabla o) {
		o.ulazimUDirektorij(dir);

		File[] children = dir.listFiles();
		if (children != null) {
			for (File f : children) {
				if (f.isFile()) {
					o.gledamDatoteku(f);
				} else if (f.isDirectory()) {
					processDirectoryTree(f, o);
				}
			}
		}

		o.izlazimIzDirektorija(dir);
	}
}
