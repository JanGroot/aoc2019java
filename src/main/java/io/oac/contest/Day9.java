package io.oac.contest;

import java.util.Arrays;
import java.util.Scanner;

public class Day9 {
    public static void main(String[] args) {
        IntComputer3 computer = new IntComputer3("", getLines(), () -> new Scanner(System.in).nextInt(), System.out::println);
        computer.run();
    }

    private static int[] getLines() {
        return Arrays.stream(Util.getInput("input9t").get(0).split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}

