package io.oac.contest;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

import static io.oac.contest.Util.getInput;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class IntComputerTest {

    IntComputer computer;

    private static long[] getMem(String file) {
        return Arrays.stream(getInput(file).get(0).split(","))
                .mapToLong(Long::parseLong)
                .toArray();
    }

    @Test
    public void day2() {
        computer = new IntComputer(getMem("input2"));
        computer.setAddress(1, 12);
        computer.setAddress(2, 2);
        computer.run();
        assertEquals(computer.getAddress(0), 3850704L);

        computer = new IntComputer(getMem("input2"));
        computer.setAddress(1, 67);
        computer.setAddress(2, 18);
        computer.run();
        assertEquals(19690720L, computer.getAddress(0));
    }

    @Test
    public void day5() {
        var result = new AtomicLong();
        computer = new IntComputer(getMem("input5"), () -> 1L, result::set);
        computer.run();
        assertEquals(9775037L, result.get());

        //part 2
        computer = new IntComputer(getMem("input5"), () -> 5L, result::set);
        computer.run();
        assertEquals(15586959L, result.get());
    }

    @Test
    public void day7() {
        assertEquals(277328L, Day7.runSimulation(new long[]{1L, 2L, 3L, 0L, 4L}, getInput("input7").get(0)));
        assertEquals(11304734L, Day7.runSimulation(new long[]{6L, 8L, 5L, 9L, 7L}, getInput("input7").get(0)));
    }

    @Test
    public void day9() {
        var result = new AtomicLong();
        computer = new IntComputer(getMem("input9"), () -> 1L, result::set);
        computer.run();
        assertEquals(2745604242L, result.get());

        //part 2
        computer = new IntComputer(getMem("input9"), () -> 2L, result::set);
        computer.run();
        assertEquals(51135L, result.get());
    }

}