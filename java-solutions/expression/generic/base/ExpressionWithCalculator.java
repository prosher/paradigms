package expression.generic.base;

import expression.generic.calculators.Calculator;

public abstract class ExpressionWithCalculator<T> implements TripleExpression<T> {
    protected final Calculator<T> calculator;

    protected ExpressionWithCalculator(Calculator<T> calculator) {
        this.calculator = calculator;
    }

    public Calculator<T> getCalculator() {
        return calculator;
    }

    @Override
    public abstract T evaluate(final T x, final T y, final T z);
}
