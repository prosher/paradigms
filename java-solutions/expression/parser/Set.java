package expression.parser;

import expression.BinaryOperation;
import expression.Evaluateable;

public class Set extends BinaryOperation {

    private static final String SYMBOL = "set";

    public Set(Evaluateable left, Evaluateable right) {
        super(left, right);
    }

    @Override
    public String getSymbol() {
        return SYMBOL;
    }

    @Override
    protected int calc(int left, int right) {
        return left | (1 << right);
    }

    @Override
    protected double calc(double left, double right) {
        throw new AssertionError("double is not supported");
    }
}
