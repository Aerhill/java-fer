package hr.fer.zemris.java.simplecomp;

import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * Program koji služi kao simulator za izvođenje asemblerskog koda u našoj
 * implementaciji računala koji je predan kao argument komandne linije.
 * 
 * @author Ante Spajic
 *
 */
public class Simulator {

	/**
	 * Ulazna točka u program. Program prima jedan argument sa komandne linije a
	 * to je staza do asemblerskog koda koji treba izvesti.
	 * 
	 * @param args
	 *            staza do datoteke koja sadrži asemblerski kod koji želimo
	 *            izvesti
	 * @throws Exception
	 *             Baca se iznimka ukoliko u kodu dođe do neočekivane greške
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("You must enter a path to assembler program as an argument");
			System.exit(1);
		}
		String path = args[0];
		if (!Files.exists(Paths.get(path))) {
			System.out.println("File does not exist");
			System.exit(1);
		}
		// Stvori računalo s 256 memorijskih lokacija i 16 registara
		Computer comp = new ComputerImpl(256, 16);
		// Stvori objekt koji zna stvarati primjerke instrukcija
		InstructionCreator creator = new InstructionCreatorImpl("hr.fer.zemris.java.simplecomp.impl.instructions");
		// Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
		// uporabom predanog objekta za stvaranje instrukcija
		if (path.trim().isEmpty()) {
			throw new IllegalArgumentException("Invalid path to location with assembler program");
		}
		ProgramParser.parse(path, comp, creator);
		// Stvori izvršnu jedinicu
		ExecutionUnit exec = new ExecutionUnitImpl();
		// Izvedi program
		exec.go(comp);
	}
}
