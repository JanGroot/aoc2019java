package io.oac.contest;

import lombok.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day3 {

    public static void main(String[] args) {
        var lines = getLines();
        var wire1 = new Wire(getMoves(lines.get(0)));
        var wire2 = new Wire(getMoves(lines.get(1)));
        var wireOneSet = new HashSet(wire1.getPath());
        var wireTwo = wire2.getPath();
        var intersections = wireTwo.stream().filter(wireOneSet::contains).sorted().collect(Collectors.toList());
        //part 1
        System.out.println(intersections);

        //part 2
        var stepsToIntersections = intersections.stream()
                .map(p -> wire1.getPath().indexOf(p) + wire2.getPath().indexOf(p))
                .sorted()
                .collect(Collectors.toList());
        System.out.println(stepsToIntersections);
    }

    private static List<Move> getMoves(String[] line) {
        return Arrays.stream(line).map(Move::new).collect(Collectors.toList());
    }

    private static List<String[]> getLines() {
        return Util.getInput("input3").stream()
                .map(l -> l.split(",")).collect(Collectors.toList());
    }
}

@Value
class Wire {
    List<Point> path;

    public Wire(List<Move> moves) {
        Point start = new Point(0, 0);
        path = new ArrayList<>();
        path.add(start);
        moves.forEach((m) -> path.addAll(m.from(path.get(path.size() - 1))));
    }
}

class Move {
    char direction;
    int size;

    public Move(String directions) {
        this.direction = directions.charAt(0);
        this.size = Integer.parseInt(directions.substring(1));
    }

    public List<Point> from(Point from) {
        return switch (direction) {
            case 'U' -> IntStream.rangeClosed(1, size)
                    .mapToObj(i -> new Point(from.getX(), from.getY() + i))
                    .collect(Collectors.toList());
            case 'D' -> IntStream.rangeClosed(1, size)
                    .mapToObj(i -> new Point(from.getX(), from.getY() - i))
                    .collect(Collectors.toList());
            case 'L' -> IntStream.rangeClosed(1, size)
                    .mapToObj(i -> new Point(from.getX() - i, from.getY()))
                    .collect(Collectors.toList());
            case 'R' -> IntStream.rangeClosed(1, size)
                    .mapToObj(i -> new Point(from.getX() + i, from.getY()))
                    .collect(Collectors.toList());
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
    }
}


@Value
class Point implements Comparable<Point> {
    int x;
    int y;

    @Override
    public int compareTo(Point o) {
        return (Math.abs(x) + Math.abs(y)) - (Math.abs(o.x) + Math.abs(o.y));
    }
}
