package io.oac.contest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

public class Day8Test {

    @ParameterizedTest
    @CsvSource({
            "123456789012, 2,3, '123456,789012'"
    })
    public void getLayerTest(String input, int h, int w, String result) {
        Assertions.assertEquals(List.of(result.split(",")), Day8.getLayers(input, h, w));
    }
}