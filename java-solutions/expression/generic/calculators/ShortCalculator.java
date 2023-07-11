package expression.generic.calculators;

public enum ShortCalculator implements Calculator<Short> {
    INSTANCE;

    @Override
    public Short add(final Short left, final Short right) {
        return (short) (left + right);
    }

    @Override
    public Short subtract(final Short left, final Short right) {
        return (short) (left - right);
    }

    @Override
    public Short multiply(final Short left, final Short right) {
        return (short) (left * right);
    }

    @Override
    public Short divide(final Short left, final Short right) {
        return (short) (left / right);
    }

    @Override
    public Short negate(final Short value) {
        return (short) -value;
    }

    @Override
    public Short castInt(int value) {
        return (short) value;
    }

    @Override
    public Short mod(final Short left, final Short right) {
        return (short) (left % right);
    }

    @Override
    public Short abs(final Short value) {
        return (short) Math.abs(value);
    }

    @Override
    public Short square(final Short value) {
        return (short) (value * value);
    }
}
