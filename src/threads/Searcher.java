package threads;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Searcher implements Runnable {

    private List<String> resultList;
    private String[] words;
    private String src;

    public Searcher(List<String> resultList, String[] words, String src) {
        this.resultList = resultList;
        this.words = words;
        this.src = src;
    }

    @Override
    public void run() {
        parse();
    }

    private void parse() {
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(src));
             Scanner scanner = new Scanner(inputStream)) {

            Pattern pattern = Pattern.compile("([^.!?]+[.!?]+)(.*)$");
            Matcher matcher;
            String sentence;
            while (scanner.hasNextLine()) {
                String text = scanner.nextLine();
                matcher = pattern.matcher(text);
                while (matcher.matches()) {
                    sentence = matcher.group(1);
                    text = matcher.group(2);
                    matcher = pattern.matcher(text);
                    handle(sentence, words);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // необходи будет оптимизировать алгоритм поиска, с ключевыми
    // словами из массива необходимо сравнивать слова из файлов,
    // а не наоборот, как сейчас. При этом слова из массива ложить в TreeSet
    private void handle(String sentence, String[] words) {
        String checked = " " + sentence.toLowerCase();
        for(String s : words) {
            if (checked.matches(".*\\s+" + s + "[,\\s!?.…]+.*")) {
                resultList.add(sentence.trim() + "\n");
                break;
            }
        }
    }
}
