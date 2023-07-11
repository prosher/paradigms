package expression.generic.operations;

import expression.generic.base.TripleExpression;
import expression.generic.calculators.Calculator;

public class Subtract<T> extends BinaryOperation<T> {

    public static final String SYMBOL = "-";

    public Subtract(final Calculator<T> calculator, final TripleExpression<T> left, final TripleExpression<T> right) {
        super(calculator, left, right);
    }

    @Override
    protected T calc(final T left, final T right) {
        return calculator.subtract(left, right);
    }

    @Override
    public String getSymbol() {
        return SYMBOL;
    }

}
