package io.oac.contest;

import java.util.Arrays;
import java.util.Scanner;

public class Day5 {
    public static void main(String[] args) {
        IntComputer3 computer = new IntComputer3("a", getLines(), () -> new Scanner(System.in).nextLong(), System.out::println);

        computer.run();
        //part 1
        System.out.println(computer.getAddress(0));
        //part 2


    }

    private static long[] getLines() {
        return Arrays.stream(Util.getInput("input5").get(0).split(","))
                .mapToLong(Long::parseLong)
                .toArray();
    }
}

