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
        numOfThreads = Runtime.getRuntime().availableProcessors();
        resultWriter = new SourceRipperWriter();
    }

    public SourceRipper(ResultWriter resultWriter) {
        numOfThreads = Runtime.getRuntime().availableProcessors();
        this.resultWriter = resultWriter;

    }

    public void getOccurrences(String[] sources, String[] words, String res) throws InterruptedException {
        if (sources == null || words == null || res == null) {
            throw new IllegalArgumentException("Arguments must be not null");
        }

        SimpleThreadPool pool = new SimpleThreadPool(numOfThreads);
        for (String source : sources) {
            Thread thread = new Thread(new Searcher(resultList, words, source));
            pool.startWork(thread);
        }
        pool.joinAll();

        if (resultList.size() != 0) {
            resultWriter.write(resultList, res);
        }
    }

}
