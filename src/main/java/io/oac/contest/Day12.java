package io.oac.contest;

import java.util.Arrays;

public class Day12 {
    public static void main(String[] args) {
       part1();
        part2();
    }

    public static void part1() {

    }

    public static void part2() {


    }


    private static long[] getLines() {
        return Arrays.stream(Util.getInput("input12").get(0).split(","))
                .mapToLong(Long::parseLong)
                .toArray();
    }
}

