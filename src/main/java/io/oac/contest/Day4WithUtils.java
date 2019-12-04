package io.oac.contest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.IntStream;

public class Day4WithUtils {
    public static void main(String[] args) {
        System.out.println(IntStream.rangeClosed(307237, 769058).boxed()
                .map(Object::toString).filter(Day4WithUtils::isSorted)
                .filter(Day4WithUtils::hasRepeatingDigits).count());
        System.out.println(IntStream.rangeClosed(307237, 769058).boxed()
                .map(Object::toString).filter(Day4WithUtils::isSorted)
                .filter(Day4WithUtils::hasGroupOfTwoDigits).count());
    }

    static boolean isSorted(String password) {
        return ArrayUtils.isSorted(password.toCharArray());
    }

    static boolean hasRepeatingDigits(String password) {
        return password.chars().anyMatch(i -> StringUtils.countMatches(password, (char) i) >= 2);
    }

    static boolean hasGroupOfTwoDigits(String password) {
        return password.chars().anyMatch(i -> StringUtils.countMatches(password, (char) i) == 2);
    }
}

