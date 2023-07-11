package expression.generic.operations;

import expression.generic.base.TripleExpression;
import expression.generic.calculators.Calculator;

public class Multiply<T> extends BinaryOperation<T> {

    private static final String SYMBOL = "*";

    public Multiply(final Calculator<T> calculator, final TripleExpression<T> left, final TripleExpression<T> right) {
        super(calculator, left, right);
    }

    @Override
    protected T calc(final T left, final T right) {
        return calculator.multiply(left, right);
    }

    @Override
    public String getSymbol() {
        return SYMBOL;
    }

}
