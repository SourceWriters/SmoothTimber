package net.sourcewriters.smoothtimber.api.util.math;

import static java.lang.Math.max;
import static java.lang.Math.min;

public final class TableMath {

    public static final int DEFAULT_ROW_SIZE = 9;
    public static final int DEFAULT_MAX_ROW_INDEX = DEFAULT_ROW_SIZE - 1;

    private TableMath() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static int getId(final int[] values) {
        if (values.length < 2 || values.length > 3) {
            return -1;
        }
        return values.length == 2 ? getId(values[0], values[1]) : getId(values[0], values[1], values[2]);
    }

    public static int getId(final int row, final int column) {
        return getId0(max(row, 0), min(max(column, 0), DEFAULT_MAX_ROW_INDEX), DEFAULT_ROW_SIZE);
    }

    public static int getId(final int row, final int column, final int rowSize) {
        final int size = max(rowSize, 1);
        return getId0(max(row, 0), min(max(column, 0), size - 1), size);
    }

    private static int getId0(final int row, final int column, final int rowSize) {
        return row * rowSize + column;
    }

    public static int[] fromId(final int[] values) {
        if (values.length < 1 || values.length > 2) {
            return new int[0];
        }
        return values.length == 1 ? fromId(values[0]) : fromId(values[0], values[1]);
    }

    public static int[] fromId(final int id) {
        return fromId0(max(id, 0), DEFAULT_ROW_SIZE);
    }

    public static int[] fromId(final int id, final int rowSize) {
        final int size = max(rowSize, 1);
        return fromId0(max(id, 0), size);
    }

    private static int[] fromId0(final int id, final int rowSize) {
        final int[] output = new int[3];
        output[2] = rowSize;
        output[1] = id % rowSize;
        output[0] = (id - output[1]) / rowSize;
        return output;
    }

}