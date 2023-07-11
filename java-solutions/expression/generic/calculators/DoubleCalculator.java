package expression.generic.calculators;

public enum DoubleCalculator implements Calculator<Double> {
    INSTANCE;

    @Override
    public Double add(final Double left, final Double right) {
        return left + right;
    }

    @Override
    public Double subtract(final Double left, final Double right) {
        return left - right;
    }

    @Override
    public Double multiply(final Double left, final Double right) {
        return left * right;
    }

    @Override
    public Double divide(final Double left, final Double right) {
        return left / right;
    }

    @Override
    public Double negate(final Double value) {
        return -value;
    }

    @Override
    public Double castInt(final int value) {
        return (double) value;
    }

    @Override
    public Double mod(final Double left, final Double right) {
        return left % right;
    }

    @Override
    public Double abs(final Double value) {
        return Math.abs(value);
    }

    @Override
    public Double square(final Double value) {
        return value * value;
    }
}
