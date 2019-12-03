package io.oac.contest;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Util {
    public static List<String> getInput(String file) {
        try {
            return Files.readAllLines(Paths.get(Util.class.getClassLoader().getResource(file).toURI()));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
