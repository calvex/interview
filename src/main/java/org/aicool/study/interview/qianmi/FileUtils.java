package org.aicool.study.interview.qianmi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.stream.Stream;

public class FileUtils {

    public static Stream<String> readStream() {
        URL url = ClassLoader.getSystemResource("message.dat");
        try {
            return Files.lines(Paths.get(url.toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return Stream.empty();
    }
}
