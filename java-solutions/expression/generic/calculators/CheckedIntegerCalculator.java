package expression.generic.calculators;

import expression.exceptions.*;
import expression.generic.base.GenericMetadata;
import expression.generic.operations.*;

public enum CheckedIntegerCalculator implements Calculator<Integer> {
    INSTANCE;

    @Override
    public Integer add(final Integer left, final Integer right) {
        if (CheckedAdd.overflow(left, right)) {
            overflowError(left, right, Add.class);
        }

        return left + right;
    }

    @Override
    public Integer subtract(final Integer left, final Integer right) {
        if (CheckedSubtract.overflow(left, right)) {
            overflowError(left, right, Subtract.class);
        }
        return left - right;
    }

    @Override
    public Integer multiply(final Integer left, final Integer right) {
        if (CheckedMultiply.overflow(left, right)) {
            overflowError(left, right, Multiply.class);
        }
        return left * right;
    }

    @Override
    public Integer divide(final Integer left, final Integer right) {
        if (CheckedDivide.overflow(left, right)) {
            overflowError(left, right, Divide.class);
        }
        return left / right;
    }

    @Override
    public Integer negate(final Integer value) {
        if (CheckedNegate.overflow(value)) {
            overflowError(Negate.class, value);
        }
        return -value;
    }

    @Override
    public Integer castInt(final int value) {
        return value;
    }

    @Override
    public Integer mod(final Integer left, final Integer right) {
        return left % right;
    }

    @Override
    public Integer abs(final Integer value) {
        if (CheckedNegate.overflow(value)) {
            overflowError(Abs.class, value);
        }
        return Math.abs(value);
    }

    @Override
    public Integer square(final Integer value) {
        if (CheckedMultiply.overflow(value, value)) {
            overflowError(Square.class, value);
        }
        return value * value;
    }

    private static void overflowError(final int left, final int right, final Class<?> op) {
        throw new OverflowExprEvalException(String.format("%d %s %d (overflow)",
                left,
                GenericMetadata.OPERATION_TO_STRING.get(op),
                right));
    }

    private static void overflowError(final Class<?> op, final int value) {
        throw new OverflowExprEvalException(String.format("%s %d (overflow)", op, value));
    }

    private static void overflowError(final int value, final Class<?> op) {
        throw new OverflowExprEvalException(String.format("%d %s (overflow)", value, op));
    }
}
