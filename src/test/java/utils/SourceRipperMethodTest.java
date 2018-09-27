package utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.saver.ResultWriter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



class SourceRipperMethodTest {

    private SourceRipper sourceRipper;
    private ResultWriter mock = Mockito.mock(ResultWriter.class);


    private String pathIn = "D:\\dev_edu\\STC13_HT\\Lab01_FileParser\\src\\main\\java\\data\\datain\\";
    private String pathOut = "D:\\dev_edu\\STC13_HT\\Lab01_FileParser\\src\\main\\java\\data\\dataout\\etalon.dat";
    private String[] correctWords = {"hello", "cruel", "world"};
    private String[] wrongWords = {"any", "text"};
    private String[] sources = null;

    @BeforeEach
    void setUp() {
        sourceRipper = new SourceRipper(mock);

        File dir = new File(pathIn);
        FilenameFilter filter = (dir1, name) -> name.toLowerCase().matches("([\\s\\w*])+(\\.)+([\\w]+)$");
        if (dir.exists()) {
            String[] f = dir.list(filter);
            sources = new String[Objects.requireNonNull(f).length];
            for (int i = 0; i < f.length; i++) {
                sources[i] = pathIn + f[i];
            }
        }
    }

    @Test
    void writeDataTest() {
        try {
            sourceRipper.getOccurrences(sources, correctWords, pathOut);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // проверяем вызывается ли метод записи если есть найденные парсером данные
        verify(mock, times(1)).write(any(), any());
    }

    @Test
    void noWriteEmptyListTest() {
        try {
            sourceRipper.getOccurrences(sources, wrongWords, pathOut);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // если парсер ничего не нашел, то вызывать метод записи не нужно
        verify(mock, times(0)).write(any(), any());
    }


    @Test
    void sourcesIllegalArgExceptTest() {
        assertThrows(IllegalArgumentException.class,
                () -> sourceRipper.getOccurrences(null, correctWords, pathOut));
    }

    @Test
    void wordsIllegalArgExceptTest() {
        assertThrows(IllegalArgumentException.class,
                () -> sourceRipper.getOccurrences(sources, null, pathOut));
    }

    @Test
    void resIllegalArgExceptTest() {
        assertThrows(IllegalArgumentException.class,
                () -> sourceRipper.getOccurrences(sources, correctWords, null));

    }
}