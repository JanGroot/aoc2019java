package io.oac.contest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day13 {

    public static void main(String[] args) {
        var screen = new ArrayList<Long>();
        IntComputer computer = new IntComputer(getLines(), () -> 1L, screen::add);
        computer.run();
        int blocks = 0;
        Map<Long[], Long> screenMap = new HashMap<>();
        for (int i = 0; i < screen.size(); i++) {
            if (i % 3 == 0) {
                screenMap.put(new Long[]{
                        screen.get(i), screen.get(i + 1)}, screen.get(i + 2));
            }
        }

        System.out.println(screenMap.values().stream().filter(v -> v.equals(2L)).count());

        Arcacde game = new Arcacde(getLines());
        game.runGame(2);
        System.out.println(game.score());
    }

    private static long[] getLines() {
        return Arrays.stream(Util.getInput("input13").get(0).split(","))
                .mapToLong(Long::parseLong)
                .toArray();
    }

}

class Arcacde {
    private final IntComputer unrealEngine;
    private List<Long> screen;

    Arcacde(long[] memory) {
        screen = new ArrayList<>();
        this.unrealEngine = new IntComputer(memory, this::input, screen::add);
    }

    void runGame(int quarters) {
        unrealEngine.setAddress(0, quarters);
        unrealEngine.run();
    }

    long input() {
        Map<Long, Long[]> screenMap = new HashMap<>();
        for (int i = 0; i < screen.size(); i++) {
            if (i % 3 == 0) {
                screenMap.put(screen.get(i + 2), new Long[]{
                        screen.get(i), screen.get(i + 1)});
            }
        }
        var ball = screenMap.get(4L)[0];
        var paddle = screenMap.get(3L)[0];
        return Long.compare(ball, paddle);
    }

    long score() {
        return screen.stream().max(Long::compareTo).orElseThrow();
    }
}



