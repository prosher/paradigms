package expression.generic.calculators;

import java.math.BigInteger;

public enum BigIntegerCalculator implements Calculator<BigInteger> {
    INSTANCE;

    @Override
    public BigInteger add(final BigInteger left, final BigInteger right) {
        return left.add(right);
    }

    @Override
    public BigInteger subtract(final BigInteger left, final BigInteger right) {
        return left.subtract(right);
    }

    @Override
    public BigInteger multiply(final BigInteger left, final BigInteger right) {
        return left.multiply(right);
    }

    @Override
    public BigInteger divide(final BigInteger left, final BigInteger right) {
        return left.divide(right);
    }

    @Override
    public BigInteger negate(final BigInteger value) {
        return value.negate();
    }

    @Override
    public BigInteger castInt(final int value) {
        return BigInteger.valueOf(value);
    }

    @Override
    public BigInteger mod(final BigInteger left, final BigInteger right) {
        return left.mod(right);
    }

    @Override
    public BigInteger abs(final BigInteger value) {
        return value.abs();
    }

    @Override
    public BigInteger square(final BigInteger value) {
        return value.multiply(value);
    }
}
