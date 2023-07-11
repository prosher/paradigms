package expression.exceptions;

import expression.Evaluateable;
import expression.Subtract;

public final class CheckedSubtract extends Subtract {

    public CheckedSubtract(Evaluateable left, Evaluateable right) {
        super(left, right);
    }

    @Override
    protected int calc(int left, int right) {
        if (overflow(left, right)) {
            throw new OverflowExprEvalException("integer subtraction overflow");
        }
        return left - right;
    }

    public static boolean overflow(int left, int right) {
        return right > 0 && left < -1 && left - Integer.MIN_VALUE < right
                || right < 0 && left > -1 && left > Integer.MAX_VALUE + right;
    }
}
