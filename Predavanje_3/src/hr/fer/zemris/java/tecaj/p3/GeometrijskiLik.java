package hr.fer.zemris.java.tecaj.p3;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public abstract class GeometrijskiLik implements SadrziocTocaka {
	
	public void popuniLik(Slika slika) {
		for (int y = 0, sirina = slika.getSirina(), visina = slika.getVisina(); y < visina; y++) {
			for (int x = 0; x < sirina; x++) {
				if (sadrziTocku(x, y)) {
					slika.upaliTocku(x, y);
				}
			}
		}
	}
}
