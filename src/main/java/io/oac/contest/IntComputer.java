package io.oac.contest;

public class IntComputer{
    private final int[] memory;
    private int instructionPointer = 0;

    public IntComputer(int[] memory) {
        this.memory = memory;
    }

    public int getCurrentOpcode() {
        return memory[instructionPointer];
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
        var value = getAddress(getAddress(instructionPointer + 1)) + getAddress(getAddress(instructionPointer + 2));
        setAddress(pos, value);
        moveToNextOpcode(4);
    }

    private void doMultiply() {
        var pos = getAddress(instructionPointer + 3);
        var value = getAddress(getAddress(instructionPointer + 1)) * getAddress(getAddress(instructionPointer + 2));
        setAddress(pos, value);
        moveToNextOpcode(4);
    }

    public void run() {
        while (getCurrentOpcode() != 99) {
            switch (getCurrentOpcode()) {
                case 1 -> doIncrement();
                case 2 ->  doMultiply();
            }
        }
    }
}




