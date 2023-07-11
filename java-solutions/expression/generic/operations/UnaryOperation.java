package expression.generic.operations;

import expression.generic.base.ExpressionWithCalculator;
import expression.generic.base.GenericMetadata;
import expression.generic.base.TripleExpression;
import expression.generic.calculators.Calculator;

public abstract class UnaryOperation<T> extends ExpressionWithCalculator<T> {

    private final TripleExpression<T> arg;

    protected UnaryOperation(final Calculator<T> calculator, final TripleExpression<T> argument) {
        super(calculator);
        arg = argument;
    }

    public TripleExpression<T> getArgument() {
        return arg;
    }

    public abstract String getSymbol();

    @Override
    public T evaluate(final T x, final T y, final T z) {
        return calc(getArgument().evaluate(x, y, z));
    }

    protected abstract T calc(final T mid);

    @Override
    public String toString() {
        return getSymbol() + GenericMetadata.LP + getArgument().toString() + GenericMetadata.RP;
    }


    @Override
    public String toMiniString() {
        if (GenericMetadata.PRIORITY.get(getArgument().getClass())
                < GenericMetadata.PRIORITY.get(this.getClass())) {
            return getSymbol() + GenericMetadata.LP + getArgument().toMiniString() + GenericMetadata.RP;
        } else {
            return getSymbol() + " " + getArgument().toMiniString();
        }
    }
}
