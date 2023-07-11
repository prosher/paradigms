package expression.generic.calculators;

public enum IntegerCalculator implements Calculator<Integer> {
    INSTANCE;

    @Override
    public Integer add(final Integer left, final Integer right) {
        return left + right;
    }

    @Override
    public Integer subtract(final Integer left, final Integer right) {
        return left - right;
    }

    @Override
    public Integer multiply(final Integer left, final Integer right) {
        return left * right;
    }

    @Override
    public Integer divide(final Integer left, final Integer right) {
        return left / right;
    }

    @Override
    public Integer negate(final Integer value) {
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
        return Math.abs(value);
    }

    @Override
    public Integer square(final Integer value) {
        return value * value;
    }
}
