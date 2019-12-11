package io.oac.contest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

@Disabled
public class Day10Test {

    @ParameterizedTest
    @CsvSource({
            "'.#..#\n.....\n#####\n....#\n...##', 8",
            "'......#.#.\n#..#.#....\n..#######.\n.#.#.###..\n.#..#.....\n..#....#.#\n#..#....#.\n.##.#..###\n##...#..#.\n.#....####', 33",
            "'#.#...#.#.\n.###....#.\n.#....#...\n##.#.#.#.#\n....#.#.#.\n.##..###.#\n..#...##..\n..##....##\n......#...\n.####.###.', 35"
    })
    public void solvePart1(String input, int best) {
        Assertions.assertEquals(best, Day10.part1(List.of(input.split("\n"))));
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