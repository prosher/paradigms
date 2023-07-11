package expression.parser;

import expression.Evaluateable;
import expression.UnaryOperation;

public class Count extends UnaryOperation {

    private static final String SYMBOL = "count";

    public Count(Evaluateable mid) {
        super(mid);
    }

    @Override
    public String getSymbol() {
        return SYMBOL;
    }

    @Override
    protected int calc(int mid) {
        return Integer.bitCount(mid);
    }

    @Override
    protected double calc(double mid) {
        throw new AssertionError("double is not supported");
    }
}
