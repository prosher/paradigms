package expression.generic.operations;

import expression.generic.base.TripleExpression;
import expression.generic.calculators.Calculator;

public class Mod<T> extends BinaryOperation<T> {

    private static final String SYMBOL = "mod";

    public Mod(Calculator<T> calculator, TripleExpression<T> left, TripleExpression<T> right) {
        super(calculator, left, right);
    }

    @Override
    public String getSymbol() {
        return SYMBOL;
    }

    @Override
    protected T calc(T left, T right) {
        return calculator.mod(left, right);
    }
}
