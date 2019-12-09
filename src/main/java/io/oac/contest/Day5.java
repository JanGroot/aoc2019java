package io.oac.contest;

import java.util.Arrays;

public class Day5 {
    public static void main(String[] args) {
        IntComputer computer = new IntComputer(getLines(), () -> 1L, System.out::println);
        computer.run();

        //part 2
        computer = new IntComputer(getLines(), () -> 5L, System.out::println);
        computer.run();
    }

    private static long[] getLines() {
        return Arrays.stream(Util.getInput("input5").get(0).split(","))
                .mapToLong(Long::parseLong)
                .toArray();
    }
}

