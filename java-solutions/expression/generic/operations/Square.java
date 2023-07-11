package expression.generic.operations;

import expression.generic.base.TripleExpression;
import expression.generic.calculators.Calculator;

public class Square<T> extends UnaryOperation<T> {

    private static final String SYMBOL = "square";

    public Square(Calculator<T> calculator, TripleExpression<T> argument) {
        super(calculator, argument);
    }

    @Override
    public String getSymbol() {
        return SYMBOL;
    }

    @Override
    protected T calc(T mid) {
        return calculator.square(mid);
    }
}
