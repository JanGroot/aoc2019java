package io.oac.contest;

import java.util.Arrays;
import java.util.Scanner;

public class Day5 {
    public static void main(String[] args) {
        IntComputer3 computer = new IntComputer3("a", getLines(), () -> new Scanner(System.in).nextInt(), System.out::println);

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

