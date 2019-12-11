package io.oac.contest;

import lombok.Data;
import lombok.Value;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day11 {
    public static void main(String[] args) {
        System.out.println(part1());
        part2();
    }

    public static int part1() {
        Robot painter = new Robot();
        IntComputer computer = new IntComputer(getLines(), painter::getInput, painter::step);
        computer.run();
        return painter.getHull().keySet().size();
    }

    public static void part2() {
        Robot painter = new Robot();
        painter.getHull().put(new Panel(0, 0), 1);
        IntComputer computer = new IntComputer(getLines(), painter::getInput, painter::step);
        computer.run();

        final var panels = painter.getHull().keySet();
        final var minx = panels.stream().mapToInt(Panel::getX).min().orElseThrow();
        final var maxx = panels.stream().mapToInt(Panel::getX).max().orElseThrow();
        final var miny = panels.stream().mapToInt(Panel::getY).min().orElseThrow();
        final var maxy = panels.stream().mapToInt(Panel::getY).max().orElseThrow();
        System.out.println(minx + " " + maxx + " " + miny + " " + maxy);
        for (int y = maxy; y >= miny; y--) {
            for(int x = minx; x<= maxx; x++) {
                var tile = painter.getHull().get(new Panel(x, y));
                if (null != tile && tile == 1) {
                    System.out.print("#");
                } else{
                    System.out.print(" ");
                }
            }
            System.out.print("\n");

        }


    }


    private static long[] getLines() {
        return Arrays.stream(Util.getInput("input11").get(0).split(","))
                .mapToLong(Long::parseLong)
                .toArray();
    }
}
@Data
class Robot {
    Map<Panel, Integer> hull = new HashMap<>();
    Panel current = new Panel(0, 0);
    boolean paint = true;
    int pointer = 0;
    int[][] directions = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};

    void step(long input) {
        if (paint) {
            hull.put(current, (int) input);
            paint = false;
        } else {
            if (input == 1) {
                if (pointer == 0) {
                    pointer = 3;
                } else {
                    pointer -= 1;
                }
            } else {
                if (pointer == 3) {
                    pointer = 0;
                } else {
                    pointer += 1;
                }
            }
            int[] direction = directions[pointer % 4];
            current = new Panel(current.getX() + direction[0], current.getY() + direction[1]);
            paint = true;
        }
    }

    long getInput() {
        final var colour = hull.get(current);
        return colour == null ? 0 : colour;
    }
}

@Value
class Panel {
    int x;
    int y;
}

