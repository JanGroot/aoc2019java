package io.oac.contest;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.oac.contest.Util.getInput;
import static java.lang.Math.atan2;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.groupingBy;

public class Day10 {
    public static void main(String[] args) {
        final var input = getInput("input10");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static int part1(List<String> input) {
        final var asteroids = getAsteroids(input);
        return asteroids.stream().map(a -> a.canSee(asteroids)).max(Integer::compareTo).get();
    }

    public static int part2(List<String> input) {
        final var asteroids = getAsteroids(input);
        final var station = asteroids.stream()
                .map(a -> a.getTargetsByAngle(asteroids))
                .max(comparingInt(m -> m.size()))
                .orElseThrow();

        var lines = new ArrayList<>(station.keySet());
        lines.sort(Double::compareTo);
        Collections.reverse(lines);
        final var winner = station.get(lines.get(199)).get(0);
        return winner.w * 100 + winner.h;
    }

    private static List<Asteroid> getAsteroids(List<String> input) {
        var h = input.size();
        var w = input.get(0).length();
        List<Asteroid> asteroids = new ArrayList<>();
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                String row = input.get(y);
                if (row.charAt(x) == '#') {
                    asteroids.add(new Asteroid(x, y));
                }
            }
        }
        return asteroids;
    }

    @Data
    static class Asteroid {
        final int w;
        final int h;

        int canSee(List<Asteroid> asteroids) {
            return asteroids.stream()
                    .filter((o) -> !this.equals(o))
                    .map((o) -> getLine(this, o))
                    .collect(Collectors.toSet())
                    .size();
        }

        Map<Double, List<Asteroid>> getTargetsByAngle(List<Asteroid> asteroids) {
            return asteroids.stream()
                    .filter((o) -> !this.equals(o))
                    .sorted(comparingInt(this::getDistance))
                    .collect(groupingBy((o) -> getLine(this, o)));
        }

        private int getDistance(Asteroid to) {
            return (to.w - w) ^ 2 + (to.h - h) ^ 2;
        }

        // thanks wikipedia...
        private double getLine(Asteroid from, Asteroid to) {
            return atan2(to.w - from.w, to.h - from.h); //
        }
    }
}

