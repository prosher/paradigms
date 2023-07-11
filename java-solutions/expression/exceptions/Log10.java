package expression.exceptions;

import expression.Evaluateable;
import expression.UnaryOperation;

public class Log10 extends UnaryOperation {

    private static final String SYMBOL = "log10";

    public Log10(Evaluateable argument) {
        super(argument);
    }

    @Override
    public String getSymbol() {
        return SYMBOL;
    }

    @Override
    protected int calc(int mid) {
        if (mid <= 0) {
            throw new LogArgExprEvalException("non-positive logarithm argument");
        }
        return Integer.toString(mid).length() - 1;
    }

    @Override
    protected double calc(double mid) {
        throw new AssertionError("double is not supported");
    }
}
