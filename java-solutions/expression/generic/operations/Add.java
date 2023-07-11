package expression.generic.operations;

import expression.generic.base.TripleExpression;
import expression.generic.calculators.Calculator;

public class Add<T> extends BinaryOperation<T> {

    private static final String SYMBOL = "+";

    public Add(final Calculator<T> calculator, final TripleExpression<T> left, final TripleExpression<T> right) {
        super(calculator, left, right);
    }

    @Override
    protected T calc(final T left, final T right) {
        return calculator.add(left, right);
    }

    @Override
    public String getSymbol() {
        return SYMBOL;
    }
}
