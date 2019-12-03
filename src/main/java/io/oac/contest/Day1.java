package io.oac.contest;

import java.util.stream.Collectors;

import static io.oac.contest.Util.getInput;

public class Day1 {

    public static void main(String[] args) {
        var ints = getInput("input")
                .stream()
                .map(Integer::parseInt).collect(Collectors.toList());

        var part1 = ints.stream()
                .map(a -> Math.floorDiv(a, 3))
                .map(a -> a - 2)
                .reduce(Integer::sum)
                .get();

        var part2 = ints.stream()
                .map(a -> Math.floorDiv(a, 3))
                .map(a -> a - 2).map(Day1::getMore)
                .reduce(Integer::sum).get();


        System.out.println(part1);
        System.out.println(part2);
    }


    static int getMore(int remainder) {
        if (remainder <= 0) {
            return 0;
        } else {
            return remainder + getMore(Math.floorDiv(remainder, 3) - 2);
        }
    }


}
