package io.oac.contest;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class IntComputer3 implements Runnable {
    private final String id;
    private final int[] memory;
    private int instructionPointer = 0;
    private Callable<Integer> input;
    private Consumer<Integer> output;

    public IntComputer3(String id, int[] memory, Callable<Integer> input, Consumer<Integer> output) {
        this.id = id;
        this.memory = memory;
        this.input = input;
        this.output = output;
    }

    public String getCurrentOpcode() {
        String instruction = String.format("%04d", memory[instructionPointer]);
        return instruction.substring(instruction.length() - 2);
    }

    public String getModes() {
        String instruction = String.format("%04d", memory[instructionPointer]);
        return StringUtils.reverse(instruction.substring(0, instruction.length() - 2));
    }

    public int getNoun() {
        char mode = getModes().charAt(0);
        int noun = 0;
        switch (mode) {
            case '1' -> noun = getAddress(instructionPointer + 1);
            case '0' -> noun = getAddress(getAddress(instructionPointer + 1));
        }
        return noun;
    }

    public int getVerb() {
        char mode = getModes().charAt(1);
        int noun = 0;
        switch (mode) {
            case '1' -> noun = getAddress(instructionPointer + 2);
            case '0' -> noun = getAddress(getAddress(instructionPointer + 2));
        }
        return noun;
    }

    public void moveToNextOpcode(int positions) {
        instructionPointer += positions;
    }

    public int getAddress(int pos) {
        return memory[pos];
    }

    public void setAddress(int pos, int value) {
        memory[pos] = value;
    }

    private void doIncrement() {
        int pos = getAddress(instructionPointer + 3);
        setAddress(pos, getNoun() + getVerb());
        moveToNextOpcode(4);
    }

    private void doMultiply() {
        int pos = getAddress(instructionPointer + 3);
        setAddress(pos, getNoun() * getVerb());
        moveToNextOpcode(4);
    }

    private void getInput() {
        var pos = getAddress(instructionPointer + 1);
        try{
            System.out.println(id + " waiting");
            var value = input.call();
            setAddress(pos, value);
        } catch (Exception e) {
            //
        }
        moveToNextOpcode(2);
    }

    private void doOutput() {
        var modes = getModes();
        int value = 0;
        switch (modes) {
            case "00" -> value = getAddress(getAddress(instructionPointer + 1));
            case "01" -> value = getAddress(instructionPointer + 1);
        }
        output.accept(value);
        moveToNextOpcode(2);
    }

    private void doJump(boolean b) {
        if ((0 != getNoun()) == b) {
            instructionPointer = getVerb();
        } else {
            moveToNextOpcode(3);
        }
    }

    private void doEquals() {
        var pos = getAddress(instructionPointer + 3);
        if (getNoun() == getVerb()) {
            setAddress(pos, 1);
        } else {
            setAddress(pos, 0);
        }
        moveToNextOpcode(4);
    }

    private void doLessThan() {
        var pos = getAddress(instructionPointer + 3);
        if (getNoun() < getVerb()) {
            setAddress(pos, 1);
        } else {
            setAddress(pos, 0);
        }
        moveToNextOpcode(4);
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
            }
        }
    }
}




