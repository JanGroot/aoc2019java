package io.oac.contest;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.List.of;

public class Day7 {
    public static void main(String[] args) throws Exception {
        Permute part1 = new Permute();
        Permute part2 = new Permute();
        part1.permute(new ArrayList<>(of(1L, 2L, 3L, 4L, 0L)), 0);
        part2.permute(new ArrayList<>(of(5L, 6L, 7L, 8L, 9L)), 0);


        System.out.println(part1.getPerms().stream().map(
                phases -> {
                    try {
                        return runSimulation(phases);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        return null;
                    }
                }).max(Long::compareTo));

        System.out.println(part2.getPerms().stream().map(
                phases -> {
                    try {
                        return runSimulation(phases);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        return null;
                    }
                }).max(Long::compareTo));
    }

    private static long runSimulation(Long[] phases) throws ExecutionException, InterruptedException {

        BlockingQueue<Long> ea = new LinkedBlockingQueue();
        BlockingQueue<Long> ab = new LinkedBlockingQueue();
        BlockingQueue<Long> bc = new LinkedBlockingQueue();
        BlockingQueue<Long> cd = new LinkedBlockingQueue();
        BlockingQueue<Long> de = new LinkedBlockingQueue();


        IntComputer3 a = null;
        IntComputer3 b = null;
        IntComputer3 c = null;
        IntComputer3 d = null;
        IntComputer3 e = null;
        try {
            a = new IntComputer3("a", getLines(), ea::take, ab::offer);
            b = new IntComputer3("b", getLines(), ab::take, bc::offer);
            c = new IntComputer3("c", getLines(), bc::take, cd::offer);
            d = new IntComputer3("d", getLines(), cd::take, de::offer);
            e = new IntComputer3("e", getLines(), de::take, ea::offer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ea.offer(phases[0]);
        ab.offer(phases[1]);
        bc.offer(phases[2]);
        cd.offer(phases[3]);
        de.offer(phases[4]);
        ea.offer(0L);
        CompletableFuture.allOf(
                CompletableFuture.runAsync(a),
                CompletableFuture.runAsync(b),
                CompletableFuture.runAsync(c),
                CompletableFuture.runAsync(d),
                CompletableFuture.runAsync(e))
                .get();
        return ea.take();

    }

    private static long[] getLines() {
        return Arrays.stream(Util.getInput("input7").get(0).split(","))
                .mapToLong(Long::parseLong)
                .toArray();
    }

    static class Permute {
        @Getter
        public final List<Long[]> perms = new ArrayList<>();

        public void permute(java.util.List<Long> arr, int k) {
            for (int i = k; i < arr.size(); i++) {
                java.util.Collections.swap(arr, i, k);
                permute(arr, k + 1);
                java.util.Collections.swap(arr, k, i);
            }
            if (k == arr.size() - 1) {
                perms.add(arr.toArray(new Long[0]));
            }
        }
    }
}

