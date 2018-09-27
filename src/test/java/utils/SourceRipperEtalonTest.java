package utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class SourceRipperEtalonTest {

    private SourceRipper sourceRipper;

    private String in = "D:\\dev_edu\\STC13_HT\\Lab01_FileParser\\src\\main\\java\\data\\datatest\\in\\";
    private String out = "D:\\dev_edu\\STC13_HT\\Lab01_FileParser\\src\\main\\java\\data\\datatest\\out\\file.dat";
    private String etalon = "D:\\dev_edu\\STC13_HT\\Lab01_FileParser\\src\\" +
            "main\\java\\data\\datatest\\etalon\\etalon.dat";
    private String[] words = {"hello", "cruel", "world"};
    private String[] sources = null;

    @BeforeEach
    void setUp() {
        sourceRipper = new SourceRipper(4);

        File dir = new File(in);
        FilenameFilter filter = (dir1, name) -> name.toLowerCase().matches("([\\s\\w*])+(\\.)+([\\w]+)$");
        if (dir.exists()) {
            String[] f = dir.list(filter);
            sources = new String[Objects.requireNonNull(f).length];
            for (int i = 0; i < f.length; i++) {
                sources[i] = in + f[i];
            }
        }
    }

    @Test
    void compareWithEtalonFileTest() {
        try {
            sourceRipper.getOccurrences(sources, words, out);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Path expected = Paths.get(etalon);
        Path actual = Paths.get(out);
        try {
            assertArrayEquals(Files.readAllBytes(expected),Files.readAllBytes(actual));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}