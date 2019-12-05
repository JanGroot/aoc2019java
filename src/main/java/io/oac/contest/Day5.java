package io.oac.contest;

import java.util.Arrays;

public class Day5 {
    public static void main(String[] args) {
        IntComputer2 computer = new IntComputer2(getLines());

        computer.run();
        //part 1
        System.out.println(computer.getAddress(0));

        //part 2


    }

    private static int[] getLines() {
        return Arrays.stream(Util.getInput("input5").get(0).split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}

