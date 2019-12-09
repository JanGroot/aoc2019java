package io.oac.contest;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IntComputerTest {

    IntComputer computer;

    @Test
    public void day2() {
        computer = new IntComputer(getMem("input2"));
        computer.setAddress(1, 12);
        computer.setAddress(2, 2);
        computer.run();
        assertThat(computer.getAddress(0), is(3850704L));

        computer = new IntComputer(getMem("input2"));
        computer.setAddress(1, 67);
        computer.setAddress(2, 18);
        computer.run();
        assertThat(computer.getAddress(0), is(19690720L));
    }

    @Test
    public void day5() {
        var result = new AtomicLong();
        computer = new IntComputer(getMem("input5"), () -> 1L, result::set);
        computer.run();
        assertThat(result.get(), is(9775037L));

        //part 2
        computer = new IntComputer(getMem("input5"), () -> 5L, result::set);
        computer.run();
        assertThat(result.get(), is(15586959L));
    }

    @Test
    public void day7() {
        assertThat(Day7.runSimulation(new Long[]{1L,2L,3L,0L,4L}, "input7"), is(277328L));
        assertThat(Day7.runSimulation(new Long[]{6L,8L,5L,9L,7L}, "input7"), is(11304734L));
    }

    @Test
    public void day9() {
        var result = new AtomicLong();
        computer = new IntComputer(getMem("input9"), () -> 1L, result::set);
        computer.run();
        assertThat(result.get(), is(2745604242L));

        //part 2
        computer = new IntComputer(getMem("input9"), () -> 2L, result::set);
        computer.run();
        assertThat(result.get(), is(51135L));
    }

    private static long[] getMem(String file) {
        return Arrays.stream(Util.getInput(file).get(0).split(","))
                .mapToLong(Long::parseLong)
                .toArray();
    }

}