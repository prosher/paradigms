package expression.exceptions;

import expression.Add;
import expression.Evaluateable;

public class CheckedAdd extends Add {

    public CheckedAdd(Evaluateable left, Evaluateable right) {
        super(left, right);
    }

    @Override
    protected int calc(int left, int right) {
        if (overflow(left, right)) {
            throw new OverflowExprEvalException("integer addition overflow");
        }
        return left + right;
    }

    public static boolean overflow(int left, int right) {
        return right > 0 && left > 0 && Integer.MAX_VALUE - left < right
                || right < 0 && left < 0 && Integer.MIN_VALUE - left > right;
    }
}
