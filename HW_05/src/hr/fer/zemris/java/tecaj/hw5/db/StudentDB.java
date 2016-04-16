package hr.fer.zemris.java.tecaj.hw5.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Simple program that acts as simple database emulator that is read from a
 * locally stored database text file.
 * 
 * @author Ante Spajic
 *
 */
public class StudentDB {

	private static StudentDatabase database;

	/**
	 * Entry point to a program that takes no arguments.
	 * 
	 * @param args
	 *            Unused command line arguments.
	 */
	public static void main(String[] args) {

		try {
			List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
			database = new StudentDatabase(lines);
		} catch (IOException e) {
			System.err.println("Couldn't load database file");
			System.exit(1);
		} catch (IllegalArgumentException ex) {
			System.err.println(ex.getMessage());
			System.exit(1);
		}

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("> ");
			String line = sc.nextLine().trim();
			try {
				if (line.startsWith("query")) {
					print(database.filter(new QueryFilter(line)));
				} else if (line.startsWith("indexquery ")) {
					String[] query = line.replace("indexquery", "").trim().split("=");
					String jmbag = query[1].trim().replace("\"", "");
					if (query[0].trim().equals("jmbag") && isValidJmbag(jmbag)) {
						print(Collections.singletonList((database.forJMBAG(jmbag))));
					} else {
						System.out.println(
								"Invalid indexquery command structure, you can only query for jmbag which is a numeric string of length 10");
					}
				} else if (line.equals("exit")) {
					System.out.println("Goodbye!");
					break;
				} else {
					System.out.println("Invalid command: " + line);
				}
			} catch (IllegalArgumentException | UnsupportedOperationException ex) {
				System.out.println(ex.getMessage());
			} catch (Exception e) {
				System.out.println("Unexpected error has ocurred ");
				System.exit(1);
			}
		}
		sc.close();

	}

	/**
	 * Helper method that check if a jmbag is valid, this method only checks if
	 * its a number, it was not specified in a homework assignment but maybe it
	 * should be checked for length since jmbags usually have length of 10.
	 * 
	 * @param jmbag
	 *            Jmbag to have its validity checked.
	 * @return True if jmbag is valid, false otherwise.
	 */
	private static boolean isValidJmbag(String jmbag) {
		// maybe this is unnecessary but its a neat feature
		if (jmbag.length() != 10) {
			return false;
		}
		try {
			Long.parseLong(jmbag);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Helper method that prints a obtained records from database.
	 * 
	 * @param list
	 *            List of obtained records from a database.
	 */
	private static void print(List<StudentRecord> list) {
		if (list == null || list.isEmpty() || list.get(0) == null) {
			System.out.println("Records selected: 0");
			return;
		}

		int[] lenghts = { 0, 0, 0, 1 };
		for (StudentRecord student : list) {
			lenghts[0] = Math.max(lenghts[0], student.getJmbag().length());
			lenghts[1] = Math.max(lenghts[1], student.getLastName().length());
			lenghts[2] = Math.max(lenghts[2], student.getFirstName().length());
		}

		printFirstOrLastLine(lenghts);
		for (StudentRecord student : list) {
			StringBuilder forPrint = new StringBuilder();
			forPrint.append("|");
			forPrint.append(String.format(" %-" + lenghts[0] + "s |", student.getJmbag()));
			forPrint.append(String.format(" %-" + lenghts[1] + "s |", student.getLastName()));
			forPrint.append(String.format(" %-" + lenghts[2] + "s |", student.getFirstName()));
			forPrint.append(String.format(" %-" + lenghts[3] + "s |", student.getFinalGrade()));
			System.out.println(forPrint);
		}
		printFirstOrLastLine(lenghts);
		System.out.println("Records selected: " + list.size());
	}

	/**
	 * Helper method to print top and bottom of a table.
	 * 
	 * @param lenghts
	 *            Lengths for each column of a table.
	 */
	private static void printFirstOrLastLine(int[] lenghts) {
		System.out.print('+');
		for (Integer len : lenghts) {
			for (int i = 0; i < len + 2; i++) {
				System.out.print('=');
			}
			System.out.print('+');
		}
		System.out.print('\n');
	}
}
