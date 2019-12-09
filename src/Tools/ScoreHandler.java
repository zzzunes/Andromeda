package Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ScoreHandler {
	public static HashMap<String, Integer> readScores() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("scores.txt"));
		HashMap<String, Integer> scores = new HashMap<>();
		while (scanner.hasNextLine()) {
			String user = scanner.nextLine();
			Integer score = Integer.parseInt(scanner.nextLine());
			scores.put(user, score);
		}
		scanner.close();
		return scores;
	}

	public static void writeScores(HashMap<String, Integer> scores) throws IOException {
		scores = sortDescendingValues(scores);
		FileWriter writer = new FileWriter("scores.txt");
		for (Map.Entry<String, Integer> stringStringEntry : scores.entrySet()) {
			Map.Entry pair = stringStringEntry;
			writer.write(pair.getKey() + "\n");
			writer.write(pair.getValue() + "\n");
		}
		writer.close();
	}

	private static HashMap<String, Integer> sortDescendingValues(HashMap<String, Integer> scores) {
		List list = new LinkedList(scores.entrySet());
		Collections.sort(list, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				return -((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
			}
		});
		HashMap result = new LinkedHashMap<>();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}
