package expression.exceptions;

import expression.Evaluateable;
import expression.Multiply;

public final class CheckedMultiply extends Multiply {

    public CheckedMultiply(Evaluateable left, Evaluateable right) {
        super(left, right);
    }

    @Override
    protected int calc(int left, int right) {
        if (overflow(left, right)) {
            throw new OverflowExprEvalException("integer multiplication overflow");
        }
        return left * right;
    }

    public static boolean overflow(int left, int right) {
        final int product = left * right;
        return left != 0 && product / left != right
                || right != 0 && product / right != left;
    }
}
