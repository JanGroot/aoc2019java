package io.oac.contest;

import lombok.Getter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<String> getInput(String file) {
        try {
            return Files.readAllLines(Paths.get(Util.class.getClassLoader().getResource(file).toURI()));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    static class Permute {
        @Getter
        public final List<Long[]> perms = new ArrayList<>();

        public void permute(List<Long> arr, int k) {
            for (int i = k; i < arr.size(); i++) {
                java.util.Collections.swap(arr, i, k);
                permute(arr, k + 1);
                java.util.Collections.swap(arr, k, i);
            }
            if (k == arr.size() - 1) {
                perms.add(arr.toArray(new Long[0]));
            }
        }
    }
}
