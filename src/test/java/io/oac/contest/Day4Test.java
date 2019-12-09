package io.oac.contest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Day4Test {

    @ParameterizedTest
    @CsvSource({
            "111111, true",
            "223450, false",
            "123789, false"
    })
    public void isValidPassword(int password, boolean valid) {
        Assertions.assertEquals(valid, Day4.isValid(password));
    }

    @ParameterizedTest
    @CsvSource({
            "112233, true",
            "123444, false",
            "111122, true"
    })
    public void hasGroupOfTwo(int password, boolean valid) {
        Assertions.assertEquals(valid, Day4.hasGroupOfTwo(password));
    }
}