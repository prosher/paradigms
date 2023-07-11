package expression.exceptions;

import expression.Divide;
import expression.Evaluateable;

public final class CheckedDivide extends Divide {

    public CheckedDivide(Evaluateable left, Evaluateable right) {
        super(left, right);
    }

    @Override
    protected int calc(int left, int right) {
        if (overflow(left, right)) {
            throw new OverflowExprEvalException("integer division overflow");
        }
        if (right == 0) {
            throw new DBZExprEvalException("division by zero");
        }
        return left / right;
    }

    public static boolean overflow(int left, int right) {
        return left == Integer.MIN_VALUE && right == -1;
    }
}
