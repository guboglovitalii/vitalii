package lesson_N3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CountSymbols {
    public static void main(String[] args) throws FileNotFoundException {

        Map<String, Integer> wordCount = new TreeMap<>();
        Scanner scanner = new Scanner(new File("file_l3.txt"));

        scanner.useDelimiter("[\\s.,;:!?()\\[\\]\"'-]+");

        while (scanner.hasNext()) {
            String word = scanner.next().toLowerCase();

            if (word.isEmpty()) continue;

            if (wordCount.containsKey(word)) {
                wordCount.put(word, wordCount.get(word) + 1);
            } else {
                wordCount.put(word, 1);
            }
        }

        scanner.close();

        System.out.println("Статистика слов (алфавитный порядок):");

        for (String word : wordCount.keySet()) {
            System.out.println(word + " -> " + wordCount.get(word));
        }

        int maxCount = 0;

        for (Integer count : wordCount.values()) {
            if (count > maxCount) {
                maxCount = count;
            }
        }

        System.out.println("\nСлова, встречающиеся чаще всего (" + maxCount + " раз):");

        for (String word : wordCount.keySet()) {
            if (wordCount.get(word) == maxCount) {
                System.out.println(word);
            }
        }
    }
}