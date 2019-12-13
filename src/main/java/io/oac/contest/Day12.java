package io.oac.contest;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;
import static org.apache.commons.math3.util.ArithmeticUtils.lcm;

public class Day12 {
    public static void main(String[] args) {
        final var moons = getMoons(Util.getInput("input12"));
        System.out.println(getEnergy(1000, moons));
        System.out.println(getStepsToRepeat(moons));
    }

    public static int getEnergy(int steps, List<Moon> moons) {
        for (int i = 0; i < steps; i++) {
            moons = doStep(moons);
        }
        return moons.stream().mapToInt(Moon::getEnergy).reduce(Integer::sum).orElseThrow();
    }

    public static long getStepsToRepeat(List<Moon> moons) {
        Set<List<Integer>> xstates = new HashSet<>();
        Set<List<Integer>> ystates = new HashSet<>();
        Set<List<Integer>> zstates = new HashSet<>();
        long xf = 0, yf = 0, zf = 0;
        for (long i = 0; i < Long.MAX_VALUE; i++) {
            moons = doStep(moons);
            var xstate = new ArrayList<Integer>();
            var ystate = new ArrayList<Integer>();
            var zstate = new ArrayList<Integer>();
            moons.stream().forEach(m -> {
                xstate.add(m.getX());
                xstate.add(m.getVx());
                ystate.add(m.getY());
                ystate.add(m.getVy());
                zstate.add(m.getZ());
                zstate.add(m.getVz());
            });
            if (xf == 0 && !xstates.add(xstate)) {
                xf = i;
            }
            if (yf == 0 && !ystates.add(ystate)) {
                yf = i;
            }
            if (zf == 0 && !zstates.add(zstate)) {
                zf = i;
            }
            if (xf > 0 && yf > 0 && zf > 0) {
                return lcm(lcm(xf, yf), zf);
            }
        }
        throw new RuntimeException("no solution");
    }

    static List<Moon> doStep(List<Moon> moons) {
        return moons.stream().map(m -> m.applyStep(moons)).collect(Collectors.toList());
    }

    static List<Moon> getMoons(List<String> input) {
        return input
                .stream()
                .map(s -> s.substring(1, s.length() - 1).split(","))
                .map(Moon::new).collect(Collectors.toList());
    }
}

@Value
@AllArgsConstructor
class Moon {
    int x, y, z, vx, vy, vz;

    Moon(String[] coords) {
        x = parseInt((coords[0].split("=")[1]));
        y = parseInt((coords[1].split("=")[1]));
        z = parseInt((coords[2].split("=")[1]));
        vx = 0;
        vy = 0;
        vz = 0;
    }

    Moon applyStep(List<Moon> moons) {
        int vxd = getVx(moons);
        int vyd = getVy(moons);
        int vzd = getVz(moons);
        return new Moon(x + vxd, y + vyd, z + vzd, vxd, vyd, vzd);
    }

    int getVx(List<Moon> moons) {
        return vx + moons.stream()
                .filter(m -> this != m)
                .map(o -> o.getX() - this.x)
                .map(i -> i < 0 ? -1 : (i == 0) ? 0 : 1)
                .reduce(Integer::sum)
                .orElseThrow();
    }

    int getVy(List<Moon> moons) {
        return vy + moons.stream()
                .filter(m -> this != m)
                .map(o -> o.getY() - this.y)
                .map(i -> i < 0 ? -1 : (i == 0) ? 0 : 1)
                .reduce(Integer::sum)
                .orElseThrow();
    }

    int getVz(List<Moon> moons) {
        return vz + moons.stream()
                .filter(m -> this != m)
                .map(o -> o.getZ() - this.z)
                .map(i -> i < 0 ? -1 : (i == 0) ? 0 : 1)
                .reduce(Integer::sum)
                .orElseThrow();
    }

    int getEnergy() {
        return (abs(x) + abs(y) + abs(z)) * (abs(vx) + abs(vy) + abs(vz));
    }
}

