package io.oac.contest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class IntComputer implements Runnable {
    private final long[] memory;
    private int instructionPointer = 0;
    private int base = 0;
    private Callable<Long> input;
    private Consumer<Long> output;

    public IntComputer(long[] memory) {
        this.memory = ArrayUtils.addAll(memory, new long[100000]);
    }

    public IntComputer(long[] memory, Callable<Long> input, Consumer<Long> output) {
        this.memory = ArrayUtils.addAll(memory, new long[100000]);
        this.input = input;
        this.output = output;
    }

    public String getCurrentOpcode() {
        String instruction = String.format("%05d", memory[instructionPointer]);
        return instruction.substring(instruction.length() - 2);
    }

    public String getModes() {
        String instruction = String.format("%05d", memory[instructionPointer]);
        return StringUtils.reverse(instruction.substring(0, instruction.length() - 2));
    }

    public long getNoun() {
        char mode = getModes().charAt(0);
        long noun = 0;
        switch (mode) {
            case '1' -> noun = getAddress(instructionPointer + 1);
            case '2' -> noun = getAddress(base + (int) getAddress(instructionPointer + 1));
            case '0' -> noun = getAddress((int) getAddress(instructionPointer + 1));
        }
        return noun;
    }

    public long getVerb() {
        char mode = getModes().charAt(1);
        long noun = 0;
        switch (mode) {
            case '1' -> noun = getAddress(instructionPointer + 2);
            case '2' -> noun = getAddress(base + (int) getAddress(instructionPointer + 2));
            case '0' -> noun = getAddress((int) (getAddress(instructionPointer + 2)));
        }
        return noun;
    }

    public int getTarget(int steps) {
        char mode = getModes().charAt(steps - 1);
        long target = 0;
        switch (mode) {
            case '0' -> target = getAddress(instructionPointer + steps);
            default -> target = base + getAddress(instructionPointer + steps);
        }
        return Math.toIntExact(target);
    }

    public void moveToNextOpcode(int positions) {
        instructionPointer += positions;
    }

    public long getAddress(int pos) {
        return memory[pos];
    }

    public void setAddress(int pos, long value) {
        memory[pos] = value;
    }

    private void doIncrement() {
        setAddress(getTarget(3), getNoun() + getVerb());
        moveToNextOpcode(4);
    }

    private void doMultiply() {
        setAddress(getTarget(3), getNoun() * getVerb());
        moveToNextOpcode(4);
    }

    private void getInput() {
        try {
            setAddress(getTarget(1), input.call());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        moveToNextOpcode(2);
    }

    private void doOutput() {
        output.accept(getNoun());
        moveToNextOpcode(2);
    }

    private void doJump(boolean b) {
        if ((0 != getNoun()) == b) {
            instructionPointer = (int) getVerb();
        } else {
            moveToNextOpcode(3);
        }
    }

    private void doEquals() {
        if (getNoun() == getVerb()) {
            setAddress(getTarget(3), 1);
        } else {
            setAddress(getTarget(3), 0);
        }
        moveToNextOpcode(4);
    }

    private void doLessThan() {
        if (getNoun() < getVerb()) {
            setAddress(getTarget(3), 1);
        } else {
            setAddress(getTarget(3), 0);
        }
        moveToNextOpcode(4);
    }

    private void setBase() {
        base += getNoun();
        moveToNextOpcode(2);
    }

    public void run() {
        while (!getCurrentOpcode().equals("99")) {
            switch (getCurrentOpcode()) {
                case "01" -> doIncrement();
                case "02" -> doMultiply();
                case "03" -> getInput();
                case "04" -> doOutput();
                case "05" -> doJump(true);
                case "06" -> doJump(false);
                case "07" -> doLessThan();
                case "08" -> doEquals();
                case "09" -> setBase();
            }
        }
    }

    public BlockingQueue<Long> append(IntComputer other) {
        BlockingQueue<Long> pipe = new LinkedBlockingQueue<>();
        output =  pipe::offer;
        other.input = pipe::take;
        return pipe;
    }
}




