package io.oac.contest;

import lombok.Value;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.oac.contest.Util.getInput;

public class Day10 {
    public static void main(String[] args) {
        final var input = getInput("input10");
        var h = input.size();
        var w = input.get(0).length();
        System.out.println("h = " + h);
        System.out.println("w = " + w);
        System.out.println(part1(input));
        System.out.println(part2(input.get(0)));
    }

    public static int part1(List<String> input) {
        var h = input.size();
        var w = input.get(0).length();
        System.out.println("h = " + h);
        System.out.println("w = " + w);
        List<Asteroid> asteroids = new ArrayList<>();
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                String row = input.get(y);
                if (row.charAt(x) == '#') {
                    asteroids.add(new Asteroid(x, y));
                }
            }
        }
        System.out.println("asteroids = " + asteroids);
        return asteroids.stream().map(a -> a.canSee(asteroids)).max(Integer::compareTo).get();
    }

    public static Object part2(String input){
        return null;
    }

    @Value
    static class Asteroid {
        int w;
        int h;

        int canSee(List<Asteroid> asteroids) {
            Set<Line> lines = new HashSet<>();
            for (Asteroid asteroid : asteroids) {
                if (asteroid.equals(this)) {
                    continue;
                }
                lines.add(new Line(this, asteroid));
            }
            return lines.size();
        }
    }

    @Value
    static class Line {
        int x;
        int y;
        double m;
        int b;

        Line(Asteroid a, Asteroid o) {
            x = a.w - o.w;
            y = a.h - o.h;
            m = (x == 0) ? 0 : y / x;
            b = y * -1;
        }

    }
}

