package io.oac.contest;

import java.util.stream.IntStream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Day4 {
    public static void main(String[] args) {
        System.out.println(IntStream.rangeClosed(307237, 769058).filter(Day4::isValid).count());
        System.out.println(IntStream.rangeClosed(307237, 769058).filter(Day4::isValid).filter(Day4::hasGroupOfTwo).count());
    }

    static boolean isValid(Integer password) {
        char[] chars = password.toString().toCharArray();
        Character prev = null;
        boolean valid = false;
        for (char aChar : chars) {
            if (prev == null) {
                prev = aChar;
                continue;
            }
            if (aChar < prev) {
                return false;
            }
            if (aChar == prev || valid) {
                valid = true;
            }
            prev = aChar;
        }
        return valid;
    }

    // as incrementing and ordering is checked previously, just count if any number occurs twice
    static boolean hasGroupOfTwo(Integer password) {
        return password.toString().chars()
                .mapToObj(c -> (char) c)
                .collect(groupingBy(identity(), counting()))
                .containsValue(2L);
    }
}

