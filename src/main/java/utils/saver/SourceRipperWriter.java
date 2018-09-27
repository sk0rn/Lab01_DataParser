package utils.saver;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class SourceRipperWriter implements ResultWriter {

    @Override
    public void write(List<String> data, String res) {
        byte[] buffer;
        try (FileOutputStream fileStream = new FileOutputStream(res);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileStream)) {
            for(String s: data) {
                buffer = s.getBytes();
                bufferedOutputStream.write(buffer);
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
