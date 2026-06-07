package engine.io;

import java.io.IOException;
import java.io.InputStream;

public class FileLoader {

    public static String readFile(String path) {
        String file;
        try {
            InputStream stream = FileLoader.class.getResourceAsStream(path);
            file = new String(stream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

}
