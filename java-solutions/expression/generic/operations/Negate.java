package expression.generic.operations;

import expression.generic.base.TripleExpression;
import expression.generic.calculators.Calculator;

public class Negate<T> extends UnaryOperation<T> {
    private static final String SYMBOL = "-";

    public Negate(Calculator<T> calculator, TripleExpression<T> argument) {
        super(calculator, argument);
    }

    @Override
    protected T calc(final T mid) {
        return calculator.negate(mid);
    }

    @Override
    public String getSymbol() {
        return SYMBOL;
    }
}
