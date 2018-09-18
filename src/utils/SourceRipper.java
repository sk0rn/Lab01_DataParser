package utils;

import threads.Searcher;
import threads.SimpleThreadPool;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class SourceRipper {

    private final List<String> resultList = Collections.synchronizedList(new ArrayList<>());
    private int numOfThreads;

    public SourceRipper(int numOfThreads) {
        this.numOfThreads = numOfThreads;
    }

    public SourceRipper() {
        this.numOfThreads = Runtime.getRuntime().availableProcessors();
    }

    public void getOccurencies(String[] sources, String[] words, String res) throws InterruptedException {
        SimpleThreadPool pool = new SimpleThreadPool(numOfThreads);
        for (int i = 0; i < sources.length; i++) {
            Thread thread = new Thread(new Searcher(resultList, words, sources[i]));
            pool.startWork(thread);
        }
        pool.joinAll();
        saveResult(res);

    }

    private void saveResult(String res) {
        byte[] buffer;
        try (FileOutputStream fileStream = new FileOutputStream(res);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileStream)) {
            for(String s: resultList) {
                buffer = s.getBytes();
                bufferedOutputStream.write(buffer);
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

}
