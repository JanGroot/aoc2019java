package io.oac.contest;

import java.util.Arrays;

public class Day9 {
    public static void main(String[] args) {
        IntComputer computer = new IntComputer(getLines(), () -> 1L, System.out::println);
        computer.run();
        IntComputer computer2 = new IntComputer(getLines(), () -> 2L, System.out::println);
        computer2.run();
    }

    private static long[] getLines() {
        return Arrays.stream(Util.getInput("input9").get(0).split(","))
                .mapToLong(Long::parseLong)
                .toArray();
    }
}

