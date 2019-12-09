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
        Permute permutations = new Permute();
        permutations.permute(new ArrayList<>(of(5, 6, 7, 8, 9)), 0);


        System.out.println(permutations.getPerms().stream().map(
                phases -> {
                    try {
                        return runSimulation(phases);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).max(Integer::compareTo));
    }

    private static int runSimulation(Integer[] phases) throws ExecutionException, InterruptedException {

        BlockingQueue<Integer> ea = new LinkedBlockingQueue();
        BlockingQueue<Integer> ab = new LinkedBlockingQueue();
        BlockingQueue<Integer> bc = new LinkedBlockingQueue();
        BlockingQueue<Integer> cd = new LinkedBlockingQueue();
        BlockingQueue<Integer> de = new LinkedBlockingQueue();


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
        ea.offer(0);
        CompletableFuture.allOf(
                CompletableFuture.runAsync(a),
                CompletableFuture.runAsync(b),
                CompletableFuture.runAsync(c),
                CompletableFuture.runAsync(d),
                CompletableFuture.runAsync(e))
                .get();
        return ea.take();

    }

    private static int[] getLines() {
        return Arrays.stream(Util.getInput("input7").get(0).split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    static class Permute {
        @Getter
        public final List<Integer[]> perms = new ArrayList<>();

        public void permute(java.util.List<Integer> arr, int k) {
            for (int i = k; i < arr.size(); i++) {
                java.util.Collections.swap(arr, i, k);
                permute(arr, k + 1);
                java.util.Collections.swap(arr, k, i);
            }
            if (k == arr.size() - 1) {
                perms.add(arr.toArray(new Integer[0]));
            }
        }
    }
}

