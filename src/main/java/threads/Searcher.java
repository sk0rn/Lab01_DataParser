package threads;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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
        try {
            parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parse() throws IOException {
        Files.lines(Paths.get(src))
                .flatMap(Pattern.compile("((?<=\\.)|(?<=\\?)|(?<=!))")::splitAsStream).
                forEach(s -> {
                    for (String w : words) {
                        if (s.toLowerCase().matches(".*\\s+" + w + "[,\\s!?.…]+.*")) {
                            resultList.add(s.trim() + "\n");
                            break;
                        }
                    }
                });
    }
}
