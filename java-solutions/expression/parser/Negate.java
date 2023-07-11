package expression.parser;

import expression.Evaluateable;
import expression.UnaryOperation;

public class Negate extends UnaryOperation {
    private static final String SYMBOL = "-";

    public Negate(Evaluateable mid) {
        super(mid);
    }

    @Override
    protected int calc(int mid) {
        return -mid;
    }

    @Override
    protected double calc(double mid) {
        return -mid;
    }

    @Override
    public String getSymbol() {
        return SYMBOL;
    }
}
