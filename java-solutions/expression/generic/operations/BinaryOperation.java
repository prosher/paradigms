package expression.generic.operations;

import expression.generic.base.ExpressionWithCalculator;
import expression.generic.base.GenericMetadata;
import expression.generic.base.TripleExpression;
import expression.generic.calculators.Calculator;

public abstract class BinaryOperation<T>
        extends ExpressionWithCalculator<T>
        implements Operation {

    private final TripleExpression<T> left;
    private final TripleExpression<T> right;

    protected BinaryOperation(Calculator<T> calculator, TripleExpression<T> left, TripleExpression<T> right) {
        super(calculator);
        this.left = left;
        this.right = right;
    }

    public TripleExpression<T> getLeft() {
        return left;
    }

    public TripleExpression<T> getRight() {
        return right;
    }

    public abstract String getSymbol();

    @Override
    public T evaluate(final T x, final T y, final T z) {
        return calc(getLeft().evaluate(x, y, z), getRight().evaluate(x, y, z));
    }

    protected abstract T calc(final T left, final T right);

    @Override
    public String toString() {
        return GenericMetadata.LP + getLeft().toString() + " "
                + getSymbol() + " " + getRight().toString() + GenericMetadata.RP;
    }


    @Override
    public String toMiniString() {
        final StringBuilder sb = new StringBuilder();
        final int thisPriority = GenericMetadata.PRIORITY.get(this.getClass());
        final int leftPriority = GenericMetadata.PRIORITY.get(getLeft().getClass());
        final int rightPriority = GenericMetadata.PRIORITY.get(getRight().getClass());

        if (thisPriority > leftPriority) {
            sb.append(GenericMetadata.LP).append(getLeft().toMiniString()).append(GenericMetadata.RP);
        } else {
            sb.append(getLeft().toMiniString());
        }
        sb.append(" ").append(this.getSymbol()).append(" ");
        if (thisPriority > rightPriority ||
                thisPriority == rightPriority && (GenericMetadata.ASYMMETRIC.contains(this.getClass()) ||
                        GenericMetadata.ORDERED.contains(getRight().getClass()))) {
            sb.append(GenericMetadata.LP).append(getRight().toMiniString()).append(GenericMetadata.RP);
        } else {
            sb.append(getRight().toMiniString());
        }
        return sb.toString();
    }
}
