package expression.exceptions;

import expression.Evaluateable;
import expression.parser.Negate;

public final class CheckedNegate extends Negate {

    public CheckedNegate(Evaluateable mid) {
        super(mid);
    }

    @Override
    protected int calc(int mid) {
        if (overflow(mid)) {
            throw new OverflowExprEvalException("integer negation overflow");
        }
        return -mid;
    }

    public static boolean overflow(int mid) {
        return mid == Integer.MIN_VALUE;
    }
}
