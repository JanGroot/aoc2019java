package io.oac.contest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.List.of;
import static org.apache.commons.lang3.ArrayUtils.toPrimitive;

public class Day7 {

    public static void main(String[] args) {
        Util.Permute part1 = new Util.Permute();
        Util.Permute part2 = new Util.Permute();
        part1.permute(new ArrayList<>(of(1L, 2L, 3L, 4L, 0L)), 0);
        part2.permute(new ArrayList<>(of(5L, 6L, 7L, 8L, 9L)), 0);

        final var input7 = Util.getInput("input7").get(0);

        System.out.println(part1.getPerms().stream().map(
                phases -> {
                    return runSimulation(toPrimitive(phases), input7);
                }).max(Long::compareTo).orElseThrow());

        System.out.println(part2.getPerms().stream().map(
                phases -> runSimulation(toPrimitive(phases), input7)).max(Long::compareTo).orElseThrow());
    }

    static long runSimulation(long[] phases, String file) {
        IntComputer a = new IntComputer(getMemory(file));
        IntComputer b = new IntComputer(getMemory(file));
        IntComputer c = new IntComputer(getMemory(file));
        IntComputer d = new IntComputer(getMemory(file));
        IntComputer e = new IntComputer(getMemory(file));

        var ea = e.append(a);
        var ab = a.append(b);
        var bc = b.append(c);
        var cd = c.append(d);
        var de = d.append(e);

        ea.offer(phases[0]);
        ab.offer(phases[1]);
        bc.offer(phases[2]);
        cd.offer(phases[3]);
        de.offer(phases[4]);
        ea.offer(0L);
        try {
            CompletableFuture.allOf(
                    CompletableFuture.runAsync(a),
                    CompletableFuture.runAsync(b),
                    CompletableFuture.runAsync(c),
                    CompletableFuture.runAsync(d),
                    CompletableFuture.runAsync(e))
                    .get();
            return ea.take();
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static long[] getMemory(String input) {
        return Arrays.stream(input.split(","))
                .mapToLong(Long::parseLong)
                .toArray();
    }

}

