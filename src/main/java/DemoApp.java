import utils.SourceRipper;

import java.io.File;
import java.io.FilenameFilter;

public class DemoApp {

    public static void main(String[] args) throws InterruptedException {

        String pathIn = "D:\\dev_edu\\STC13_HT\\Lab01_FileParser\\src\\main\\java\\data\\datain\\";
        String pathOut = "D:\\dev_edu\\STC13_HT\\Lab01_FileParser\\src\\main\\java\\data\\dataout\\result.dat";
        String[] searchedWords = {"hello", "cruel", "world"};
        String[] sources = null;
        File dir = new File(pathIn);
        FilenameFilter filter = (dir1, name) -> name.toLowerCase().matches("([\\s\\w*])+(\\.)+([\\w]+)$");
        if (dir.exists()) {
            String[] f = dir.list(filter);
            sources = new String[f.length];
            for (int i = 0; i < f.length; i++) { ;
                sources[i] = pathIn + f[i];
            }
        }

        SourceRipper sr = new SourceRipper(4);
        sr.getOccurrences(sources, searchedWords, pathOut);

    }
}
