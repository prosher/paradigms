package expression.exceptions;

import expression.Evaluateable;
import expression.UnaryOperation;

public class Pow10 extends UnaryOperation {

    private static final String SYMBOL = "pow10";

    public Pow10(Evaluateable argument) {
        super(argument);
    }

    @Override
    public String getSymbol() {
        return SYMBOL;
    }

    @Override
    protected int calc(int mid) {
        if (mid < 0) {
            throw new NegPowExprEvalException("negative pow argument");
        }
        int result = 1;
        while (mid > 0) {
            if (result > Integer.MAX_VALUE / 10) {
                throw new OverflowExprEvalException("integer power overflow");
            }
            result *= 10;
            mid--;
        }
        return result;
    }

    @Override
    protected double calc(double mid) {
        throw new AssertionError("double is not supported");
    }
}
