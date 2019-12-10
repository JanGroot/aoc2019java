package io.oac.contest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Disabled
public class Day10Test {

    @ParameterizedTest
    @CsvSource({
            "111111, true",
            "223450, false",
            "123789, false"
    })
    public void solvePart1(String input, boolean valid) {
        Assertions.assertEquals(valid, Day10.part1(input));
    }

    @ParameterizedTest
    @CsvSource({
            "112233, true",
            "123444, false",
            "111122, true"
    })
    public void solvePart2(String input, boolean valid) {
        Assertions.assertEquals(valid, Day10.part2(input));
    }
}