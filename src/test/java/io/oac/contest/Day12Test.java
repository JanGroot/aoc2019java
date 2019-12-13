package io.oac.contest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static java.util.List.of;

public class Day12Test {

    @ParameterizedTest
    @CsvSource({
            "'<x=-1, y=0, z=2>\n<x=2, y=-10, z=-7>\n<x=4, y=-8, z=8>\n<x=3, y=5, z=-1>', 10, 179",
            "'<x=-8, y=-10, z=0>\n<x=5, y=5, z=10>\n<x=2, y=-7, z=3>\n<x=9, y=-8, z=-3>', 100, 1940"
    })
    public void calculateSystemEnergy(String input, int steps, int result) {
        final var moons = Day12.getMoons(of(input.split("\n")));
        Assertions.assertEquals(result, Day12.getEnergy(steps, moons));
    }

    @ParameterizedTest
    @CsvSource({
            "'<x=-1, y=0, z=2>\n<x=2, y=-10, z=-7>\n<x=4, y=-8, z=8>\n<x=3, y=5, z=-1>', 2772",
            "'<x=-8, y=-10, z=0>\n<x=5, y=5, z=10>\n<x=2, y=-7, z=3>\n<x=9, y=-8, z=-3>', 4686774924",
    })
    public void stepsToSameState(String input, long steps) {
        final var moons = Day12.getMoons(of(input.split("\n")));
        Assertions.assertEquals(steps, Day12.getStepsToRepeat(moons));
    }
}