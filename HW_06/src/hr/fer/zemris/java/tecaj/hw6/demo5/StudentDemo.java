package hr.fer.zemris.java.tecaj.hw6.demo5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Demo presents ways of manipulating student's data, provided from database -
 * in this case textual file, utilizing <tt>stream</tt> functionality.
 * 
 * @author Ante Spajic
 *
 */
public class StudentDemo {

	/**
	 * Entry point to a program.
	 * 
	 * @param args
	 *            Unused command line arguments.
	 */
	public static void main(String[] args) {

		List<String> lines;
		List<StudentRecord> records;
		try {
			lines = Files.readAllLines(Paths.get("./studenti.txt"), StandardCharsets.UTF_8);
			records = convert(lines);

			System.out.println("BROJ onih sa više od 25");
			long broj = records.stream().filter(s -> s.getBrojBodLV() + s.getBrojBodMI() + s.getBrojBodZI() > 25)
					.count();
			System.out.println(broj);

			System.out.println("BROJ ODLIKAŠA");
			long broj5 = records.stream().filter(s -> s.getOcjena() == 5).count();
			System.out.println(broj5);

			System.out.println("ODLIKAŠI");
			List<StudentRecord> odlikasi = records.stream().filter(s -> s.getOcjena() == 5)
					.collect(Collectors.toList());
			odlikasi.forEach(System.out::println);

			System.out.println("ODLIKAŠI SORTIRANI");
			List<StudentRecord> odlikasiSortirano = records.stream().filter(s -> s.getOcjena() == 5)
					.sorted((s1, s2) -> {
						return (int) -((s1.getBrojBodLV() + s1.getBrojBodMI() + s1.getBrojBodZI())
								- (s2.getBrojBodLV() + s2.getBrojBodMI() + s2.getBrojBodZI()));
					}).collect(Collectors.toList());
			odlikasiSortirano.forEach(System.out::println);

			System.out.println("NEPOLOZENI JMBAGOVI");
			List<String> nepolozeniJMBAGovi = records.stream().filter(s -> s.getOcjena() == 1).map(s -> s.getJmbag())
					.sorted().collect(Collectors.toList());
			System.out.println(nepolozeniJMBAGovi.size());
			nepolozeniJMBAGovi.forEach(System.out::println);

			System.out.println("MAPA PO OCJENAMA");
			Map<Integer, List<StudentRecord>> mapaPoOcjenama = records.stream()
					.collect(Collectors.groupingBy(StudentRecord::getOcjena));
			for (Map.Entry<Integer, List<StudentRecord>> entry : mapaPoOcjenama.entrySet()) {
				entry.getValue().forEach(System.out::println);
			}

			System.out.println("MAPA PO OCJENAMA");
			Map<Integer, Integer> mapaPoOcjenama2 = records.stream()
					.collect(Collectors.toMap(StudentRecord::getOcjena, v -> 1, (k, v) -> Integer.sum(k, 1)));
			for (Map.Entry<Integer, Integer> entry : mapaPoOcjenama2.entrySet()) {
				System.out.println("Ocjena: " + entry.getKey() + " broj: " + entry.getValue());
			}

			System.out.println("PROLAZ/NEPROLAZ");
			Map<Boolean, List<StudentRecord>> prolazNeprolaz = records.stream()
					.collect(Collectors.partitioningBy(s -> s.getOcjena() >= 2));
			for (Map.Entry<Boolean, List<StudentRecord>> entry : prolazNeprolaz.entrySet()) {
				entry.getValue().forEach(System.out::println);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Private static method analyzes all lines read from student database, and
	 * retrieves information in form of list of student records.
	 * 
	 * @param lines
	 *            lines read from database
	 * @return list of student records
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> ret = new ArrayList<>();
		for (String string : lines) {
			String[] cols = string.split("\\t+");
			ret.add(new StudentRecord(cols[0], cols[1], cols[2], Double.parseDouble(cols[3]),
					Double.parseDouble(cols[4]), Double.parseDouble(cols[5]), Integer.parseInt(cols[6])));
		}
		return ret;
	}

}
