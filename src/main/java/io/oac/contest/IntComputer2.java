package io.oac.contest;

import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

public class IntComputer2 {
    private final int[] memory;
    private int instructionPointer = 0;

    public IntComputer2(int[] memory) {
        this.memory = memory;
    }

    public String getCurrentOpcode() {
        String instruction = String.format("%04d", memory[instructionPointer]);
        return instruction.substring(instruction.length() - 2);
    }

    public String getModes() {
        String instruction = String.format("%04d", memory[instructionPointer]);
        return StringUtils.reverse(instruction.substring(0, instruction.length() - 2));
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
        var modes = getModes();
        int value = 0;
        switch (modes) {
            case "00" -> value = getAddress(getAddress(instructionPointer + 1)) + getAddress(getAddress(instructionPointer + 2));
            case "01" -> value = getAddress(getAddress(instructionPointer + 1)) + getAddress(instructionPointer + 2);
            case "10" -> value = getAddress(instructionPointer + 1) + getAddress(getAddress(instructionPointer + 2));
            case "11" -> value = getAddress(instructionPointer + 1) + getAddress(instructionPointer + 2);
        }
        setAddress(pos, value);
        moveToNextOpcode(4);
    }

    private void doMultiply() {
        var pos = getAddress(instructionPointer + 3);
        var modes = getModes();
        int value = 0;
        switch (modes) {
            case "00" -> value = getAddress(getAddress(instructionPointer + 1)) * getAddress(getAddress(instructionPointer + 2));
            case "01" -> value = getAddress(getAddress(instructionPointer + 1)) * getAddress(instructionPointer + 2);
            case "10" -> value = getAddress(instructionPointer + 1) * getAddress(getAddress(instructionPointer + 2));
            case "11" -> value = getAddress(instructionPointer + 1) * getAddress(instructionPointer + 2);
        }
        setAddress(pos, value);
        moveToNextOpcode(4);
    }

    private void getInput() {
        var pos = getAddress(instructionPointer + 1);
        System.out.println("input:");
        Scanner in = new Scanner(System.in);
        var s = in.nextInt();
        setAddress(pos, s);
        moveToNextOpcode(2);
    }

    private void doOutput() {
        var modes = getModes();
        int value = 0;
        switch (modes) {
            case "00" -> value = getAddress(getAddress(instructionPointer + 1));
            case "01" -> value = getAddress(instructionPointer + 1);
        }
        System.out.println("output:" + value);
        moveToNextOpcode(2);
    }

    private void doJump(boolean b) {
        var modes = getModes();
        int first = 0;
        int second = 0;
        switch (modes) {
            case "00" -> {
                first = getAddress(getAddress(instructionPointer + 1));
                second = getAddress(getAddress(instructionPointer + 2));
            }
            case "01" -> {
                first = getAddress(getAddress(instructionPointer + 1));
                second = getAddress(instructionPointer + 2);
            }
            case "10" -> {
                first = getAddress(instructionPointer + 1);
                second = getAddress(getAddress(instructionPointer + 2));
            }
            case "11" -> {
                first = getAddress(instructionPointer + 1);
                second = getAddress(instructionPointer + 2);
            }
        }
        if ((0 != first) == b) {
            instructionPointer = second;
        } else {
            moveToNextOpcode(3);
        }
    }

    private void doEquals() {
        var pos = getAddress(instructionPointer + 3);
        var modes = getModes();
        int first = 0;
        int second = 0;
        switch (modes) {
            case "00" -> {
                first = getAddress(getAddress(instructionPointer + 1));
                second = getAddress(getAddress(instructionPointer + 2));
            }
            case "01" -> {
                first = getAddress(getAddress(instructionPointer + 1));
                second = getAddress(instructionPointer + 2);
            }
            case "10" -> {
                first = getAddress(instructionPointer + 1);
                second = getAddress(getAddress(instructionPointer + 2));
            }
            case "11" -> {
                first = getAddress(instructionPointer + 1);
                second = getAddress(instructionPointer + 2);
            }
        }
        if (first == second) {
            setAddress(pos, 1);
        } else {
            setAddress(pos, 0);
        }
        moveToNextOpcode(4);
    }

    private void doLessThan() {
        var pos = getAddress(instructionPointer + 3);
        var modes = getModes();
        int first = 0;
        int second = 0;
        switch (modes) {
            case "00" -> {
                first = getAddress(getAddress(instructionPointer + 1));
                second = getAddress(getAddress(instructionPointer + 2));
            }
            case "01" -> {
                first = getAddress(getAddress(instructionPointer + 1));
                second = getAddress(instructionPointer + 2);
            }
            case "10" -> {
                first = getAddress(instructionPointer + 1);
                second = getAddress(getAddress(instructionPointer + 2));
            }
            case "11" -> {
                first = getAddress(instructionPointer + 1);
                second = getAddress(instructionPointer + 2);
            }
        }
        if (first < second) {
            setAddress(pos, 1);
        } else {
            setAddress(pos, 0);
        }
        moveToNextOpcode(4);
    }

    public void run() {
        while (getCurrentOpcode() != "99") {
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




