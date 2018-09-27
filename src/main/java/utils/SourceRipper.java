package utils;

import threads.Searcher;
import threads.SimpleThreadPool;
import utils.saver.ResultWriter;
import utils.saver.SourceRipperWriter;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class SourceRipper {

    private final List<String> resultList = Collections.synchronizedList(new ArrayList<>());
    private ResultWriter resultWriter;
    private int numOfThreads;

    public SourceRipper(int numOfThreads) {
        this.numOfThreads = numOfThreads;
        resultWriter = new SourceRipperWriter();
    }

    public SourceRipper() {
        this(Runtime.getRuntime().availableProcessors());
    }

    public void getOccurrences(String[] sources, String[] words, String res) throws InterruptedException {
        SimpleThreadPool pool = new SimpleThreadPool(numOfThreads);
        for (int i = 0; i < sources.length; i++) {
            Thread thread = new Thread(new Searcher(resultList, words, sources[i]));
            pool.startWork(thread);
        }
        pool.joinAll();
        resultWriter.write(resultList, res);

    }

}
