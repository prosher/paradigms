package expression.generic.calculators;

public enum LongCalculator implements Calculator<Long> {
    INSTANCE;

    @Override
    public Long add(final Long left, final Long right) {
        return left + right;
    }

    @Override
    public Long subtract(final Long left, final Long right) {
        return left - right;
    }

    @Override
    public Long multiply(final Long left, final Long right) {
        return left * right;
    }

    @Override
    public Long divide(final Long left, final Long right) {
        return left / right;
    }

    @Override
    public Long negate(final Long value) {
        return -value;
    }

    @Override
    public Long castInt(int value) {
        return (long) value;
    }

    @Override
    public Long mod(final Long left, final Long right) {
        return left % right;
    }

    @Override
    public Long abs(final Long value) {
        return Math.abs(value);
    }

    @Override
    public Long square(final Long value) {
        return value * value;
    }
}
