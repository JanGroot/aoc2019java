package io.oac.contest;

import java.util.Arrays;

public class Day2 {

    public static void main(String[] args) {
        IntComputer computer = new IntComputer(getLines());
        computer.setAddress(1, 12);
        computer.setAddress(2, 2);
        computer.run();
        //part 1
        System.out.println(computer.getAddress(0));

        //part 2
        for (int i = 1; i < 100; i++) {
            for (int j = 99; j > 0; j--) {
                computer = new IntComputer(getLines());
                computer.setAddress(1, i);
                computer.setAddress(2, j);
                computer.run();
                if (computer.getAddress(0) == 19690720) {
                    System.out.println((i * 100) + j);
                }

            }
        }

    }

    private static int[] getLines() {
        return Arrays.stream(Util.getInput("input2").get(0).split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
